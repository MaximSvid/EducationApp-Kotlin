package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.databinding.ItemQuestionAnswerBinding

class QuestionAnswerAdapter (
    private val dataset: List<*>,
) : RecyclerView.Adapter<QuestionAnswerAdapter.ItemViewHolder>(){

    inner class ItemViewHolder (val binding: ItemQuestionAnswerBinding): RecyclerView.ViewHolder (binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAnswerAdapter.ItemViewHolder {
        val binding = ItemQuestionAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionAnswerAdapter.ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}