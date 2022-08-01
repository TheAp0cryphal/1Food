package com.project.onefood.MainMenu.ReservationAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.R

class ReservationRecyclerView : RecyclerView.Adapter<ReservationRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       //
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {
       var restaurantPic : ImageView
       var date : TextView
       var time : TextView
       var restaurantName : TextView

       init {
           restaurantPic = itemView.findViewById(R.id.RestaurantPic)
           date = itemView.findViewById(R.id.reservation_date)
           time = itemView.findViewById(R.id.reservation_time)
           restaurantName = itemView.findViewById(R.id.reservation_restaurant)
       }
    }

}