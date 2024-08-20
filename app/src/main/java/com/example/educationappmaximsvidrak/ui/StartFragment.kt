package com.example.educationappmaximsvidrak.ui

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.QuestionAnswerAdapter
import com.example.educationappmaximsvidrak.customElements.NonScrollableLinearLayoutManager
import com.example.educationappmaximsvidrak.databinding.FragmentStartBinding
import com.example.educationappmaximsvidrak.model.Folder


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: QuestionAnswerAdapter


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

//        viewModel.flashcardList.observe(viewLifecycleOwner) { flashcards ->
//            val adapter = QuestionAnswerAdapter(flashcards, binding.rvQuestionAnswer)
//
//            binding.rvQuestionAnswer.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            binding.rvQuestionAnswer.adapter = adapter
//        }

        adapter = QuestionAnswerAdapter(emptyList(), binding.rvQuestionAnswer)
        binding.rvQuestionAnswer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvQuestionAnswer.adapter = adapter

        // Подписка на изменения списка карточек
        viewModel.flashcardList.observe(viewLifecycleOwner) { flashcards ->
            adapter.updateData(flashcards)
        }


        // Подписка на изменения выбранной папки
        viewModel.selectedFolder.observe(viewLifecycleOwner) { folder ->
            folder?.let {
                viewModel.getFlashcardsBySelectedFolder().observe(viewLifecycleOwner) { flashcards ->
//                    val adapter = QuestionAnswerAdapter(emptyList(), binding.rvQuestionAnswer)
                        adapter.updateData(flashcards)
                    }
            } ?: run {
                // Если папка не выбрана, показать все карточки
                viewModel.flashcardList.value?.let { flashcards ->
                    adapter.updateData(flashcards)
                }
            }
        }



        binding.tvFolder.setOnClickListener {
            showPopupMenu(it)
        }

        binding.ivArrow.setOnClickListener {
            showPopupMenu(it)
        }


    }

    private fun showPopupMenu(view: View) {
        val popupMenu = android.widget.PopupMenu(context, view)

        // Добавляем папки в меню
        viewModel.folderList.value?.forEach { folder ->
            popupMenu.menu.add(0, folder.id.toInt(), 0, folder.name)
        }

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            val selectedFolder =
                viewModel.folderList.value?.find { it.id.toInt() == menuItem.itemId }
            if (selectedFolder != null) {
                viewModel.selectFolder(selectedFolder)
                binding.tvFolder.text = selectedFolder.name

            }
            true
        }

        popupMenu.show()
    }


}

