package com.example.vipassignment

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        lateinit var auth: FirebaseAuth;
        auth = FirebaseAuth.getInstance();
        val database = FirebaseDatabase.getInstance();
        val userid = auth.currentUser?.uid ?:"N/A";

        if (userid != "N/A") {
            println("User ID: $userid");
            val userRef = database.getReference("users").child(userid);
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            findViewById<TextView>(R.id.usernamehome).text = user.username + "!";
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        val mentorlist = ArrayList<Mentor>()

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.rv1)

        val mentorsRef = database.getReference("mentors");

        val mentorAdapter = MentorCardAdapter(mentorlist, this@HomeActivity)
        recyclerView.adapter = mentorAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mentorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mentorlist.clear()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key // Get the category name
                    for (mentorSnapshot in categorySnapshot.children) {
                        val mentor = mentorSnapshot.getValue(Mentor::class.java)
                        mentor?.let {
                            mentorlist.add(mentor)
                        }
                    }
                }
                // Notify the adapter after adding all mentors
                mentorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val educationmentorlist = ArrayList<Mentor>()

        val recyclerView2: RecyclerView = findViewById<RecyclerView>(R.id.rv2)

        val educationmentorAdapter = MentorCardAdapter(educationmentorlist, this@HomeActivity)
        recyclerView2.adapter = educationmentorAdapter
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mentorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val linearLayout: LinearLayout = findViewById(R.id.educationmentorlayout)
                linearLayout.visibility = View.GONE
                educationmentorlist.clear()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key // Get the category name
                    for (mentorSnapshot in categorySnapshot.children) {
                        val mentor = mentorSnapshot.getValue(Mentor::class.java)
                        mentor?.let {
                            if (category == "Education"){
                                educationmentorlist.add(mentor)
                                linearLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                // Notify the adapter after adding all mentors
                educationmentorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val entrementorlist = ArrayList<Mentor>()

        val recyclerView3: RecyclerView = findViewById<RecyclerView>(R.id.rv3)

        val entrementorAdapter = MentorCardAdapter(entrementorlist, this@HomeActivity)
        recyclerView3.adapter = entrementorAdapter
        recyclerView3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mentorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val linearLayout: LinearLayout = findViewById(R.id.entrepreneurshipmentorlayout)
                linearLayout.visibility = View.GONE
                entrementorlist.clear()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key // Get the category name
                    for (mentorSnapshot in categorySnapshot.children) {
                        val mentor = mentorSnapshot.getValue(Mentor::class.java)
                        mentor?.let {
                            if (category == "Entreprenuership"){
                                entrementorlist.add(mentor)
                                linearLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                // Notify the adapter after adding all mentors
                entrementorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val personalmentorlist = ArrayList<Mentor>()

        val recyclerView4: RecyclerView = findViewById<RecyclerView>(R.id.rv4)

        val personalmentorAdapter = MentorCardAdapter(personalmentorlist, this@HomeActivity)
        recyclerView4.adapter = personalmentorAdapter
        recyclerView4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mentorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val linearLayout: LinearLayout = findViewById(R.id.personalmentorlayout)
                linearLayout.visibility = View.GONE
                personalmentorlist.clear()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key // Get the category name
                    for (mentorSnapshot in categorySnapshot.children) {
                        val mentor = mentorSnapshot.getValue(Mentor::class.java)
                        mentor?.let {
                            if (category == "Personal Growth"){
                                personalmentorlist.add(mentor)
                                linearLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                // Notify the adapter after adding all mentors
                personalmentorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val careermentorlist = ArrayList<Mentor>()

        val recyclerView5: RecyclerView = findViewById<RecyclerView>(R.id.rv5)

        val careermentorAdapter = MentorCardAdapter(careermentorlist, this@HomeActivity)
        recyclerView5.adapter = careermentorAdapter
        recyclerView5.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mentorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val linearLayout: LinearLayout = findViewById(R.id.careermentorlayout)
                linearLayout.visibility = View.GONE
                careermentorlist.clear()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.key // Get the category name
                    for (mentorSnapshot in categorySnapshot.children) {
                        val mentor = mentorSnapshot.getValue(Mentor::class.java)
                        mentor?.let {
                            if (category == "Career"){
                                careermentorlist.add(mentor)
                                linearLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                // Notify the adapter after adding all mentors
                careermentorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })


        findViewById<ImageButton>(R.id.notificationbtn).setOnClickListener()
        {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        findViewById<ImageButton>(R.id.searchbtn).setOnClickListener()
        {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        findViewById<ImageButton>(R.id.chatbtn).setOnClickListener()
        {
            startActivity(Intent(this, ChatsActivity::class.java))
        }

        findViewById<ImageButton>(R.id.addbtn).setOnClickListener()
        {
            startActivity(Intent(this, AddMentorActivity::class.java))
        }

        findViewById<ImageButton>(R.id.profilebtn).setOnClickListener()
        {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }
}