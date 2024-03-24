package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.get_startedbtn).setOnClickListener()
        {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        if(auth.currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java));
        }
    }
}