package com.example.vcontachim.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.models.ItemVideo
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.io.Serializable

class VideoActivity : AppCompatActivity(R.layout.activity_video) {
    private val navigator = AppNavigator(this, R.id.fragment_video_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoSerializable: Serializable? = intent?.getSerializableExtra(SAVE_VIDEO_KEY)!!
        val video: ItemVideo? = videoSerializable as? ItemVideo

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.videoComment(video!!))
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
        private const val SAVE_VIDEO_KEY = "videoActivity"

        fun createIntent(context: Context, video: ItemVideo): Intent {
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(SAVE_VIDEO_KEY, video)

            return intent
        }
    }
}