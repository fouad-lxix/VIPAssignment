package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class ChatMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_message)

        findViewById<ImageButton>(R.id.camerabtn).setOnClickListener()
        {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        findViewById<ImageView>(R.id.videocallbtn).setOnClickListener()
        {
            startActivity(Intent(this, VideoCallActivity::class.java))
        }
        findViewById<ImageView>(R.id.voicecallbtn).setOnClickListener()
        {
            startActivity(Intent(this, VoiceCallActivity::class.java))
        }
    }
}