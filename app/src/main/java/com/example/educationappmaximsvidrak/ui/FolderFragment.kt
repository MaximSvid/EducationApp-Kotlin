package com.example.educationappmaximsvidrak.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.FolderAdapter
import com.example.educationappmaximsvidrak.adapter.FolderAdapter.*
import com.example.educationappmaximsvidrak.databinding.FragmentFolderBinding
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder
import com.google.android.material.textfield.TextInputEditText
import java.util.Collections

class FolderFragment : Fragment() {

    private lateinit var binding: FragmentFolderBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFolderBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        viewModel.folderList.observe(viewLifecycleOwner) { folder ->
            binding.rvFolder.adapter = FolderAdapter(folder, viewModel)
            val myAdapter = FolderAdapter(folder, viewModel)

            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    source: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val sourcePosition = source.bindingAdapterPosition
                    val targetPosition = target.bindingAdapterPosition
                    Collections.swap(folder, sourcePosition, targetPosition)
                    myAdapter.notifyItemMoved(sourcePosition, targetPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    myAdapter.deleteItem(viewHolder.bindingAdapterPosition)
                }

            })

            itemTouchHelper.attachToRecyclerView(binding.rvFolder)


        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(FolderFragmentDirections.actionFolderFragmentToHomeFragment())
        }

        binding.btnNewFolder.setOnClickListener {
            context?.let { it1 -> showAlertDialog(it1) }
        }



    }

    @SuppressLint("MissingInflatedId")
    private fun showAlertDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_new_folder, null)
        dialogBuilder.setView(dialogView)

        val nameOfFolder = dialogView.findViewById<TextInputEditText>(R.id.alert_folder_name)

        dialogBuilder.setTitle("Add new Folder")
        dialogBuilder.setPositiveButton("Save") { _, _ -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val folderName = nameOfFolder.text.toString()

            if (folderName.isBlank()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val updateFolder = Folder(
                    name = folderName
                )
                viewModel.addFolder(updateFolder)
                alertDialog.dismiss()
            }
        }


    }


}