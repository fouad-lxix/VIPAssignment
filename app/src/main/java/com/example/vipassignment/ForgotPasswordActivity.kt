package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        findViewById<Button>(R.id.verify).setOnClickListener()
        {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }
}