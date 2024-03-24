package com.example.vipassignment

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.ArrayList

class MentorCardAdapter(list: ArrayList<Mentor>, c: Context) :
    RecyclerView.Adapter<MentorCardAdapter.MyViewHolder>(){
    var list = list
    var context = c
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v: View = LayoutInflater
                .from(context)
                .inflate(R.layout.mentor_card, parent, false)
            return MyViewHolder(v)
        }
        override fun getItemCount(): Int {
            return list.size
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.name.text = list.get(position).name
            holder.rate.text = list.get(position).rate
            holder.occup.text = list.get(position).occupation
            if (list.get(position).online == "0"){
                holder.status.text = "Unavailable"
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.unavailable))
                holder.imgstatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.unavailable))
            }
            else{
                holder.status.text = "Available"
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.available))
                holder.imgstatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.available))
            }

            Picasso.get().load(list.get(position).pic).into(holder.pic);

        }

    class MyViewHolder: RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)


        var card = itemView.findViewById<CardView>(R.id.mentorcard)
        var name = itemView.findViewById<TextView>(R.id.mentorname)
        var rate = itemView.findViewById<TextView>(R.id.mentorrate)
        var occup = itemView.findViewById<TextView>(R.id.mentorcat)
        var status = itemView.findViewById<TextView>(R.id.mentorstatus)
        var imgstatus = itemView.findViewById<ImageView>(R.id.mentorstatuscircle)
        var pic = itemView.findViewById<ImageView>(R.id.mentorpic)
    }

    fun updateData(newList: List<Mentor>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

