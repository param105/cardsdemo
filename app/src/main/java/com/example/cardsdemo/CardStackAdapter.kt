package com.example.cardsdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class CardStackAdapter(private val mainActivity: Context) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    public var users: UserDetails?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Glide.with(holder.image)
                .load(users?.results?.get(0)?.user?.picture)
            .placeholder(R.drawable.user)
                .into(holder.image)

        holder.bottomBar.selectedItemId=R.id.navigation_profile



        holder.bottomBar.setOnNavigationItemSelectedListener {item->

            when (item.itemId) {
                R.id.navigation_profile -> {

                    val name=users?.results?.get(0)?.user?.name?.title + " "+users?.results?.get(0)?.user?.name?.first+" "+users?.results?.get(0)?.user?.name?.last

                    holder.userPropertyTextView.text=mainActivity.getString(R.string.my_name)
                    holder.userPropertyValueTextView.text= name
                    return@setOnNavigationItemSelectedListener true


                }
                R.id.navigation_adress -> {

                    holder.userPropertyTextView.text=mainActivity.getString(R.string.my_address)
                    holder.userPropertyValueTextView.text= users?.results?.get(0)?.user?.location?.city.toString()
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_phone -> {

                    holder.userPropertyTextView.text=mainActivity.getString(R.string.my_phone)
                    holder.userPropertyValueTextView.text= users?.results?.get(0)?.user?.phone.toString()
                    return@setOnNavigationItemSelectedListener true

                }

                R.id.navigation_pass -> {

                    holder.userPropertyTextView.text=mainActivity.getString(R.string.my_pass)
                    holder.userPropertyValueTextView.text= users?.results?.get(0)?.user?.password.toString()
                    return@setOnNavigationItemSelectedListener true

                }
            }
           false
        }

    }

    override fun getItemCount(): Int {
        return 1
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image: ImageView = view.findViewById(R.id.itemImageView)
        var bottomBar: BottomNavigationView = view.findViewById(R.id.bottomView)

        var userPropertyTextView:TextView=view.findViewById(R.id.userPropertyTextView)

        var userPropertyValueTextView:TextView=view.findViewById(R.id.userPropertyValueTextView)
    }

}
