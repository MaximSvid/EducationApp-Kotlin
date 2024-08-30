package com.example.educationappmaximsvidrak.ui

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.QuestionAnswerAdapter
import com.example.educationappmaximsvidrak.customElements.NonScrollableLinearLayoutManager
import com.example.educationappmaximsvidrak.databinding.FragmentStartBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Collections


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

        // Если папок нет должна появится надпись
        viewModel.selectedFolder.observe(viewLifecycleOwner) { folder ->
            checkIfFolderIsEmpty(folder.id)
        }

        viewModel.folderList.observe(viewLifecycleOwner) {folder ->
            checkFolderExist()
        }




        binding.btnNewFolder.setOnClickListener {
            Log.d("ButtonVisibility", "Button clicked")
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAddQuestionFragment())
        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToHomeFragment())
        }

        binding.rvQuestionAnswer.layoutManager = NonScrollableLinearLayoutManager(requireContext())

        adapter = QuestionAnswerAdapter(emptyList(), binding.rvQuestionAnswer)
        binding.rvQuestionAnswer.adapter = adapter

        // Подписка на изменения списка карточек
        viewModel.flashcardList.observe(viewLifecycleOwner) { flashcards ->
            adapter.updateData(flashcards)

        }


        // Подписка на изменения выбранной папки
        viewModel.selectedFolder.observe(viewLifecycleOwner) { folder ->
            Log.e("LogS", "crash")
            folder?.let {
                viewModel.getFlashcardsBySelectedFolder()
                    .observe(viewLifecycleOwner) { flashcards ->
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

    private fun checkIfFolderIsEmpty(folderId: Long) {
        val folderItems = viewModel.getItemsInFolder(folderId)
        folderItems.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvEmptyPage.visibility = View.GONE
                binding.btnNewFolder.visibility = View.GONE
                binding.rvQuestionAnswer.visibility = View.VISIBLE
                binding.lavArrowUpAnim.visibility = View.GONE
                binding.tvFolder.visibility = View.GONE
            } else {
                binding.tvEmptyPage.visibility = View.VISIBLE
                binding.btnNewFolder.visibility = View.VISIBLE
                binding.rvQuestionAnswer.visibility = View.GONE
                binding.lavArrowUpAnim.visibility = View.VISIBLE
                binding.tvFolder.visibility = View.VISIBLE
                arrowAnim()
                Log.d("ButtonVisibility", "Button is now visible")
            }
        }
    }

    private fun arrowAnim() {
        val animation = binding.lavArrowUpAnim
        animation.repeatCount = LottieDrawable.INFINITE
        animation.playAnimation()
    }

    private fun checkFolderExist () {
       viewModel.checkFolderExist().observe(viewLifecycleOwner) {folders ->
           if (folders.isNotEmpty()) {
               binding.tvFolderNotExist.visibility = View.GONE
               binding.btnNewFolder.visibility = View.GONE
               binding.rvQuestionAnswer.visibility = View.VISIBLE
               binding.tvFolder.visibility = View.GONE
           } else {
               binding.tvFolderNotExist.visibility = View.VISIBLE
               binding.btnNewFolder.visibility = View.VISIBLE
               binding.rvQuestionAnswer.visibility = View.GONE
               binding.tvFolder.visibility = View.VISIBLE
           }
       }


    }

}




