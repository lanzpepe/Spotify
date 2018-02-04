package com.elano.spotify.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Jess on 1/5/2018.
 */
class RecyclerItemClickListener(context: Context?, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener, GestureDetector.SimpleOnGestureListener() {

    private val gestureDetector = GestureDetector(context, this)

    override fun onSingleTapUp(e: MotionEvent?): Boolean = true

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv?.findChildViewUnder(e!!.x, e.y)

        if (clickListener != null && child != null && gestureDetector.onTouchEvent(e))
            clickListener.onItemClick(child, rv.getChildAdapterPosition(child))

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }
}