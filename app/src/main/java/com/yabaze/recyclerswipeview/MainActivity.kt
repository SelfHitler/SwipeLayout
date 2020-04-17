package com.yabaze.recyclerswipeview

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
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

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Hi"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#874567"

        }
        val secondItem = SwipeItemDetail().apply {

            backgroundColor = "#567897"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Sample"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#874567"

        }
        val third = SwipeItemDetail().apply {

            backgroundColor = "#A46578"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Clock"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#CD4679"

        }
        val forth = SwipeItemDetail().apply {

            backgroundColor = "#567897"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Sample"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                iconBitmap = it
            }

            textColor = "#874567"

        }
        val fifth = SwipeItemDetail().apply {

            backgroundColor = "#A46578"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
                backgroundImage = it
            }

            text = "Clock"

            ContextCompat.getDrawable(this@MainActivity, R.drawable.sample_icon)?.toBitmap()?.let {
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
                Log.e(
                    " position $position",
                    " state = ${state.name}  position $swipeButtonPosition"
                )
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

