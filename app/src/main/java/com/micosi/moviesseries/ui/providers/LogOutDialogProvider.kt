package com.micosi.moviesseries.ui.providers

import android.app.AlertDialog
import android.content.Context

class LogOutDialogProvider(val context: Context) {

    fun show(positiveResult: () -> Unit) {

        val builder = AlertDialog.Builder(context)

        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setCancelable(false)
        builder.setNegativeButton("NO") { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton("YES") { _, _ -> positiveResult() }

        builder.create().show()
    }
}