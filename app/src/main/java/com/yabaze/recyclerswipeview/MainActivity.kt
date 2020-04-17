package com.yabaze.recyclerswipeview

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yabaze.swipelibrary.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = SampleRecyclerAdapter()

        val leftMenu = ArrayList<SwipeItemDetail>()
        val rightMenu = ArrayList<SwipeItemDetail>()

        val firstItem = SwipeItemDetail().apply {

            backgroundColor = "#567897"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.background_box)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Love"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_heart)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }
        val secondItem = SwipeItemDetail().apply {

            backgroundColor = "#567897"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.background_box)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Like"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_like)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }
        val third = SwipeItemDetail().apply {

            backgroundColor = "#FEEFDD"

            //text = "Call"

            iconWidth = 150
            iconHeight = 150

            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_call)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }
        val forth = SwipeItemDetail().apply {

            backgroundColor = "#FEEFDD"

            //text = "SnapChat"

            iconWidth = 150
            iconHeight = 150

            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_snapchat)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }
        val fifth = SwipeItemDetail().apply {

            backgroundColor = "#A46578"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.background_box)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Share"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_share)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }

        rightMenu.apply {
            add(third)
            add(forth)
        }

        leftMenu.apply {
            add(firstItem)
            add(secondItem)
            add(fifth)
        }

        val swipeController = SwipeController(object : SwipeControllerActions() {

            override fun onClicked(position: Int, state: SwipeState, swipeButtonPosition: Int) {

                when(state){
                    SwipeState.RIGHT->{
                        Log.e("clicked - ","${rightMenu[swipeButtonPosition].text}")
                        Toast.makeText(this@MainActivity,"${rightMenu[swipeButtonPosition].id} Clicked",Toast.LENGTH_SHORT).show()
                    }
                    SwipeState.LEFT->{
                        Log.e("clicked - ","${leftMenu[swipeButtonPosition].text}")
                        Toast.makeText(this@MainActivity,"${leftMenu[swipeButtonPosition].text} Clicked",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }, this@MainActivity, rightMenu, leftMenu)

        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })

    }
}

