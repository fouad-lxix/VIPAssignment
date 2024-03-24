package com.example.vipassignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        var database = FirebaseDatabase.getInstance();

        val userid = auth.currentUser?.uid ?:"N/A";

        var user : User;

        if (userid != "N/A") {
            println("User ID: $userid");
            val userRef = database.getReference("users").child(userid);
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        user = snapshot.getValue(User::class.java)!!
                        val profilePicUrl = user.profilepic
                        Picasso.get().load(profilePicUrl).into(findViewById<ImageButton>(R.id.profilepic))
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        var pickImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK && result.data?.data!=null){

                var img: Uri? =result.data?.data
                findViewById<ImageButton>(R.id.profilepic).setImageURI(img)

                var storageRef = FirebaseStorage.getInstance()
                var filename=userid+"pic.jpg"
                var ref=storageRef.getReference("profilepics/"+filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)

                                val updates = HashMap<String, Any>()
                                updates["profilepic"] = it.toString()
                                databaseReference.updateChildren(updates)
                            }
                    }
            }
        }

        findViewById<ImageButton>(R.id.profilepic).setOnClickListener{
            pickImage.launch(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }

        findViewById<Button>(R.id.updateprofilebtn).setOnClickListener {
            val userid = auth.currentUser?.uid ?:"NULL";
            val username = findViewById<EditText>(R.id.editTextName).text.toString();
            val phone = findViewById<EditText>(R.id.editTextContact).text.toString();
            val city = findViewById<EditText>(R.id.editTextCity).text.toString();
            val country = findViewById<EditText>(R.id.editTextCounttry).text.toString();

            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)

            val updates = HashMap<String, Any>()
            updates["city"] = city
            updates["country"] = country
            updates["phone"] = phone
            updates["username"] = username

            databaseReference.updateChildren(updates).addOnSuccessListener {
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        }

    }
}