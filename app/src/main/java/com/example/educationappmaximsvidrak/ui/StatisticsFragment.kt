package com.example.educationappmaximsvidrak.ui

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
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

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFolders()
        showFlashcards()

        val calendarView = binding.cvCalendar


        viewModel.studyDates.observe(viewLifecycleOwner) {dates->
            val calendarDays = mutableListOf<CalendarDay>()

            dates.forEach { dateInMillis ->
                val icuCalendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
                val javaCalendar = convertIcuToJavaCalendar(icuCalendar)

                val eventDay = CalendarDay(javaCalendar).apply {
//                    backgroundResource = android.R.color.holo_green_light
                    backgroundResource = R.drawable.ic_braun_dot
//                    selectedLabelColor = android.graphics.Color.WHITE
                }

                calendarDays.add(eventDay)
            }

            // Установка дней с событиями в календаре
            calendarView.setCalendarDays(calendarDays)
        }

//        // Обработка кликов по дням
//        calendarView.setOnDayClickListener(object : OnDayClickListener {
//            override fun onDayClick(eventDay: EventDay) {
//                val clickedDay = eventDay.calendar
//                // Действия при клике на день
//            }
//        })




        binding.ibBack.setOnClickListener {
            findNavController().navigate(StatisticsFragmentDirections.actionStatisticsFragmentToSettingsFragment())
        }


    }

    // Функция для преобразования android.icu.util.Calendar в java.util.Calendar
    private fun convertIcuToJavaCalendar(icuCalendar: Calendar): java.util.Calendar {
        val javaCalendar = java.util.Calendar.getInstance()
        javaCalendar.timeInMillis = icuCalendar.timeInMillis
        return javaCalendar
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