package com.example.vipassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /*findViewById<Button>(R.id.signupbtn).setOnClickListener()
        {
            startActivity(Intent(this, VerifyNumberActivity::class.java))
        }*/


        //Registering User
        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        val database = FirebaseDatabase.getInstance();

        findViewById<Button>(R.id.signupbtn).setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextUsername).text.toString();
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString();
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userid = auth.currentUser?.uid ?:"NULL";
                        val username = findViewById<EditText>(R.id.editTextName).text.toString();
                        val phone = findViewById<EditText>(R.id.editTextContact).text.toString();
                        val city = findViewById<EditText>(R.id.editTextCity).text.toString();
                        val country = findViewById<EditText>(R.id.editTextCounttry).text.toString();
                        val profilepic = "placeholder";
                        val coverpic = "placeholder";

                        val user = User(userid, username, phone, city, country, profilepic, coverpic);
                        database.reference.child("users").child(userid).setValue(user)
                            .addOnCompleteListener() { dbTask ->
                                if (dbTask.isSuccessful) {
                                    //SignIn User
                                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(){signinTask->
                                        if (signinTask.isSuccessful){
                                            startActivity(Intent(this, HomeActivity::class.java))
                                            finish()
                                        }
                                    }
                                }
                            }
                    }
                }
        }
    }
}