package com.example.educationappmaximsvidrak.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.FlashcardAdapter
import com.example.educationappmaximsvidrak.databinding.FragmentFlashcardBinding


class FlashcardFragment : Fragment() {

    private lateinit var binding: FragmentFlashcardBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlashcardBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.flashcardList.observe(viewLifecycleOwner) {
            binding.rvFlashcard.adapter = FlashcardAdapter(it, viewModel)
        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(FlashcardFragmentDirections.actionFlashcardFragmentToHomeFragment())
        }

        binding.btnNewFlashcard.setOnClickListener {

        }
    }

    private fun showAlertDialog(context: Context) {

    }


}