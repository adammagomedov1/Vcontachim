package com.example.vcontachim.utility

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackbar(text: String) {
    val snackbar = Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    )
    snackbar.show()
}

fun Fragment.toast(text: String) {
    val toast = Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    )
    toast.show()
}