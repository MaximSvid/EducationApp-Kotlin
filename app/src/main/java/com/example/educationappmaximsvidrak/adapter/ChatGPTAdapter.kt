package com.example.educationappmaximsvidrak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.MainViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.ItemChatBinding
import com.example.educationappmaximsvidrak.model.Message

class ChatGPTAdapter(
    private var messageList: List<Message>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<ChatGPTAdapter.MesgViewHolder>() {


    inner class MesgViewHolder(var binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatGPTAdapter.MesgViewHolder {
       val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MesgViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    override fun onBindViewHolder(holder: MesgViewHolder, position: Int) {
        val message = messageList[position]
        val binding = holder.binding

//        binding.tvRightChat.text = message.content
//        binding.tvLeftChat.text = message.content

        if (message.role == "user") {
            binding.tvRightChat.text = message.content
            binding.tvRightChat.visibility = View.VISIBLE
            binding.tvLeftChat.visibility = View.GONE
        } else {
            binding.tvLeftChat.text = message.content
            binding.tvLeftChat.visibility = View.VISIBLE
            binding.tvRightChat.visibility = View.GONE
        }


    }

}

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