package com.example.vcontachim

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper {
    private var sharedPreferences: SharedPreferences =
        VcontachimApplication.context.getSharedPreferences(
            "vcontachim",
            Context.MODE_PRIVATE
        )

    var tookToken: String? = sharedPreferences.getString("auth", null)

    val editor: SharedPreferences.Editor = sharedPreferences.edit()

}