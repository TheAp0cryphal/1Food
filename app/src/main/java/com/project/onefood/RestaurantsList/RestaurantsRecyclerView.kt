package com.project.onefood.RestaurantsList
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.R


class RestaurantsRecyclerView(private val context: Context, private val list: ArrayList<RestaurantItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvRestaurantName: TextView
        internal var tvRestaurantAddress: TextView
        internal var tvRestaurantDistance: TextView

        init {
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName)
            tvRestaurantAddress = itemView.findViewById(R.id.tvRestaurantAddress)
            tvRestaurantDistance = itemView.findViewById(R.id.tvRestaurantDistance)
        }

        internal fun bind(position: Int) {
            tvRestaurantName.text = list[position].name
            tvRestaurantAddress.text = list[position].address
            tvRestaurantDistance.text = list[position].distance.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_item_card, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}