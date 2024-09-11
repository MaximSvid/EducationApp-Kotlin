package com.example.educationappmaximsvidrak

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.educationappmaximsvidrak.databinding.ActivityMainBinding
import com.example.educationappmaximsvidrak.ui.AddQuestionFragment
import com.example.educationappmaximsvidrak.ui.ChatGPTFragment
import com.example.educationappmaximsvidrak.ui.FolderFragment
import com.example.educationappmaximsvidrak.ui.HomeFragment
import com.example.educationappmaximsvidrak.ui.LoginFragment
import com.example.educationappmaximsvidrak.ui.StartFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewBottom) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatGPTFragment, R.id.startFragment, R.id.addQuestionFragment, R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }

                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        // rückwärts
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerViewBottom.findNavController().navigateUp()
            }
        })





    }




}
