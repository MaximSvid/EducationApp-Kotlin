package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.model.Message

class ChatGPTAdapter (
    private var messageList: List<Message>
) : RecyclerView.Adapter<ChatGPTAdapter.MessageViewHolder>(){

    inner class MessageViewHolder (val binding: View): RecyclerView.ViewHolder(binding) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_left, parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}