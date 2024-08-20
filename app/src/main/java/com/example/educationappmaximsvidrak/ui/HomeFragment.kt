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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.MainActivity
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val folderList = mutableListOf<String>()
    private val viewModelLogin: LoginViewModel by activityViewModels()

    private lateinit var drawerLayout: DrawerLayout // Добавляем это для доступа к DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Сообщаем, что у фрагмента есть меню
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
//        binding.toolbar.inflateMenu(R.menu.nav_menu)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем Toolbar как ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // Инициализация DrawerLayout из Activity
        drawerLayout = (activity as MainActivity).findViewById(R.id.drawer_layout)

        // Настройка кнопки меню для открытия DrawerLayout
        binding.ibMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.ibPlus

//        // Устанавливаем Toolbar как ActionBar
//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
//
//        binding.toolbar.setOnMenuItemClickListener{
//            when(it.itemId) {
//                R.id.homeFragment -> {
//                    findNavController().navigate(R.id.homeFragment)
//                    true
//                }
//                else -> false
//            }
//        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddQuestionFragment())
        }

        binding.btnStart.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStartFragment())
        }

        binding.btnChatGPT.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatGPTFragment())
        }

        binding.btnFolders.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFolderFragment())
        }

        binding.btnFolders.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFolderFragment())
        }

        viewModel.folderList.observe(viewLifecycleOwner) {
            folderList.clear()
            folderList.addAll(it.map { it.name })
        }

        binding.btnLogout.setOnClickListener {
            viewModelLogin.logout()
            Toast.makeText(
                context,
                "You have successfully logged out of your account",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }

//        binding.tvFolder.setOnClickListener { view ->
//            showPopupMenu(view)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)  // Загрузка меню в Toolbar
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Обработка нажатий на элементы меню
            R.id.homeFragment -> {
                findNavController().navigate(R.id.addQuestionFragment)
                // Действие при нажатии на элемент меню
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = android.widget.PopupMenu(context, view)

        // Добавляем папки в меню
        folderList.forEachIndexed { index, folderName ->
            popupMenu.menu.add(0, index, 0, folderName)
        }
        // Добавляем кнопку "Добавить папку" в конце
        popupMenu.menu.add(1, folderList.size, 1, "Add a folder")

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.groupId == 1) {
                showAlertDialog(requireContext())
            } else {
                Toast.makeText(requireContext(), "Open ${menuItem.title}", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
        popupMenu.show()
    }

    private fun showAlertDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.pop_menu_new_folder, null)
        dialogBuilder.setView(dialogView)

        val folderEditText = dialogView.findViewById<EditText>(R.id.pop_menu_new_folder)

        dialogBuilder.setTitle("Enter a folder name")
        dialogBuilder.setPositiveButton("Save") { _, _ -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val nameFolder = folderEditText.text.toString().trim()

            if (nameFolder.isBlank()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // добавляем новую папку в начало списка
                folderList.add(0, nameFolder)
                Toast.makeText(context, "Folder $nameFolder added", Toast.LENGTH_SHORT).show()

                //закрываем диалог
                alertDialog.dismiss()

                // Обновляем меню после добавления новой папки
//                showPopupMenu(binding.tvFolder)


            }
        }


    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        // Инфлейтинг меню, как в Activity
//        inflater.inflate(R.menu.nav_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                // Действия при нажатии на элемент меню
//                findNavController().navigate(R.id.addQuestionFragment)
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}