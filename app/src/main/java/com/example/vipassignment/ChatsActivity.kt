package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout

class ChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        findViewById<LinearLayout>(R.id.mentorchat).setOnClickListener()
        {
            startActivity(Intent(this, ChatMessageActivity::class.java))
        }
    }
}