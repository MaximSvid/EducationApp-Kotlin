package com.example.educationappmaximsvidrak.ui

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.EventDay
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private  val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFolders()
        showFlashcards()

        val calendarView = binding.cvCalendar


        viewModel.studyDates.observe(viewLifecycleOwner) {dates->



        }

        binding.ibBack.setOnClickListener {
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToSettingsFragment())
        }


    }



    private fun showFolders () {
        viewModel.folderList.observe(viewLifecycleOwner) {
            val count = it.size
            binding.tvCountFolders.text = count.toString()
        }
    }

    private fun showFlashcards () {
        viewModel.flashcardList.observe(viewLifecycleOwner) {
            val count = it.size
            binding.tvCountCards.text = count.toString()
        }
    }
}