package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.databinding.ItemAnswerBinding
import com.example.educationappmaximsvidrak.databinding.ItemQuestionBinding
import com.example.educationappmaximsvidrak.model.FlashcardData

class QuestionAnswerAdapter (
    private var flashcards: List<FlashcardData>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private val questionType = 1
    private val answerType = 2

    private var currentPosition = 0


    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) questionType else answerType
    }

    inner class QuestionViewHolder(private val questionBinding: ItemQuestionBinding): RecyclerView.ViewHolder(questionBinding.root) {
        fun bind (flashcard: FlashcardData) {
            questionBinding.tvQuestion.text = flashcard.question

            questionBinding.mcvQuestion.setOnClickListener {
                showAnswer()
            }

        }
    }

    inner class AnswerViewHolder (private val answerBinding: ItemAnswerBinding): RecyclerView.ViewHolder (answerBinding.root) {
        fun bind (flashcard: FlashcardData) {
            answerBinding.tvAnswer.text = flashcard.answer

            answerBinding.ivBackToQuestion.setOnClickListener {
                showQuestion()
            }

            answerBinding.ivForwardToNewQuestion.setOnClickListener {
                showNextCard()
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

    private fun showAnswer() {
        if (currentPosition < itemCount -1) {
            currentPosition++
            recyclerView.scrollToPosition(currentPosition)
        }
    }

    private fun showQuestion() {
        if (currentPosition > 0) {
            currentPosition--
            recyclerView.scrollToPosition(currentPosition)
        }
    }
    private fun showNextCard () {
        if (currentPosition < itemCount -2) {
            currentPosition +=1
            recyclerView.scrollToPosition(currentPosition)
        } else {
            Toast.makeText(recyclerView.context, "You've learned all the cards", Toast.LENGTH_SHORT).show()
        }
    }
}