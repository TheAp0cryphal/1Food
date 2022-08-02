package com.project.onefood.MainMenu.ReservationAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.project.onefood.Databases.ReservationDB.ReservationItem
import com.project.onefood.R
import com.project.onefood.RestaurantPage.ReservationMapsActivity
import kotlinx.coroutines.withContext

class ReservationRecyclerView(private val context : Context, private var list: List<ReservationItem>) : RecyclerView.Adapter<ReservationRecyclerView.MyViewHolder>() {

     var lat : Double = 0.0
     var lon : Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.restaurantName.text = list[position].restaurantName
        holder.date.text = list[position].date
        holder.time.text = list[position].time
        lat = list[position].latitude
        lon = list[position].longitude

       // holder.restaurantPic
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView), View.OnClickListener {
       var restaurantPic : ImageView
       var date : TextView
       var time : TextView
       var restaurantName : TextView

       init {
           itemView.setOnClickListener(this)
           restaurantPic = itemView.findViewById(R.id.RestaurantPic)
           date = itemView.findViewById(R.id.reservation_date)
           time = itemView.findViewById(R.id.reservation_time)
           restaurantName = itemView.findViewById(R.id.reservation_restaurant)
       }

        override fun onClick(v: View?) {
            val intent = Intent(context, ReservationMapsActivity::class.java)
            intent.putExtra("restaurant_coordinates", LatLng(lat, lon))
            context.startActivity(intent)
        }
    }
    fun replace(newReservationList: List<ReservationItem>) {
        list = newReservationList
    }
}