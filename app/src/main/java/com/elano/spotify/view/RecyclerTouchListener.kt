package com.elano.spotify.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Storm-2 on 1/5/2018.
 */
class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener) : RecyclerView.OnItemTouchListener {

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean = true

        override fun onLongPress(e: MotionEvent?) {
            val child = recyclerView.findChildViewUnder(e!!.x, e.y)

            if (child != null)
                clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
        }
    })

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        TODO("not implemented")
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv!!.findChildViewUnder(e!!.x, e.y)

        if (child != null && gestureDetector.onTouchEvent(e))
            clickListener.onClick(child, rv.getChildAdapterPosition(child))

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("not implemented")
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }
}