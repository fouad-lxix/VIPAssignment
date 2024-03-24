package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        val database = FirebaseDatabase.getInstance();

        findViewById<Button>(R.id.loginbtn).setOnClickListener()
        {
            val email = findViewById<EditText>(R.id.editTextUsername).text.toString();
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
                if (task.isSuccessful){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish();
                }
            }
        }

        findViewById<TextView>(R.id.SignUpText).setOnClickListener()
        {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<TextView>(R.id.forgotText).setOnClickListener()
        {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}