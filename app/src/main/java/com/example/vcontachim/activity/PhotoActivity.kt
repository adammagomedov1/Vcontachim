package com.example.vcontachim.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemPhotos
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.io.Serializable

class PhotoActivity : AppCompatActivity(R.layout.activity_photo) {
    private val navigator = AppNavigator(this, R.id.fragment_photo_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoSerializable: Serializable? = intent?.getSerializableExtra(SAVE_PHOTOS_KEY)
        val photo: ItemPhotos? = photoSerializable as? ItemPhotos

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.photo(photo!!))
        }
    }

    override fun onResume() {
        super.onResume()
        VcontachimApplication.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        VcontachimApplication.navigatorHolder.removeNavigator()
    }

    companion object {
        private const val SAVE_PHOTOS_KEY = "photo"

        fun createIntent(context: Context, photos: ItemPhotos): Intent {
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra(SAVE_PHOTOS_KEY, photos)

            return intent
        }
    }
}