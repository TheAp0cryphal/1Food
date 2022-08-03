package com.project.onefood.FavouriteList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.Databases.FavouriteDB.FavouriteDatabase
import com.project.onefood.Databases.FavouriteDB.FavouriteItem
import com.project.onefood.R
import com.project.onefood.RestaurantPage.RestaurantActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouritesRecyvlerView(private val context: Context, private var list: ArrayList<FavouriteItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var tvRestaurantName: TextView
        internal var tvRestaurantAddress: TextView
        internal var tvRestaurantDistance: TextView
        var favouriteDatabaseDao = FavouriteDatabase.getInstance(context).FavouriteDatabaseDao

        init {
            itemView.findViewById<LinearLayout>(R.id.restaurantCard).setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.favoriteBtn).setOnClickListener(this)
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName)
            tvRestaurantAddress = itemView.findViewById(R.id.tvRestaurantAddress)
            tvRestaurantDistance = itemView.findViewById(R.id.tvRestaurantDistance)
        }

        internal fun bind(position: Int) {

            tvRestaurantName.text = list[position].name
            tvRestaurantAddress.text = list[position].address
            tvRestaurantDistance.text = list[position].distance.toString()

            itemView.findViewById<ImageView>(R.id.favoriteBtn).setImageResource(R.drawable.heart)

            itemView.findViewById<LinearLayout>(R.id.restaurantCard).setOnClickListener {
                val intent = Intent(context, RestaurantActivity::class.java)
                intent.putExtra("restaurant_name", list[position].name)
                intent.putExtra("restaurant_address", list[position].address)
                //intent.putExtra("restaurant_coordinates", list[position].latLng)
                context.startActivity(intent)
            }

            itemView.findViewById<ImageView>(R.id.favoriteBtn).setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch{
                    favouriteDatabaseDao.deleteById(list[position].place_id)
                }
            }

        }

        override fun onClick(p0: View?) {
            //TODO("Not yet implemented")
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

    fun replace(favList: ArrayList<FavouriteItem>) {
        list = favList
    }
}