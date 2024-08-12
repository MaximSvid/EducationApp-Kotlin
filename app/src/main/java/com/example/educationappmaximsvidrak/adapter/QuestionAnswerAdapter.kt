package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.ItemAnswerBinding
import com.example.educationappmaximsvidrak.databinding.ItemQuestionBinding
import com.example.educationappmaximsvidrak.model.FlashcardData

class QuestionAnswerAdapter (
    private val flashcards: List<FlashcardData>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val TYPE_QUESTION = 0
        private const val TYPE_ANSWER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) TYPE_QUESTION else TYPE_ANSWER
    }

    inner class QuestionViewHolder(private val binding: ItemQuestionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (flashcard: FlashcardData) {
            binding.tvQuestion.text = flashcard.question
            binding.mcvQuestion.strokeWidth = 10
            binding.mcvQuestion.strokeColor = binding.root.context.getColor(R.color.blue)


            binding.mcvQuestion.setOnClickListener {
                val answerPosition = adapterPosition + 1
                if (answerPosition < itemCount) {
                    recyclerView.smoothScrollToPosition(answerPosition)
                }
//                notifyItemChanged(answerPosition)
//                (binding.root.context as? RecyclerView)?.smoothScrollToPosition(answerPosition)
            }
        }
    }

    inner class AnswerViewHolder (private val binding: ItemAnswerBinding): RecyclerView.ViewHolder (binding.root) {
        fun bind (flashcard: FlashcardData) {
            binding.tvAnswer.text = flashcard.answer
            binding.mcvAnswer.strokeWidth = 10
            binding.mcvAnswer.strokeColor = binding.root.context.getColor(R.color.green)

            binding.ivBackToQuestion.setOnClickListener {
                val  questionPosition = adapterPosition -1
                if (questionPosition >= 0) {
                    recyclerView.smoothScrollToPosition(questionPosition)
                }

            }

            binding.ivForwardToNewQuestion.setOnClickListener {
                val nextQuestion = adapterPosition + 1
                if (nextQuestion < itemCount) {
                   recyclerView.smoothScrollToPosition(nextQuestion)
                }
                else {
                    Toast.makeText(binding.root.context, "You've learned all the cards", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
       return if ((viewType == TYPE_QUESTION)) {
           val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           QuestionViewHolder(binding)
       } else {
           val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           AnswerViewHolder(binding)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentCard = flashcards[position / 2]
        if (getItemViewType(position) == TYPE_QUESTION) {
            (holder as QuestionViewHolder).bind(currentCard)
        } else {
            (holder as AnswerViewHolder).bind(currentCard)
        }




    }

    override fun getItemCount(): Int {
        return flashcards.size * 2
    }
}