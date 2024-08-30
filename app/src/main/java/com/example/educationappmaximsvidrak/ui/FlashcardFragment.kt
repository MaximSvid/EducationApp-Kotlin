package com.example.educationappmaximsvidrak.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.FlashcardAdapter
import com.example.educationappmaximsvidrak.databinding.FragmentFlashcardBinding
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder
import com.google.android.material.textfield.TextInputEditText
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Collections


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

        // Подписываемся на список заметок, связанных с выбранной папкой
        viewModel.getFlashcardsBySelectedFolder().observe(viewLifecycleOwner) {flashcards ->
            val myAdapter = FlashcardAdapter(flashcards, viewModel)
            binding.rvFlashcard.adapter = myAdapter

//            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//                ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    source: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    val sourcePosition = source.bindingAdapterPosition
//                    val targetPosition = target.bindingAdapterPosition
//                    Collections.swap(flashcards, sourcePosition, targetPosition)
//                    myAdapter.notifyItemMoved(sourcePosition, targetPosition)
//                    return true
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    myAdapter.deleteItem(viewHolder.bindingAdapterPosition)
//
//                }
//
//                override fun onChildDraw(
//                    c: Canvas,
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    dX: Float,
//                    dY: Float,
//                    actionState: Int,
//                    isCurrentlyActive: Boolean
//                ) {
//
//                    RecyclerViewSwipeDecorator.Builder(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                        .addBackgroundColor(
//                            ContextCompat.getColor(
//                                requireContext(),
//                                R.color.my_background
//                            )
//                        )
//                        .addActionIcon(R.drawable.delete_black_icon)
//                        .create()
//                        .decorate()
//                    super.onChildDraw(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                }
//
//            })
//
//            itemTouchHelper.attachToRecyclerView(binding.rvFlashcard)

        }



//        viewModel.flashcardList.observe(viewLifecycleOwner) {
//            binding.rvFlashcard.adapter = FlashcardAdapter(it, viewModel)
//        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(FlashcardFragmentDirections.actionFlashcardFragmentToFolderFragment())
        }

        binding.btnNewFlashcard.setOnClickListener {
            context?.let { it1 -> showAlertDialog(it1) }
        }

        //показывается название текущей папки в заголовке
        val selectedFolder = viewModel.selectedFolder.value
        binding.tvFlashcard.text = selectedFolder?.name
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
        dialogBuilder.setPositiveButton("Save") { _, _ -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
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
                val selectedFolder = viewModel.selectedFolder.value
                if (selectedFolder != null) {
                    val updateFlashcard = FlashcardData(
                        question = questionText,
                        answer = answerText,
                        folderId = selectedFolder.id,
                        studyDate = System.currentTimeMillis() // Сохранение текущей даты в миллисекундах
                    )
                    viewModel.addFlashcard(updateFlashcard)
                    alertDialog.dismiss()
                }
            }
        }
    }


}