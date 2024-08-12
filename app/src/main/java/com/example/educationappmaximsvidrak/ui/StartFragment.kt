package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.QuestionAnswerAdapter
import com.example.educationappmaximsvidrak.customElements.NonScrollableLinearLayoutManager
import com.example.educationappmaximsvidrak.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToHomeFragment())
        }

        viewModel.flashcardList.observe(viewLifecycleOwner) { flashcards ->
            val adapter = QuestionAnswerAdapter (flashcards, binding.rvQuestionAnswer )

            binding.rvQuestionAnswer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvQuestionAnswer.adapter = adapter
        }





    }

}

