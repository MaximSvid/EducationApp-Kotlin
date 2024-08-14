package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.databinding.ItemFoldersBinding
import com.example.educationappmaximsvidrak.model.Folder

class FolderAdapter (
    private val folders: List<Folder>
): RecyclerView.Adapter<FolderAdapter.ItemViewHolder>() {

    inner class ItemViewHolder (val binding: ItemFoldersBinding): RecyclerView.ViewHolder(binding.root)
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

        binding.mcvFolder.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return folders.size
    }

}