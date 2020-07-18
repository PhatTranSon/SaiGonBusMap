package com.example.busmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get nav controller
        val navController = findNavController(R.id.fragment)

        //Set bottom nav
        bottomNavigationView.setupWithNavController(navController)

        //Set up action bar (if there is an action bar)
        //setupActionBarWithNavController(navController)
    }
}