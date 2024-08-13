package com.example.educationappmaximsvidrak.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.ItemChatBinding
import com.example.educationappmaximsvidrak.databinding.ItemChatLeftBinding
import com.example.educationappmaximsvidrak.databinding.ItemChatRightBinding
import com.example.educationappmaximsvidrak.model.Message


class ChatGPTAdapter(
    private var messageList: MutableList<Message>
) : RecyclerView.Adapter<ViewHolder>() {

    inner class IncomingViewHolder (val incomingBinding: ItemChatLeftBinding): ViewHolder (incomingBinding.root)

    inner class OutgoingViewHolder (val outgoingBinding: ItemChatRightBinding): ViewHolder (outgoingBinding.root)

    private val incomingType = 1
    private val outgoingType = 2
    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]

        return if (message.role == "user"){
            outgoingType
        } else {
            incomingType
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       if (viewType == incomingType) {
           val incomingBinding = ItemChatLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IncomingViewHolder(incomingBinding)
       }
        val outgoingBinding = ItemChatRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutgoingViewHolder(outgoingBinding)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]

        if (holder is IncomingViewHolder){
            holder.incomingBinding.tvMessageLeft.text = message.content
        } else if (holder is OutgoingViewHolder) {
            holder.outgoingBinding.tvMessageRight.text = message.content
        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun updateMessages (newMessages: List<Message>) {
//        messageList.addAll(newMessages)
//        notifyDataSetChanged()
//    }

        @SuppressLint("NotifyDataSetChanged")
    fun updateMessages (newMessages: List<Message>) {
        messageList.clear()
        messageList.addAll(newMessages)
        notifyDataSetChanged()
    }


}
