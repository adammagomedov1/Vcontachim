package com.example.vcontachim.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.ActivityMainBinding
import com.example.vcontachim.VcontachimApplication
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.main_fragment_container_view)

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout: View = findViewById(R.id.main_layout)
        binding = ActivityMainBinding.bind(rootLayout)

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.mainScreen())
        }

        binding!!.mainBottomNavigation.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                if (item.itemId == R.id.menu_home_bottom_nav) {
                    VcontachimApplication.router.replaceScreen(Screens.mainScreen())
                }
                if (item.itemId == R.id.menu_profile_bottom_nav) {
                    VcontachimApplication.router.replaceScreen(Screens.profileScreen())
                }
                if (item.itemId == R.id.menu_people_search_nav) {
                    VcontachimApplication.router.navigateTo(Screens.searchScreen())
                }

                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        VcontachimApplication.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        VcontachimApplication.navigatorHolder.removeNavigator()
    }
}