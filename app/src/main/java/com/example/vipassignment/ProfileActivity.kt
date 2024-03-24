package com.example.vipassignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
                        if (user != null) {
                            findViewById<TextView>(R.id.username).text = user.username;
                            findViewById<TextView>(R.id.usercity).text = user.city;
                            val profilePicUrl = user.profilepic
                            Picasso.get().load(profilePicUrl).into(findViewById<ImageButton>(R.id.profilepic))
                            val coverPicUrl = user.coverpic
                            Picasso.get().load(coverPicUrl).into(findViewById<ImageView>(R.id.coverphoto))
                        }
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
                findViewById<ImageView>(R.id.coverphoto).setImageURI(img)

                var storageRef = FirebaseStorage.getInstance()
                var filename=userid+"pic.jpg"
                var ref=storageRef.getReference("coverpics/"+filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)

                                val updates = HashMap<String, Any>()
                                updates["coverpic"] = it.toString()
                                databaseReference.updateChildren(updates)
                            }
                    }
            }
        }

        findViewById<ImageButton>(R.id.editcoverbtn).setOnClickListener{
            pickImage.launch(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }

        findViewById<ImageButton>(R.id.editprofilebtn).setOnClickListener{
            startActivity(Intent(this, EditProfileActivity::class.java))
        }


        findViewById<TextView>(R.id.logoutbtn).setOnClickListener{
            auth.signOut();
            startActivity(Intent(this, MainActivity::class.java));
            finish();
        }
    }
}