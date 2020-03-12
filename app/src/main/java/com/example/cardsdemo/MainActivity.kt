package com.example.cardsdemo

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.room.Room
import com.example.cardsdemo.roomdatabase.Users
import com.example.retrodemo.ApiClient
import com.example.retrodemo.ApiInterface
import com.example.roomdemo.UserDatabase
import com.example.roomdemo.UserOperations
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*

class MainActivity : AppCompatActivity(), CardStackListener,UserContract.UserViewLoader {

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }



    private lateinit var  userPresenter: UserPresenter
    private lateinit var apiInterface:ApiInterface
    private lateinit var adapter: CardStackAdapter
    private lateinit var  cardStackView:CardStackView

    // for database


    companion object{

        lateinit var userDatabase: UserDatabase
        lateinit var userOperations: UserOperations
    }

    var userCount=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationSetUp()
        initUI()
    }

    private fun navigationSetUp(){


        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        // DrawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
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

    private fun reload(){

        userCount=1
        getFirstUserData(userCount)
    }

    private fun gotoFavourites(){




        userDatabase=
            Room.databaseBuilder(applicationContext,UserDatabase::class.java,"users").build()

        val userOperations = userDatabase.userDao()

         fetchFavData(userOperations)




    }

    private   fun  fetchFavData(userOperations: UserOperations) {

        var favList=ArrayList<Users>()

//
//        GlobalScope.launch {
//            favList= userOperations.getAll() as ArrayList<FavListModel>
//
//        }




        if(favList.isNotEmpty()){

            Toast.makeText(this," Fav ",Toast.LENGTH_SHORT).show()

            val intent=Intent(this,FavActivity::class.java)
            startActivity(intent)

        }
        else{

            Toast.makeText(this,"No Fav ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI(){

        userPresenter= UserPresenter(this)
        apiInterface= ApiClient.getAPIClient().create(ApiInterface::class.java)

         cardStackView = findViewById(R.id.card_stack_view)

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
        adapter=CardStackAdapter(this@MainActivity)

        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

    }

    private fun getFirstUserData(count:Int){

        var strCount= "0.$count"
        var url = "$strCount/?randomapi"

        userPresenter.getUserDetails(url,apiInterface)

        userCount += 1

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {


        val side:String= direction.toString()


        if(side == "Left"){

            Toast.makeText(this,getString(R.string.add_to_favourites),Toast.LENGTH_SHORT).show()

            if(adapter.users!=null){

                val name=adapter.users?.results?.get(0)?.user?.name?.title + " "+adapter.users?.results?.get(0)?.user?.name?.first
                val address=adapter.users?.results?.get(0)?.user?.location?.city.toString()
                val imageUrl=adapter.users?.results?.get(0)?.user?.picture



                val favListModel=
                    Users(1,name,address,imageUrl)

                userDatabase=
                    Room.databaseBuilder(applicationContext,UserDatabase::class.java,"fav_table").build()



                userOperations = userDatabase.userDao()

                SaveAsync().execute(favListModel)


//
//
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    setToFavList(favListModel,userOperations)
//                }





            }


        }

        if(userCount<9){

            getFirstUserData(userCount)
        }

    }

    class  SaveAsync : AsyncTask<Any, Any, Any>() {

        override fun doInBackground(vararg params: Any?) {





            userOperations.insertAll(params)
        }


    }

   private suspend fun setToFavList(
       favListModel: Users,
       userOperations: UserOperations
   ) {

   //    Toast.makeText(this,"add",Toast.LENGTH_SHORT).show()

//       withContext(Dispatchers.IO){
//           userOperations.insertAll(favListModel)
//       }




    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    override fun setUserData(userDetails: UserDetails) {

        adapter.users=userDetails

        adapter.notifyDataSetChanged()
    }
}
