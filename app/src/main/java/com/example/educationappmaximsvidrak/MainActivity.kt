package com.example.educationappmaximsvidrak

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.educationappmaximsvidrak.databinding.ActivityMainBinding
import com.example.educationappmaximsvidrak.ui.AddQuestionFragment
import com.example.educationappmaximsvidrak.ui.ChatGPTFragment
import com.example.educationappmaximsvidrak.ui.FolderFragment
import com.example.educationappmaximsvidrak.ui.LoginFragment
import com.example.educationappmaximsvidrak.ui.StartFragment
import com.google.android.material.navigation.NavigationView

//import com.example.educationappmaximsvidrak.adapter.ChatGPTAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

//    private lateinit var fragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

//        binding.drawerNavView.setNavigationItemSelectedListener(this)

        //нижняя навигация
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

        // возврат назад
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerViewBottom.findNavController().navigateUp()
            }
        })




        // боковая навигация

        val drawerNavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_drawer) as NavHostFragment
        binding.drawerNavView.setupWithNavController(drawerNavHostFragment.navController)

        drawerNavHostFragment.navController.addOnDestinationChangedListener {_, destination, _ ->
            binding.drawerNavView.visibility = View.VISIBLE
        }



        // возврат назад
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerViewDrawer.findNavController().navigateUp()
            }
        })



    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.homeFragment -> openFragment(StartFragment())
//            R.id.homeFragment -> openFragment(AddQuestionFragment())
//            R.id.homeFragment -> openFragment(ChatGPTFragment())
//            R.id.homeFragment -> openFragment(FolderFragment())
//            R.id.homeFragment -> openFragment(LoginFragment())
//        }
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressedDispatcher.onBackPressed()
//        }
//    }
//
//    private fun openFragment (fragment: Fragment) {
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_container_view_drawer, fragment)
//        fragmentTransaction.commit()
//    }


}
