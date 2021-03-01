package com.natkarock.flowweatherapp.ui.main

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mobile.finiza.app.data.exception.ClientException
import com.mobile.finiza.app.data.exception.UnauthorizedException
import com.mobile.finiza.app.data.exception.ValidationFieldException
import com.natkarock.flowweatherapp.R
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException

open class BaseFragment(@LayoutRes private val layoutId: Int) : Fragment(layoutId) {

    fun getValueFromException(e: Exception): String {
        Log.i("Exception", e.toString())
        val resources = requireContext().resources
        return when (e) {
            is ConnectException -> resources.getString(R.string.alert_no_internet_exception)
            is UnknownHostException ->
                resources.getString(R.string.alert_no_internet_exception)
            is UnauthorizedException -> resources.getString(R.string.alert_unauthorized_error)
            is ClientException -> e.errorTitle
            is ValidationFieldException -> e.errorTitle
            else -> resources.getString(R.string.alert_error)
        }
    }




}