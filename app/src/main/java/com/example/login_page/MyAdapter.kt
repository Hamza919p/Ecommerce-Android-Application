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
import java.net.URL




/**
class MyAdapter(private val productList: ArrayList<List>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = productList[position]
        //holder.titleImage.setImageResource(currentProduct.mapImage)
        holder.titleHeading.text=currentProduct.pname
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleImage: ImageView=itemView.findViewById(R.id.title_image)
        val titleHeading: TextView =itemView.findViewById(R.id.heading)

    }

}
*/


class MyAdapter(private var recyclerView: ArrayList<List>, mContext: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick: ((List)->Unit)?=null

    var context =mContext
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)
        return  MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct = recyclerView[position]

        val imageUrl = currentProduct?.mapImage?.get("url") // Assuming "url" is the key for the image URL
        Log.e("imageUrl",imageUrl.toString())
        //holder.image.setImageResource(currentProduct.mapImage.toString())
        //var imgurl= currentProduct.mapImage
        holder.name.text=currentProduct.pname
        holder.description.text=currentProduct.pdescription
        holder.discount.text=currentProduct.pdiscount
        holder.cost.text=currentProduct.pcost
        Picasso.with(context).load(imageUrl.toString())?.into(holder.titleImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductClicked::class.java)
            intent.putExtra("pname",currentProduct.pname)
            intent.putExtra("img",imageUrl.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recyclerView.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name = itemView.findViewById<TextView>(R.id.pname)
        val description = itemView.findViewById<TextView>(R.id.pdescription)
        val discount = itemView.findViewById<TextView>(R.id.pdiscount)
        val cost = itemView.findViewById<TextView>(R.id.pprice)
        //val contents = itemView.findViewById<TextView>(R.id.pcontents)
        val titleImage = itemView.findViewById<ImageView>(R.id.title_image)
    }

    /**fun setFilteredList(recyclerView: ArrayList<List>){
        this.recyclerView=recyclerView
        notifyDataSetChanged()
    }
    */
}
