package com.example.vipassignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddMentorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mentor)

        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        var database: DatabaseReference? = null;

        findViewById<Spinner>(R.id.mentorcategory).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                database = FirebaseDatabase.getInstance().reference.child("mentors").child(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing or handle the case where nothing is selected
            }
        }

        var mentorpic:String = "placeholder";

        var pickImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK && result.data?.data!=null){

                var img: Uri? =result.data?.data
                findViewById<ImageView>(R.id.selectedmentorpic).setImageURI(img)
                findViewById<ImageView>(R.id.mentorpicimg).visibility = View.GONE
                findViewById<TextView>(R.id.mentorpictext).visibility = View.GONE
                findViewById<ImageView>(R.id.selectedmentorpic).visibility = View.VISIBLE


                var storageRef = FirebaseStorage.getInstance()
                var filename="${UUID.randomUUID()}"+"pic.jpg"
                var ref=storageRef.getReference("mentorpics/"+filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                mentorpic = it.toString()
                            }
                    }
            }
        }

        findViewById<CardView>(R.id.uploadmentorpicbtn).setOnClickListener{
            pickImage.launch(
                Intent(
                    Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }

        findViewById<Button>(R.id.creatementorbtn).setOnClickListener {
            val userid = auth.currentUser?.uid ?: "N/A";
            val name = findViewById<EditText>(R.id.mentorname).text.toString();
            val occupation = findViewById<EditText>(R.id.mentoroccup).text.toString();
            val about = findViewById<EditText>(R.id.mentordesc).text.toString();
            val rate = "$"+findViewById<EditText>(R.id.mentorrate).text.toString()+"\\session";
            val online = "0";
            val category = findViewById<Spinner>(R.id.mentorcategory)

            category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    database = FirebaseDatabase.getInstance().reference.child("mentors").child(selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing or handle the case where nothing is selected
                }
            }

            val mentor = Mentor(userid, name, occupation, about, rate, online, "5.0", mentorpic);
            database?.child(userid)?.setValue(mentor)
                ?.addOnCompleteListener() { dbtask ->
                    if (dbtask.isSuccessful) {
                        Toast.makeText(this, "You are now a mentor", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }
}