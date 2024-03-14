package com.example.login_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class Cart : AppCompatActivity(), PaymentResultListener{

    private lateinit var dbref: DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<CartData>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var databaseReference:DatabaseReference
    private lateinit var user:CartData
    private var sumOfPCost: Double=0.0
    private lateinit var pay:Button
    private lateinit var card:CardView
    private lateinit var success:TextView
    private lateinit var failed:TextView
    private  var userName=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        newRecyclerView = findViewById(R.id.recyclerview2)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)


        auth = FirebaseAuth.getInstance()
        //uid ="tester"//auth.currentUser?.email.toString()
        //Log.e("uid",uid.toString())

        //uid=auth.currentUser?.email.toString()

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

                    newArrayList = arrayListOf<CartData>()
                    getUserData()
                    var adapter = MyAdapter2(newArrayList, this@Cart)
                    newRecyclerView.adapter = adapter//MyAdapter(newArrayList)
                    adapter.onItemClick = {
                        //destination from clicking recycler view
                    }
                    totalCost()
                    // Now you have the user's name
                    Log.d("TAG", "User Name: $userName")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException())
            }
        })


        /* newArrayList = arrayListOf<CartData>()
             getUserData()
             var adapter = MyAdapter2(newArrayList, this)
             newRecyclerView.adapter = adapter//MyAdapter(newArrayList)
             adapter.onItemClick = {

                 //destination from clicking recycler view

             }*/

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview2)
        val total=findViewById<TextView>(R.id.total)
        /**
        val query2: Query = database.child("CartData").child(userName).orderByChild("pcost")
        query2.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (userSnapshot in dataSnapshot.children) {
        val user = userSnapshot.getValue(CartData::class.java)
        val cost = user?.pcost
        sumOfPCost=sumOfPCost+(cost)!!.toDouble()
        total.text=sumOfPCost.toString()
        }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        Log.e("TAG", "Error getting data", databaseError.toException())
        }
        })
         */


        pay = findViewById<Button>(R.id.checkout)
        pay.setOnClickListener{
            makePayment()
        }

    }

    private fun totalCost(){

        val total=findViewById<TextView>(R.id.total)
        val query2: Query = database.child("CartData").child(userName).orderByChild("pcost")
        query2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(CartData::class.java)
                    val cost = user?.pcost
                    sumOfPCost=sumOfPCost+(cost)!!.toDouble()
                    total.text=sumOfPCost.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException())
            }
        })

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("CartData").child(userName)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val list = userSnapshot.getValue(CartData::class.java)
                        newArrayList.add(CartData(list!!.userName,list.mapImage,list.pname,list.pcost,list.pdiscount,list.pdescription)!!)
                        Log.e("newArrayList", newArrayList.toString())

                    }

                    newRecyclerView.adapter = MyAdapter2(newArrayList, this@Cart)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Cart, "fail", Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun makePayment(){
    val co = Checkout()
    try {
    val options = JSONObject()
    options.put("name","Razorpay")
    options.put("description","Pay Charges")
    //You can omit the image option to fetch the image from the dashboard
    options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
    options.put("theme.color", "#3399cc");
    options.put("currency","INR");
    options.put("amount","199")//pass amount in currency subunits

    val prefill = JSONObject()
    prefill.put("email","")
    prefill.put("contact","")

    options.put("prefill",prefill)
    co.open(this,options)
    }catch (e: Exception){
    Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
    e.printStackTrace()
    }

    }

    override fun onPaymentSuccess(p0: String?) {
    Toast.makeText(this,"Payment Successful $p0",Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
    Toast.makeText(this,"Error in payment $p0",Toast.LENGTH_LONG).show()
    }
}