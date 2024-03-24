package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MentorHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_home)

        findViewById<Button>(R.id.reviewbtn).setOnClickListener()
        {
            startActivity(Intent(this, ReviewActivity::class.java))
        }

        findViewById<Button>(R.id.communitybtn).setOnClickListener()
        {
            startActivity(Intent(this, CommunityChatActivity::class.java))
        }
    }
}