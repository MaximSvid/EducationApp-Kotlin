package com.example.educationappmaximsvidrak.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.MainActivity
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val folderList = mutableListOf<String>()
    private val viewModelLogin: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAnim()


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddQuestionFragment())
        }

        binding.btnStart.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStartFragment())
        }

        binding.mcvStartClick.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStartFragment())
        }

        binding.btnChatGPT.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatGPTFragment())
        }

        binding.btnFolders.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFolderFragment())
        }


        viewModel.folderList.observe(viewLifecycleOwner) {
            folderList.clear()
            folderList.addAll(it.map { it.name })
        }



    }

    private fun showAnim() {
        val animation = binding.lavHomeAnim
        animation.repeatCount = LottieDrawable.INFINITE
        animation.playAnimation()
    }




}

