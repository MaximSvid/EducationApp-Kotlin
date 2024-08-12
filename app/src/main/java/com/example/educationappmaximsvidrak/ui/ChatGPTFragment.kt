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
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val testMessages = listOf(
//            Message(role = "user", content = "Hello! How are you?"),
//            Message(
//                role = "assistant",
//                content = "I'm fine, thank you! How can I assist you today?"
//            ),
//            Message(role = "user", content = "Tell me something interesting."),
//            Message(
//                role = "assistant",
//                content = "Did you know that honey never spoils? Archaeologists have found pots of honey in ancient Egyptian tombs that are over 3,000 years old and still perfectly edible!"
//            )
//        )

        binding.rvChatGPT.layoutManager = LinearLayoutManager(requireContext())

        val chatAdapter = ChatGPTAdapter(mutableListOf(), viewModel)
        binding.rvChatGPT.adapter = chatAdapter

//        chatAdapter.updateMessages(testMessages)
//        binding.rvChatGPT.scrollToPosition(testMessages.size - 1)


        viewModel.message.observe(viewLifecycleOwner) { messages ->
            Log.e("ChatGPTFragment", "Messages: $messages")
            chatAdapter.updateMessages(messages)
            binding.rvChatGPT.scrollToPosition(messages.size - 1)
        }


        binding.ibSend.setOnClickListener {
            val userMessage = binding.etMessage.text.toString()
            if (userMessage.isNotEmpty()) {
                viewModel.sendMessage(userMessage)
                binding.etMessage.text.clear()
            }
        }

//       viewModel.error.observe(viewLifecycleOwner) {
//           Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
//       }



        binding.ibBack.setOnClickListener {
            findNavController().navigate(ChatGPTFragmentDirections.actionChatGPTFragmentToHomeFragment())
        }
    }
}