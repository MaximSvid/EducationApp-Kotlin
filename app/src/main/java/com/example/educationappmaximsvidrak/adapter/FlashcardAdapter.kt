package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.databinding.ItemFlashcardBinding
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.ui.FlashcardFragmentDirections

class FlashcardAdapter (
    private val flashcards: List<FlashcardData>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<FlashcardAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemFlashcardBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlashcardAdapter.ItemViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashcardAdapter.ItemViewHolder, position: Int) {
        val flashcard = flashcards[position]
        val binding = holder.binding

        binding.tvQuestion.text = flashcard.question
        binding.tvAnswer.text = flashcard.answer

        binding.ibDelete.setOnClickListener {
            viewModel.deleteFlashcard(flashcard)
        }

        binding.mcvFlashcard.setOnClickListener {
            viewModel.selectedFlashcard(flashcard)

            holder.itemView.findNavController().navigate(FlashcardFragmentDirections.actionFlashcardFragmentToFlashcardDetailFragment())
        }
    }

    override fun getItemCount(): Int {
     return flashcards.size
    }

}