package com.micosi.moviesseries.ui.extensions

import com.micosi.moviesseries.ui.providers.SnackBarProvider

fun SnackBarProvider.showSnackBar(success: Boolean, message: String) {
    if (success) {
        this.showSuccess(message)
    } else {
        this.showError(message)
    }
}