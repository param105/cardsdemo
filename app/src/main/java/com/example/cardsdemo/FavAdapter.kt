package com.example.cardsdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsdemo.roomdatabase.FavUsers
import com.example.roomdemo.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavAdapter (private val context: Context): RecyclerView.Adapter<FavAdapter.AppViewHolder>() {
    lateinit var favUsers : ArrayList<FavUsers>
    init {
        CoroutineScope(Dispatchers.IO).launch {
            favUsers = UserDatabase.getInstance(context = context).userDao().getAll() as ArrayList<FavUsers>
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AppViewHolder {
        return AppViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_users, parent, false))
    }

    override fun getItemCount(): Int {
       return favUsers.size
    }

    override fun onBindViewHolder(holder:AppViewHolder, position: Int) {
       holder.tvName.text = favUsers.get(position).firstName
        holder.tvAddress.text = favUsers.get(position).address

        Glide.with(holder.ivPhoto)
            .load(favUsers?.get(position).imageUrl)
            .placeholder(R.drawable.user)
            .into(holder.ivPhoto)
    }

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view){
          var tvName:TextView = view.findViewById(R.id.userNameTextView)
         var tvAddress:TextView = view.findViewById(R.id.userAddressTextView)
         var ivPhoto : ImageView  = view.findViewById(R.id.userImageView)
    }
}