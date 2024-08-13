package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.adapter.ChatGPTAdapter
import com.example.educationappmaximsvidrak.databinding.FragmentChatGPTBinding
import com.example.educationappmaximsvidrak.model.Message
import okhttp3.OkHttpClient

class ChatGPTFragment : Fragment() {

    private lateinit var binding: FragmentChatGPTBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatGPTBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvChatGPT.layoutManager = LinearLayoutManager(requireContext())
        val chatAdapter = ChatGPTAdapter(mutableListOf())
        binding.rvChatGPT.adapter = chatAdapter

        viewModel.message.observe(viewLifecycleOwner) {messages ->
            chatAdapter.updateMessages(messages)
            binding.rvChatGPT.scrollToPosition(messages.size -1)
        }




        binding.ibSend.setOnClickListener {
            val userMessage = binding.etMessage.text.toString()
            if (userMessage.isNotEmpty()) {
                viewModel.sendMessage(userMessage)
                binding.etMessage.text.clear()
            }
        }



        binding.ibBack.setOnClickListener {
            findNavController().navigate(ChatGPTFragmentDirections.actionChatGPTFragmentToHomeFragment())
        }
    }
}