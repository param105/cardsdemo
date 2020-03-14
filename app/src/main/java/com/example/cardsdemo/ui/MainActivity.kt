package com.example.cardsdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.cardsdemo.CardStackAdapter
import com.example.cardsdemo.R
import com.example.cardsdemo.UserContract
import com.example.cardsdemo.UserDetails
import com.example.cardsdemo.UserPresenter
import com.example.cardsdemo.roomdatabase.FavUsers
import com.example.retrodemo.ApiClient
import com.example.retrodemo.ApiInterface
import com.example.roomdemo.UserDatabase
import com.example.roomdemo.UserOperations
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity(), CardStackListener,
    UserContract.UserViewLoader {

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private lateinit var userPresenter: UserPresenter
    private lateinit var apiInterface: ApiInterface
    private lateinit var adapter: CardStackAdapter
    private lateinit var cardStackView: CardStackView

    // for database
    companion object {
        lateinit var myTinderDB: UserDatabase
        lateinit var userOperations: UserOperations
    }

    var userCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationSetUp()
        initUI()
    }

    private fun navigationSetUp() {

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        // DrawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        actionBarDrawerToggle.syncState()
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        // NavigationView
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.reload -> reload()
                R.id.add_spot_to_first -> gotoFavourites()

            }
            drawerLayout.closeDrawers()
            true
        }
    }


    private fun reload() {
        userCount = 1
        getFirstUserData(userCount)
    }


    private fun gotoFavourites() {
            Toast.makeText(this, " Fav ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)
    }


    private fun initUI() {

        userPresenter = UserPresenter(this)
        apiInterface = ApiClient.getAPIClient().create(ApiInterface::class.java)
        cardStackView = findViewById(R.id.card_stack_view)
        myTinderDB = UserDatabase.getInstance(context = applicationContext)

        getFirstUserData(1)

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())

        cardStackView.layoutManager = manager
        adapter = CardStackAdapter(this@MainActivity)

        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

    }

    private fun getFirstUserData(count: Int) {
        var strCount = "0.$count"
        var url = "$strCount/?randomapi"
        userPresenter.getUserDetails(url, apiInterface)
        userCount += 1
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }


    override fun onCardSwiped(direction: Direction?) {
        val side: String = direction.toString()

        if (side == "Left") {

            if (adapter.users != null) {
                val name =
                    adapter.users?.results?.get(0)?.user?.name?.title + " " + adapter.users?.results?.get(
                        0
                    )?.user?.name?.first
                val address = adapter.users?.results?.get(0)?.user?.location?.city.toString()
                val imageUrl = adapter.users?.results?.get(0)?.user?.picture

                val favListModel =
                    FavUsers(0, name, address, imageUrl)

                CoroutineScope(Dispatchers.IO).launch {
                    userOperations = myTinderDB.userDao()
                    userOperations.insert(favListModel)
                    Log.d("MainActivity", "FavUsers count-" + userOperations.getAll().count())
                }
            }

        }

        if (userCount < 9) {
            getFirstUserData(userCount)
        }

    }


    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    override fun setUserData(userDetails: UserDetails) {
        adapter.users = userDetails
        adapter.notifyDataSetChanged()
    }
}
