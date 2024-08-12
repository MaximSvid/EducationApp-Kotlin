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
    private var messageList: MutableList<Message>,
    private val viewModel: MainViewModel
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages (newMessages: List<Message>) {
        messageList.clear()
        messageList.addAll(newMessages)
        notifyDataSetChanged()
    }


}
//class ChatGPTAdapter(
//    private var messageList: List<Message>,
//    private val viewModel: MainViewModel
//) : RecyclerView.Adapter<ChatGPTAdapter.MesgViewHolder>() {
//
//
//    inner class MesgViewHolder(var binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatGPTAdapter.MesgViewHolder {
//       val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MesgViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return messageList.size
//
//    }
//
//    override fun onBindViewHolder(holder: MesgViewHolder, position: Int) {
//        val message = messageList[position]
//        val binding = holder.binding
//
////        binding.tvRightChat.text = message.content
////        binding.tvLeftChat.text = message.content
//
//        if (message.role == "user") {
//            binding.tvRightChat.text = message.content
//            binding.tvRightChat.visibility = View.VISIBLE
//            binding.tvLeftChat.visibility = View.GONE
//        } else {
//            binding.tvLeftChat.text = message.content
//            binding.tvLeftChat.visibility = View.VISIBLE
//            binding.tvRightChat.visibility = View.GONE
//        }
//
//
//    }
//
//}

//class ChatGPTAdapter(
//    private var messageList: List<Message>,
//    private val viewModel: MainViewModel
//) : RecyclerView.Adapter<ChatGPTAdapter.MesgViewHolder>() {
//
//    inner class MesgViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
//
//
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesgViewHolder {
//        val chatView =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
//        return MesgViewHolder(chatView)
//    }
//
//    override fun getItemCount(): Int {
//        return messageList.size
//
//    }
//
//    override fun onBindViewHolder(holder: MesgViewHolder, position: Int) {
//        val message = messageList[position]
//
//
//    }
//
//}


//class ChatGPTAdapter(
//    private var messageList: List<Message>
//) : RecyclerView.Adapter<ChatGPTAdapter.MesgViewHolder>() {
//
//    inner class MesgViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
//
//        val leftChatView = view.findViewById<LinearLayout>(R.id.left_chat_view)
//        val leftTextView = view.findViewById<TextView>(R.id.tv_left_chat)
//        val rightChatView = view.findViewById<LinearLayout>(R.id.right_chat_view)
//        val rightTextView = view.findViewById<TextView>(R.id.tv_right_chat)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesgViewHolder {
//        val chatView =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
//        return MesgViewHolder(chatView)
//    }
//
//    override fun getItemCount(): Int {
//        return messageList.size
//
//    }
//
//    override fun onBindViewHolder(holder: MesgViewHolder, position: Int) {
//        val message = messageList[position]
//
//        if (message.sentBy == Message.SENT_BY_ME) {
//            holder.leftChatView.visibility = View.GONE
//            holder.rightChatView.visibility = View.VISIBLE
//            holder.rightTextView.text = message.message
//        } else {
//            holder.leftChatView.visibility = View.VISIBLE
//            holder.rightChatView.visibility = View.GONE
//            holder.rightTextView.text = message.message
//        }
//    }
//
//}


//class ChatGPTAdapter (
//    private var messageList: List<Message>
//) : RecyclerView.Adapter<ViewHolder>(){
//
//    inner class IncomingViewHolder (val incomingBinding: ItemChatRightBinding): ViewHolder(incomingBinding.root)
//
//    inner class OutgoingViewHolder (val outgoingBinding: ItemChatLeftBinding) : ViewHolder (outgoingBinding.root)
//
//    private val incomingType = 1
//    private val outgoingType = 2
//
////    override fun getItemViewType(position: Int): Int {
////        val message = messageList[position]
////
////    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        return messageList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//
//}