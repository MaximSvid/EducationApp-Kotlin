package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentChatGPTBinding
import okhttp3.OkHttpClient

class ChatGPTFragment : Fragment() {

    private lateinit var binding: FragmentChatGPTBinding

    private val client = OkHttpClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatGPTBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//        binding.btnQuestionEnter.setOnClickListener {
//            val question = binding.etQuestion.text.toString()
//            Toast.makeText(context, question, Toast.LENGTH_SHORT).show()
//            getResponse(question) {response ->
//            }
//        }

    }

//    fun getResponse(question: String) {
//        val apiKey = "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"
//        val url = "https://api.openai.com/v1/chat/completions"
//
//    }


}