package com.example.educationappmaximsvidrak.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.FlashcardAdapter
import com.example.educationappmaximsvidrak.databinding.FragmentFlashcardBinding
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.google.android.material.textfield.TextInputEditText


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
            findNavController().navigate(FlashcardFragmentDirections.actionFlashcardFragmentToFolderFragment())
        }

        binding.btnNewFlashcard.setOnClickListener {
            context?.let { it1 -> showAlertDialog(it1) }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAlertDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_new_flashcard, null)
        dialogBuilder.setView(dialogView)

        val question = dialogView.findViewById<TextInputEditText>(R.id.alert_question)
        val answer = dialogView.findViewById<TextInputEditText>(R.id.alert_answer)

        dialogBuilder.setTitle("Add new flashcard")
        dialogBuilder.setPositiveButton("Save") {_,_ ->}
        dialogBuilder.setNegativeButton("Cancel") {dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val questionText = question.text.toString()
            val answerText = answer.text.toString()

            if (questionText.isBlank() || answerText.isBlank()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val updateFlashcard = FlashcardData (
                    question = questionText,
                    answer = answerText, folderId = 0
                )
                viewModel.addFlashcard(updateFlashcard)
                alertDialog.dismiss()
            }
        }


    }


}