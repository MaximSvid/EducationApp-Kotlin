package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentStartDetailBinding

class StartDetailFragment : Fragment() {

    private lateinit var binding: FragmentStartDetailBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartDetailBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener {
            findNavController().navigate(StartDetailFragmentDirections.actionStartDetailFragmentToHomeFragment())
        }

        viewModel.flashcardList.observe(viewLifecycleOwner) {flashcards ->
            if (flashcards.isNotEmpty()) {
                binding.tvAnswer.text = flashcards[0].question
            } else {
                binding.tvAnswer.text = R.string.no_card.toString()
            }
        }

        binding.ivBackToQuestion.setOnClickListener {
            findNavController().navigateUp()
        }

//        binding.ivForwardToNewQuestion.setOnClickListener {
//            var index = viewModel.flashcardList.value.index
//            viewModel.flashcardList.observe(viewLifecycleOwner) {
//                if (it.isNotEmpty()) {
//                    binding.tvAnswer
//                }
//            }
//        }


    }

}