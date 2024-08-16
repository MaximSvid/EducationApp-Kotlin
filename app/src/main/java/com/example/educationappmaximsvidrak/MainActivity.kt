package com.example.educationappmaximsvidrak

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.educationappmaximsvidrak.databinding.ActivityMainBinding
import com.example.educationappmaximsvidrak.ui.LoginFragment

//import com.example.educationappmaximsvidrak.adapter.ChatGPTAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
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
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })

//        if (savedInstanceState == null) {
//            val fragment = LoginFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, fragment)
//                .commit()
//        }
    }




}