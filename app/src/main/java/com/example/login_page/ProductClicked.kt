package com.example.login_page

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProductClicked : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: ProductDataStore
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var userName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_clicked)

        val name=findViewById<TextView>(R.id.heading)
        val image=findViewById<ImageView>(R.id.product_image)
        val discount=findViewById<TextView>(R.id.discount)
        val price=findViewById<TextView>(R.id.price)
        val description=findViewById<TextView>(R.id.productdescription)
        val contents=findViewById<TextView>(R.id.boxcontents)

        val pintent=intent
        val id=pintent.getStringExtra("pname")
        val img=pintent.getStringExtra("img")

        databaseReference = FirebaseDatabase.getInstance().getReference("ProductDataStore")
        if (id != null) {
            databaseReference.child(id).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(ProductDataStore::class.java)!!
                    name.text=user.pname
                    discount.text=user.pdiscount
                    price.text=user.pcost
                    description.text=user.pdescription
                    contents.text=user.pboxcontents
                    val imageUrl = user?.mapImage?.get("url")
                    Picasso.with(this@ProductClicked).load(imageUrl).into(image);

                }

                override fun onCancelled(error: DatabaseError) {
                    //Toast.makeText(,"Check Email or Password Again!!",Toast.LENGTH_SHORT).show()
                }

            })
        }


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        // Assuming you have the user's email
        val userEmail = auth.currentUser?.email.toString()
        // Get the UID of the authenticated user
        val currentUser = auth.currentUser
        val uid = currentUser?.uid
        // Query the database to get the user's name
        val query: Query = database.child("DatabaseStore").orderByChild("email").equalTo(userEmail)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(DatabaseStore::class.java)
                    // Assuming your User class has a 'name' field
                    userName = (user?.name).toString()
                    // Now you have the user's name
                    Log.d("TAG", "User Name: $userName")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException())
            }
        })




        val cart=findViewById<Button>(R.id.add_to_cart)
        val buy=findViewById<Button>(R.id.buy)

        cart.setOnClickListener {
            database=FirebaseDatabase.getInstance().getReference("CartData")
            auth = FirebaseAuth.getInstance()
            //uid =auth.currentUser?.email.toString()//tester

            val CartData = CartData(userName,user.mapImage,user.pname,user.pdiscount,user.pcost,user.pdescription,user.pboxcontents)
            database.child(userName).child((user.pname).toString()).setValue(CartData).addOnSuccessListener {//name replaced
                Toast.makeText(this,"Added To Cart", Toast.LENGTH_SHORT).show()
            }
        }

        buy.setOnClickListener {
            database=FirebaseDatabase.getInstance().getReference("CartData")
            auth = FirebaseAuth.getInstance()
            //uid =auth.currentUser?.email.toString()//tester

            val CartData = CartData(userName,user.mapImage,user.pname,user.pdiscount,user.pcost,user.pdescription,user.pboxcontents)
            database.child(userName).child((user.pname).toString()).setValue(CartData).addOnSuccessListener {//name replaced
                Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Cart::class.java)
                startActivity(intent)
            }
        }

    }
}