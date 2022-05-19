package com.micosi.moviesseries.ui.providers

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.micosi.moviesseries.R

class SnackBarProvider(private val activity: Activity) {

    fun showSuccess(message: String) {
        showSnackBar(message, R.color.green)
    }

    fun showError(message: String) {
        showSnackBar(message, R.color.red)
    }

    fun showSnackBar(message: String, @ColorRes color: Int) {
        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
        snackBar.view.setBackgroundColor(ContextCompat.getColor(activity, color))
        snackBar.show()
    }
}