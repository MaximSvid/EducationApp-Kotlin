package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.databinding.ItemQuestionAnswerBinding
import com.example.educationappmaximsvidrak.model.FlashcardData

class QuestionAnswerAdapter (
    private val flashcards: List<FlashcardData>,
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
        val currentCard = flashcards[position]
        val binding = holder.binding

        binding.tvQuestion.text = currentCard.question
        binding.tvQuestion.text = currentCard.answer
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }
}