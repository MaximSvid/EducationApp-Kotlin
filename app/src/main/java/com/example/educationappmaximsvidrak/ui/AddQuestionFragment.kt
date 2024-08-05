package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentAddQuestionBinding


class AddQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAddQuestionBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }


}