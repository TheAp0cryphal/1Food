package com.project.onefood.MainMenu.PromoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.R

class HorizontalRecyclerView : RecyclerView.Adapter<HorizontalRecyclerView.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       //
    }

    override fun getItemCount(): Int {
        return 10
    }
}