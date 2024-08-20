package com.example.educationappmaximsvidrak

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.educationappmaximsvidrak.databinding.ActivityMainBinding

//import com.example.educationappmaximsvidrak.adapter.ChatGPTAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var drawerNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

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

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerViewBottom.findNavController().navigateUp()
            }
        })


        // боковая навигация

        val drawerNavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_drawer) as NavHostFragment
        drawerNavController = drawerNavHostFragment.navController
        binding.drawerNavView.setupWithNavController(drawerNavController)

        binding.drawerNavView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            drawerNavController.navigate(menuItem.itemId)
            true
        }



//        val drawerNavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewBottom) as NavHostFragment
//        binding.drawerNavView.setupWithNavController(drawerNavHostFragment.navController)
//
//        binding.drawerNavView.setNavigationItemSelectedListener { menuItem ->
//            binding.drawerLayout.closeDrawers(GravityCompat.START)
//            drawerNavController.navigate(menuItem.itemId)
//            true
//        }

    }

}
