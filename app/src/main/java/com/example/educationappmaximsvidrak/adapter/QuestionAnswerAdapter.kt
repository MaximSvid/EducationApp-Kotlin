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
    private var flashcards: List<FlashcardData>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private val questionType = 1
    private val answerType = 2

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) questionType else answerType
    }

    inner class QuestionViewHolder(private val questionBinding: ItemQuestionBinding): RecyclerView.ViewHolder(questionBinding.root) {
        fun bind (flashcard: FlashcardData) {
            questionBinding.tvQuestion.text = flashcard.question
//

            questionBinding.mcvQuestion.setOnClickListener {
                val answerPosition = adapterPosition + 1
                if (answerPosition < itemCount) {
                    recyclerView.smoothScrollToPosition(answerPosition)
                }
            }
        }
    }

    inner class AnswerViewHolder (private val answerBinding: ItemAnswerBinding): RecyclerView.ViewHolder (answerBinding.root) {
        fun bind (flashcard: FlashcardData) {
            answerBinding.tvAnswer.text = flashcard.answer
//
            answerBinding.ivBackToQuestion.setOnClickListener {
                val  questionPosition = adapterPosition -1
                if (questionPosition >= 0) {
                    recyclerView.smoothScrollToPosition(questionPosition)
                }

            }

            answerBinding.ivForwardToNewQuestion.setOnClickListener {
                val nextQuestion = adapterPosition + 1
                if (nextQuestion < itemCount) {
                   recyclerView.smoothScrollToPosition(nextQuestion)
                }
                else {
                    Toast.makeText(answerBinding.root.context, "You've learned all the cards", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
       return if ((viewType == questionType)) {
           val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           QuestionViewHolder(binding)
       } else {
           val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           AnswerViewHolder(binding)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentCard = flashcards[position / 2]
        if (getItemViewType(position) == questionType) {
            (holder as QuestionViewHolder).bind(currentCard)
        } else {
            (holder as AnswerViewHolder).bind(currentCard)
        }


    }

    override fun getItemCount(): Int {
        return flashcards.size * 2
    }

    fun updateData(newFlashcards: List<FlashcardData>) {
        flashcards = newFlashcards
        notifyDataSetChanged()
    }
}