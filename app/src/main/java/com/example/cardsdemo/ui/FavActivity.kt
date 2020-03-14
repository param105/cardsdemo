package com.example.cardsdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsdemo.FavAdapter
import com.example.cardsdemo.R


class FavActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

    }

    /***
     * Keep data ready fetched from database
     */
    private fun init() {
        var rvFavorite = findViewById<RecyclerView>(R.id.favorite_match)
        val llManager = LinearLayoutManager(this)
        llManager.orientation = LinearLayoutManager.VERTICAL
        rvFavorite.layoutManager = llManager
        rvFavorite.adapter = FavAdapter(this)

    }

    override fun onResume() {
        super.onResume()
        init()
    }
}
