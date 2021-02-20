package com.natkarock.flowweatherapp.util


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setToolbar(toolbar: Toolbar){
    toolbar.title = ""
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    setHasOptionsMenu(true)
}