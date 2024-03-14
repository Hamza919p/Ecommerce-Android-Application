package com.example.login_page

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.collections.List
class MyAdapter2(private val recyclerView2: ArrayList<CartData>, mContext: Context) : RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {

    var onItemClick: ((CartData)->Unit)?=null
    private var sum: Double = 0.0
    var context =mContext
    /**

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductClicked::class.java)
            intent.putExtra("pname",currentProduct.pname)
            intent.putExtra("img",imageUrl.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_design2,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct = recyclerView2[position]

        val imageUrl = currentProduct?.mapImage?.get("url") // Assuming "url" is the key for the image URL
        Log.e("imageUrl",imageUrl.toString())
        holder.name.text=currentProduct.pname
        holder.description.text=currentProduct.pdescription
        holder.discount.text=currentProduct.pdiscount
        holder.cost.text=currentProduct.pcost
        Picasso.with(context).load(imageUrl.toString())?.into(holder.titleImage)

    }

    override fun getItemCount(): Int {
        return recyclerView2.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.pnamec)
        val description = itemView.findViewById<TextView>(R.id.pdescriptionc)
        val discount = itemView.findViewById<TextView>(R.id.pdiscountc)
        val cost = itemView.findViewById<TextView>(R.id.ppricec)
        val titleImage = itemView.findViewById<ImageView>(R.id.title_imagec)
    }

}