package com.project.onefood.MainMenu.PromoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.R

class PromoRecyclerView : RecyclerView.Adapter<PromoRecyclerView.MyViewHolder>() {

    private val imageList = intArrayOf(R.drawable.fake_promo, R.drawable.fake_promo2, R.drawable.fake_promo3, R.drawable.fake_promo4)
    private val titles = arrayOf("Fried Chicken Noodles", "Buttery Lobster", "Paneer Pasanda", "Strawberry Feta Salad")
    private val descriptions = arrayOf("Freshly made just for you!", "Loaded...", "Cottage cheese with a twist", "Weird Dish :D")
    private val prices = arrayOf("$10.50", "$15.50", "$14.99", "$9.99")

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {
        var image : ImageView
        var title : TextView
        var description : TextView
        var price : TextView

        init {
            image = itemView.findViewById(R.id.promoimage)
            title = itemView.findViewById(R.id.promo_item_title)
            description = itemView.findViewById(R.id.promo_item_description)
            price = itemView.findViewById(R.id.promo_item_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position])
        holder.title.text = titles[position]
        holder.description.text = descriptions[position]
        holder.price.text = prices[position]
    }

    override fun getItemCount(): Int {
        return 4
    }
}