package com.project.onefood.MainMenu.PromoAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.project.onefood.MainMenu.promoItem
import com.project.onefood.MainMenu.viewmodels.ReservationItem
import com.project.onefood.R
import com.project.onefood.RestaurantPage.RestaurantActivity

class PromoRecyclerView(private val context : Context, private var list: ArrayList<promoItem>) : RecyclerView.Adapter<PromoRecyclerView.MyViewHolder>() {

    private val nameArr = arrayOf("That Place Restaurant", "The Sandbar Seafood Restaurant", "Cardero's Restaurant", "Prestons Restaurant + Lounge Vancouver")
    private val addressesArr = arrayOf("932 Brunette Ave, Coquitlam", "1535 Johnston Street, Creekhouse #102, Vancouver", "1583 Coal Harbour Quay, Vancouver", "1177 W Pender St, Vancouver")
    private val distanceArr = arrayOf("5.59", "15.9", "15.47", "Weird Dish :D")
    private val imgsArr = arrayOf("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=AeJbb3cqX_fJsKwwmu5IMksj9O5G-KoyuMDhQ15_rXHA39l2jAKxyHmUjmO-7ie1ropzCKTiijXUnO0SVcRzUKiypz-HrXvPOo6Zh8R5_Ghed0mcIeXHnZAwwgamaG-FLCCReNBVJ4J1qjfn-7wRfqdFdKxyNRQZbRfviaqFzKQB6tFKWc2Q&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA",
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=AeJbb3dEMbe3VuNJ-BQDRuJabbDjPY1lEmkdT0Qgy8S7CgjEdSplR0kakrb1EVAckuv4AARkxBlsILF2iXGulmQR7tgcDdMKGRYcbfvaD14XYRmIWSEDzkDz_Wq1lmhn3QQE5dyjkTY2SvrPB7_Z3ryGy4ywJIiYFFa5A1cHZiVE3fEyjJhc&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA",
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=AeJbb3fFR6Mgu1O_V5Hbt5Pqa9OEiCBSj8NKjnhSeUjPFM8nTnubXKxoUE7ujDBB3QX-RMJEJeZs7p6HPNWUbzafVuaVV58rdEQK7TuubZC_6e5XHgLTrzVRLBr66BI9B_g61utCeHLfQw8xpdMwZvvMYZTOyQj830Sa80NUN1zOChT9r5KP&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA",
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=AeJbb3fqC3JFCePYVFrx3gCPPrc9mr7jEHGhQ6HKPf3jlodA3BO_mIDoJ-Ywt2ZUXE5K06r_vudn7YrK0g9JUeIVYtVfGYhRiHhgx_QGGoIjLYsJ2I0sl6XD1xOtASqpnMuu3fQDFdfmHtRrLoSZNwdw84Lv4jf9l9UlVYKYTIECN0_e9jIL&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA")
    private val latLongArr = arrayOf(LatLng(49.2369453,-122.8704416), LatLng(49.2715215,-123.1341479), LatLng(49.2916101,-123.1274816), LatLng(49.288219,-123.1222251))
    private val ratingsArr = arrayOf("4.2", "4.4", "4.5", "3.9")
    private val placeIdArr = arrayOf("ChIJDa4eoQ14hlQRF3eP4ISQlPI", "ChIJj6Jmb85zhlQR8ho06uTUy6U", "ChIJOQXpDoZxhlQRwQ2A6Ulh9-w", "ChIJ1-V8VYFxhlQRrV7eXCmKzDg")

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {
        var image : ImageView
        var title : TextView
        var description : TextView
        var price : TextView
        var cardView: CardView
        var restaurantName: TextView

        init {
            image = itemView.findViewById(R.id.promoimage)
            title = itemView.findViewById(R.id.promo_item_title)
            description = itemView.findViewById(R.id.promo_item_description)
            price = itemView.findViewById(R.id.promo_item_price)
            cardView = itemView.findViewById(R.id.promoCard)
            restaurantName = itemView.findViewById(R.id.promo_item_restaurant)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(list[position].img.toInt())
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        holder.price.text = list[position].price
        holder.restaurantName.text = nameArr[position]

        holder.cardView.setOnClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurant_name", nameArr[position])
            intent.putExtra("restaurant_address", addressesArr[position])
            intent.putExtra("restaurant_coordinates", latLongArr[position])
            intent.putExtra("restaurant_rating", ratingsArr[position])
            intent.putExtra("restaurant_status", "")
            intent.putExtra("restaurant_img", imgsArr[position])
            intent.putExtra("restaurant_distance", distanceArr[position])
            intent.putExtra("place_id", placeIdArr[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun replace(newPromoItem: ArrayList<promoItem>) {
        list = newPromoItem
    }
}