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
import com.example.educationappmaximsvidrak.databinding.FragmentFlashcardDetailBinding
import com.example.educationappmaximsvidrak.model.FlashcardData


class FlashcardDetailFragment : Fragment() {

    private lateinit var binding: FragmentFlashcardDetailBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlashcardDetailBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedCard.observe(viewLifecycleOwner) {
            binding.tietQuestion.setText(it.question)
            binding.tietAnswer.setText(it.answer)
        }

        binding.btnSave.setOnClickListener {
            val questionText = binding.tietQuestion.text.toString()
            val answerText = binding.tietAnswer.text.toString()
            if (questionText.isNotEmpty() && answerText.isNotEmpty()) {
                val updateFlashcard = FlashcardData(
                    viewModel.selectedCard.value!!.id,
                    questionText,
                    answerText,
                    folderId = viewModel.selectedFolder.value!!.id
                )
                viewModel.updateFlashcard(updateFlashcard)
                findNavController().navigate(FlashcardDetailFragmentDirections.actionFlashcardDetailFragmentToFlashcardFragment())
            } else {
                Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(FlashcardDetailFragmentDirections.actionFlashcardDetailFragmentToFlashcardFragment())
        }
    }


}