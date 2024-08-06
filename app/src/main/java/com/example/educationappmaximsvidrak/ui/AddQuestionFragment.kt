package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentAddQuestionBinding
import com.example.educationappmaximsvidrak.model.FlashcardData


class AddQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAddQuestionBinding
    private val viewModel: MainViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
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

            if (question.isNotEmpty() && answer.isNotEmpty()) {
                val newFlashcard = FlashcardData (
                    question = question,
                    answer =  answer
                )
                viewModel.addFlashcard(newFlashcard)
                Toast.makeText(context, "Card Saved", Toast.LENGTH_SHORT).show()

                binding.tietQuestion.text?.clear()
                binding.tietAnswer.text?.clear()
            } else {
                Toast.makeText(context, "The fields must be filled in", Toast.LENGTH_SHORT).show()
            }

        }



    }


}