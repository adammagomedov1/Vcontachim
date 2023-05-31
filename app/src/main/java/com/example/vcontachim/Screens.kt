package com.example.vcontachim

import android.content.Intent
import com.example.vcontachim.activity.LaunchActivity
import com.example.vcontachim.activity.MainActivity
import com.example.vcontachim.activity.PhotoActivity
import com.example.vcontachim.fragment.*
import com.example.vcontachim.models.*
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun splash() = FragmentScreen { SplashFragment() }

    fun accountLoginScreen() = FragmentScreen { AccountLoginScreenFragment() }

    fun auth() = FragmentScreen { AuthFragment() }

    fun main() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun mainScreen() = FragmentScreen { MainScreenFragment() }

    fun profileScreen() = FragmentScreen { ProfileFragment() }

    fun friends() = FragmentScreen { FriendsFragment() }

    fun communities() = FragmentScreen { CommunitiesFragment() }

    fun photoAlbums() = FragmentScreen { PhotoAlbumsFragment() }

    fun photos(photoAlbums: ItemPhotoAlbums) = FragmentScreen {
        PhotosFragment.createFragment(
            photoAlbums = photoAlbums
        )
    }

    fun launch() = ActivityScreen { Intent(it, LaunchActivity::class.java) }

    fun video() = FragmentScreen { VideoFragment() }

    fun photo(itemPhotos: ItemPhotos) =
        FragmentScreen { PhotoFragment.createFragment(photos = itemPhotos) }

    fun photoActivity(itemPhotos: ItemPhotos) =
        ActivityScreen { PhotoActivity.createIntent(photos = itemPhotos, context = it) }

    fun videoPlayback(itemVideo: ItemVideo) =
        FragmentScreen { VideoPlaybackFragment.createFragment(itemVideo) }

    fun photoComments(itemPhotos: ItemPhotos) =
        FragmentScreen { PhotoCommentsFragment.createFragment(itemPhotos) }

    fun videoComment(itemVideo: ItemVideo) =
        FragmentScreen { VideoCommentFragment.createFragment(itemVideo) }
}