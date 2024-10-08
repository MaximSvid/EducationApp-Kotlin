package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.databinding.ItemFoldersBinding
import com.example.educationappmaximsvidrak.model.Folder
import com.example.educationappmaximsvidrak.ui.FolderFragmentDirections

class FolderAdapter(
    private val folders: List<Folder>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<FolderAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemFoldersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FolderAdapter.ItemViewHolder {
        val binding = ItemFoldersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderAdapter.ItemViewHolder, position: Int) {
        val folder = folders[position]
        val binding = holder.binding

        binding.tvFolder.text = folder.name

        binding.ibDelete.setOnClickListener {
            viewModel.deleteFolder(folder)
        }

        binding.mcvFolder.setOnClickListener {
            viewModel.selectFolder1(folder)
            holder.itemView.findNavController().navigate(FolderFragmentDirections.actionFolderFragmentToFlashcardFragment())
        }
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    fun deleteItem(bindingAdapterPosition: Int) {

        viewModel.deleteFolder(folders[bindingAdapterPosition])
        notifyItemRemoved(bindingAdapterPosition)
//        notifyItemRangeChanged(bindingAdapterPosition, folders.size - bindingAdapterPosition)
    }

}