package com.micosi.moviesseries.ui.providers

import android.app.AlertDialog
import android.content.Context

class DeleteDialogProvider(val context: Context, val type: String) {

    fun show(positiveResult: () -> Unit, movieTitle: String) {

        val builder = AlertDialog.Builder(context)

        builder.setTitle("Removing $type")
        builder.setMessage("Are you sure you want to delete ${movieTitle}?")
        builder.setCancelable(false)
        builder.setNegativeButton("NO") { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton("YES") { _, _ -> positiveResult() }

        builder.create().show()
    }
}