package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.QuestionAnswerAdapter
import com.example.educationappmaximsvidrak.customElements.NonScrollableLinearLayoutManager
import com.example.educationappmaximsvidrak.databinding.FragmentStartBinding


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

        viewModel.folderList.observe(viewLifecycleOwner) {folders ->
            viewModel.observeFolderList()
            checkFolderSelection()
        }
//
//        binding.btnNewCards.setOnClickListener {
//            findNavController().navigate(StartFragmentDirections.actionStartFragmentToFolderFragment())
//        }




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
                viewModel.selectFolder1(selectedFolder)
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
                binding.lavStartAnim.visibility = View.GONE
            } else {
                binding.tvEmptyPage.visibility = View.VISIBLE
                binding.btnNewFolder.visibility = View.VISIBLE
                binding.rvQuestionAnswer.visibility = View.GONE
                binding.lavStartAnim.visibility = View.VISIBLE
                personAnim()
            }
        }
    }

    private fun checkFolderSelection() {
        viewModel.checkFolderExist().observe(viewLifecycleOwner) { folders ->
            if (folders.isNotEmpty()) {
                viewModel.selectedFolder.observe(viewLifecycleOwner) { selectedFolder ->
                    if (selectedFolder != null) {
                        // Если папка выбрана, скрываем сообщение о необходимости выбора папки
//                        binding.tvFolder.text = getString(R.string.select_the_folder) // Отображаем имя выбранной папки
                        binding.ivArrow.visibility = View.VISIBLE
                    } else {
                        // Если папка не выбрана, отображаем сообщение о необходимости выбора папки
//                        binding.tvFolder.text = getString(R.string.select_the_folder)
                        binding.rvQuestionAnswer.visibility = View.GONE
                        binding.ivArrow.visibility = View.VISIBLE
//                        binding.lavStartAnim.visibility = View.GONE
                    }
                }
                binding.btnNewFolder.visibility = View.GONE
            } else {
                // Если папок нет, отображаем сообщение о необходимости создать новую папку
                binding.tvFolder.text = getString(R.string.create_a_new_folder)
                binding.btnNewFolder.visibility = View.VISIBLE
                binding.rvQuestionAnswer.visibility = View.GONE
                binding.ivArrow.visibility = View.GONE
                binding.lavStartAnim.visibility = View.VISIBLE
                personAnim()
            }
        }
    }





    private fun personAnim() {
        val animation = binding.lavStartAnim
        animation.repeatCount = LottieDrawable.INFINITE
        animation.playAnimation()
    }
}




