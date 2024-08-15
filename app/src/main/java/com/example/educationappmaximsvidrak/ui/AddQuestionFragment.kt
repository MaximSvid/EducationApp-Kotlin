package com.example.educationappmaximsvidrak.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentAddQuestionBinding
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder


class AddQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAddQuestionBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener {
            findNavController().navigate(AddQuestionFragmentDirections.actionAddQuestionFragmentToHomeFragment())
        }

        binding.btnSave.setOnClickListener {
            val question = binding.tietQuestion.text.toString()
            val answer = binding.tietAnswer.text.toString()
            val selectedFolder = viewModel.selectedFolder.value

            if (question.isNotEmpty() && answer.isNotEmpty() && selectedFolder != null) {

                    val newFlashcard = FlashcardData(
                        question = question,
                        answer = answer,
                        folderId = selectedFolder.id
                    )
                    viewModel.addFlashcard(newFlashcard)
                    Toast.makeText(context, "Card Saved", Toast.LENGTH_SHORT).show()

                    binding.tietQuestion.text?.clear()
                    binding.tietAnswer.text?.clear()
                } else {
                    Toast.makeText(context, "Enter a Folder and fill in all fields", Toast.LENGTH_SHORT)
                        .show()

            }


        }
        binding.tvFolder.setOnClickListener {
            showPopupMenu(it)
        }


    }

    private fun showPopupMenu(view: View) {
        val popupMenu = android.widget.PopupMenu(context, view)

        // Добавляем папки в меню
        viewModel.folderList.value?.forEach { folder ->
            popupMenu.menu.add(0, folder.id.toInt(), 0, folder.name)
        }
        // Добавляем кнопку "Добавить папку" в конце
        popupMenu.menu.add(1, viewModel.folderList.value?.size ?: 0, 1, "Add a folder")

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.groupId == 1) {
                showAlertDialog(requireContext())
            } else {
                val selectedFolder = viewModel.folderList.value?.find { it.id.toInt() == menuItem.itemId }
                if (selectedFolder != null) {
                    viewModel.selectFolder(selectedFolder)
                    binding.tvFolder.text = selectedFolder.name
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun showAlertDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_new_folder, null)
        dialogBuilder.setView(dialogView)

        val folderEditText = dialogView.findViewById<EditText>(R.id.alert_new_folder)

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
                val newFolder = Folder(name = nameFolder)
                viewModel.addFolder(newFolder)
                Toast.makeText(context, "Folder $nameFolder added", Toast.LENGTH_SHORT).show()

                //закрываем диалог
                alertDialog.dismiss()

                // Обновляем меню после добавления новой папки
                showPopupMenu(binding.tvFolder)
            }
        }


    }


}