package com.yabaze.swipelibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


class SwipeController : Callback {

    private var displayWidth = 0
    private var swipeBack = false
    private var buttonShowedState = ButtonsState.GONE
    private var buttonInstance: RectF? = null
    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private var buttonsActions: SwipeControllerActions? = null
    private var rightButton: RectF? = null
    private var leftButton: RectF? = null

    private var rightMenuItems: ArrayList<SwipeItemDetail> = ArrayList()
    private var leftMenuItems: ArrayList<SwipeItemDetail> = ArrayList()
    private var allItems: ArrayList<SwipeItemDetail> = ArrayList()

    private var rightSideVisibilleArea = 0f
    private var leftSideVisibilleArea = 0f
    private var eachItemWidth = 0f

    constructor(
        buttonsActions: SwipeControllerActions,
        context: Context,
        rightMenuItems: ArrayList<SwipeItemDetail>,
        leftMenuItems: ArrayList<SwipeItemDetail>
    ) {
        this.rightMenuItems = rightMenuItems
        this.leftMenuItems = leftMenuItems

        this.buttonsActions = buttonsActions
        displayWidth = context.resources.displayMetrics.widthPixels

        val count = rightMenuItems.size + leftMenuItems.size
        eachItemWidth = (1.toFloat() / count)


        for ((index, l) in leftMenuItems.withIndex()) {
            l.id = index
            l.buttonState = SwipeState.LEFT
            allItems.add(l)
        }

        for ((rightIndex, item) in rightMenuItems.withIndex()) {
            item.id = rightIndex
            item.buttonState = SwipeState.RIGHT
            allItems.add(item)
        }

        rightSideVisibilleArea = (rightMenuItems.size.toFloat() / count.toFloat())
        leftSideVisibilleArea = (leftMenuItems.size.toFloat() / count.toFloat())

    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            0,
            LEFT or RIGHT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonShowedState !== ButtonsState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        var dX = dX
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState !== ButtonsState.GONE) {
                if (buttonShowedState === ButtonsState.LEFT_VISIBLE) dX =
                    max(dX.toDouble(), displayWidth * leftSideVisibilleArea.toDouble())
                        .toFloat()
                if (buttonShowedState === ButtonsState.RIGHT_VISIBLE) dX =
                    min(dX.toDouble(), -(displayWidth * rightSideVisibilleArea.toDouble()))
                        .toFloat()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            } else {
                setTouchListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        if (buttonShowedState === ButtonsState.GONE) {
            Log.e("--------x-------",""+dX)



            if(leftMenuItems.size==rightMenuItems.size){
                super.onChildDraw(c, recyclerView, viewHolder, dX/2, dY, actionState, isCurrentlyActive)
            } else {

                if(dX>0){

                    if(dX>displayWidth * eachItemWidth * leftMenuItems.size) {


                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder, displayWidth * eachItemWidth * leftMenuItems.size,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )

                    } else {
                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder, dX ,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }
                    //left side

                } else {
                    if(dX>((-1)*displayWidth * eachItemWidth * rightMenuItems.size)) {

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    } else {
                        super.onChildDraw(c, recyclerView, viewHolder, (-1)*displayWidth * eachItemWidth * rightMenuItems.size, dY, actionState, isCurrentlyActive)
                    }

                    //right side
                }
            }


        }
        currentItemViewHolder = viewHolder
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { v: View?, event: MotionEvent ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (dX < -50) buttonShowedState =
                    ButtonsState.RIGHT_VISIBLE else if (dX > 50) buttonShowedState =
                    ButtonsState.LEFT_VISIBLE
                if (buttonShowedState !== ButtonsState.GONE) {
                    setTouchDownListener(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    setItemsClickable(recyclerView, false)
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                super@SwipeController.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    0f,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                recyclerView.setOnTouchListener { v1: View?, event1: MotionEvent? -> false }
                setItemsClickable(recyclerView, true)
                swipeBack = false
                if (buttonsActions != null && buttonInstance != null && buttonInstance!!.contains(
                        event.x,
                        event.y
                    )
                ) {
                    if (buttonShowedState === ButtonsState.LEFT_VISIBLE) {

                        leftButton?.let {
                            if (it.contains(event.x, event.y)) {
                                for ((index, i) in allItems.withIndex()) {
                                    i.clickableArea?.let { clickable ->
                                        if (clickable.contains(event.x, event.y)) {
                                            buttonsActions?.onClicked(
                                                viewHolder.adapterPosition,
                                                SwipeState.LEFT,
                                                i.id
                                            )
                                        }
                                    }
                                }
                            }
                        }


                    } else if (buttonShowedState === ButtonsState.RIGHT_VISIBLE) {

                        rightButton?.let {
                            if (it.contains(event.x, event.y)) {
                                for ((index, i) in allItems.withIndex()) {
                                    i.clickableArea?.let { clickable ->
                                        if (clickable.contains(event.x, event.y)) {
                                            buttonsActions?.onClicked(
                                                viewHolder.adapterPosition,
                                                SwipeState.RIGHT,
                                                i.id
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                buttonShowedState = ButtonsState.GONE
                currentItemViewHolder = null
            }
            false
        }
    }

    private fun setItemsClickable(
        recyclerView: RecyclerView,
        isClickable: Boolean
    ) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    private fun drawButtons(
        c: Canvas,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val itemView = viewHolder.itemView
        val left = itemView.left
        val right = itemView.right
        val top = itemView.top
        val bottom = itemView.bottom

        leftButton = RectF(
            left.toFloat(),
            top.toFloat(),
            (left + displayWidth * (eachItemWidth * leftMenuItems.size)),
            bottom.toFloat()
        )

        rightButton = RectF(
            (right - displayWidth * (eachItemWidth * rightMenuItems.size)),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat()
        )

        drawItem(c, itemView)

        buttonInstance = null
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
        }
    }

    private fun drawItem(c: Canvas, itemView: View) {
        val left = itemView.left
        val top = itemView.top
        val bottom = itemView.bottom

        for ((index, item) in allItems.withIndex()) {


            if (buttonShowedState == ButtonsState.LEFT_VISIBLE && item.buttonState  == SwipeState.RIGHT ) {
                return
            }

            /*else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE && item.buttonState  == SwipeState.LEFT) {
                return
            }*/


            val backgroundRect = RectF(
                left.toFloat() + index * (displayWidth * eachItemWidth),
                top.toFloat(),
                (left + (index + 1) * (displayWidth * eachItemWidth)),
                bottom.toFloat()
            )

            val paint = Paint()

            item.backgroundImage?.let {

                try {
                    it.height = if (backgroundRect.top > backgroundRect.bottom) {
                        (backgroundRect.top - backgroundRect.bottom).roundToInt()
                    } else {
                        (backgroundRect.bottom - backgroundRect.top).roundToInt()
                    }

                    it.width = ((displayWidth * eachItemWidth).roundToInt())
                } catch (e: Exception) {

                }
                c.drawBitmap(it, backgroundRect.left, backgroundRect.top, null)

            } ?: kotlin.run {

                paint.color = Color.parseColor(item.backgroundColor)

                c.drawRoundRect(backgroundRect, 0f, 0f, paint)
            }


            var textHeight = 0f

            item.text?.let {
                if (!it.isBlank() || it.isNotEmpty()) {
                    textHeight = item.textHeight
                }
            }

            allItems[index].clickableArea = backgroundRect

            item.iconBitmap?.let {

                var a : Bitmap? = null
                try {
                    if (item.iconWidth > 0 && item.iconHeight > 0) {
                        a = ImageUtils().getResizedBitmap(it,item.iconWidth,item.iconHeight)
                    }
                } catch (e: java.lang.Exception) {

                }

                val iconRect = RectF(
                    backgroundRect.centerX() - (it.width / 2),
                    backgroundRect.centerY() - (it.height / 2) - textHeight,
                    backgroundRect.centerX() + (it.width / 2),
                    backgroundRect.centerY() + (it.height / 2) + textHeight
                )
                c.drawBitmap(a?:it, iconRect.left, iconRect.top, null)

                item.text?.let { text ->
                    if (!text.isBlank() || text.isNotEmpty()) {
                        drawText(text, c, iconRect, paint, textHeight, item.textColor)
                    }
                }
            }

        }

    }

    private fun drawText(
        text: String,
        c: Canvas,
        button: RectF,
        p: Paint,
        height: Float,
        color: String
    ) {
        p.isAntiAlias = true
        p.textSize = height
        p.isFakeBoldText = true
        p.color = Color.parseColor(color)
        c.drawText(text, button.left + height, button.bottom, p)
    }

    fun onDraw(canvas: Canvas) {
        if (currentItemViewHolder != null) {
            drawButtons(canvas, currentItemViewHolder!!)
        }
    }

}