package com.natkarock.flowweatherapp.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

//fun showSnackbar(root: View, text: String, duration: Int = 3000) {
//    val snackbar = Snackbar.make(root, text, duration)
//    snackbar.show()
//}

fun showSnackbar(view: View, text: String, duration: Int = 1000){
    val snackBar = Snackbar.make(view, text,
        duration).setAction("Action", null)
    val snackBarView = snackBar.view
    val textView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.textSize = 14f
    val view: View = snackBar.view
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.BOTTOM
    view.layoutParams = params
    snackBar.show()
}