package com.elano.spotify.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Jess on 1/5/2018.
 */
class RecyclerTouchListener(context: Context?, clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

    private val mContext = context; private val mClickListener = clickListener

    private val gestureDetector = GestureDetector(mContext, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean = true
    })

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv?.findChildViewUnder(e!!.x, e.y)

        if (mClickListener != null && child != null && gestureDetector.onTouchEvent(e)) {
            mClickListener.onClick(child, rv.getChildAdapterPosition(child))
            return true
        }

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    interface ClickListener {
        fun onClick(view: View, position: Int)
    }
}