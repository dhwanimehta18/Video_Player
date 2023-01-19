package com.silverorange.videoplayer.utils

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.silverorange.videoplayer.R

object AppAlert {

}

fun Activity.showError(view: View?, message: Int) {
    view ?: return
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.error_message))
    snackbar.show()
}

fun Activity.showSuccess(view: View?, message: Int) {
    view ?: return
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.success_message))
    snackbar.show()
}

fun Activity.showError(view: View?, message: String) {
    view ?: return
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.error_message))
    snackbar.show()
}

fun Activity.showSuccess(view: View?, message: String) {
    view ?: return
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackbar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.success_message))
    snackbar.show()
}
