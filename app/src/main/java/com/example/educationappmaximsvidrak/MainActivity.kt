package com.example.educationappmaximsvidrak

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.educationappmaximsvidrak.adapter.ChatGPTAdapter
import com.example.educationappmaximsvidrak.model.Message
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    val API_KEY = "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"

    lateinit var recyclerView: RecyclerView
    lateinit var welcomeText : TextView
    lateinit var messageEditText: EditText
    lateinit var sendButton: ImageButton
    lateinit var messageList: MutableList<Message>
    lateinit var chatGPTAdapter: ChatGPTAdapter
    val client = OkHttpClient
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageList = ArrayList()
        recyclerView = findViewById(R.id.rv_chatGPT)
        welcomeText = findViewById(R.id.tv_welcome_text)
        messageEditText = findViewById(R.id.et_message)
        sendButton = findViewById(R.id.ib_send)
        chatGPTAdapter = ChatGPTAdapter(messageList)
        recyclerView.adapter = chatGPTAdapter


    }
}