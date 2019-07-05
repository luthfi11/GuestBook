package com.luthfi.guestbook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luthfi.guestbook.R
import com.luthfi.guestbook.ui.event.EventFragment
import com.luthfi.guestbook.ui.export.ExportFragment
import com.luthfi.guestbook.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeView(HomeFragment())
            }
            R.id.navigation_event -> {
                changeView(EventFragment())
            }
            R.id.navigation_export -> {
                changeView(ExportFragment())
            }
        }
        true
    }

    private fun changeView(fragment: Fragment) {
        fm.beginTransaction().replace(R.id.content, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) changeView(HomeFragment())
    }
}
