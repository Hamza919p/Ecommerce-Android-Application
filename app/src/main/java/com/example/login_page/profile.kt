package com.example.login_page
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class profile : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: DatabaseStore
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  inflater.inflate(R.layout.fragment_profile, container, false)


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
                    val userName = user?.name

                    // Now you have the user's name
                    Log.d("TAG", "User Name: $userName")
                    var uname = view.findViewById<TextView>(R.id.uname)
                    uname.text=userName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException())
            }
        })

        /**
        databaseReference = FirebaseDatabase.getInstance().getReference("DatabaseStore")
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(DatabaseStore::class.java)!!
                var uname = view.findViewById<TextView>(R.id.uname)
                //var uemail = view.findViewById<TextView>(R.id.uemail)
                //var uaddress = view.findViewById<TextView>(R.id.uaddress)
                Log.e("uid", uid.toString())
                uname.text=user.name

            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(,"Check Email or Password Again!!",Toast.LENGTH_SHORT).show()
            }
        })
        */

        var sell = view.findViewById<Button>(R.id.b3)
        sell.setOnClickListener {
            val intent = Intent(requireContext(), SellerPage::class.java)
            startActivity(intent)
        }

        /**
        if(auth.currentUser!=null){
            auth.currentUser?.let {
                var e=view.findViewById<TextView>(R.id.uname)
                e.text=it.uid
            }
        }
        */

        var signout=view.findViewById<Button>(R.id.b4)
        signout.setOnClickListener {

            auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        var cart=view.findViewById<Button>(R.id.b4)
        cart.setOnClickListener {
            val intent = Intent(requireContext(), Cart::class.java)
            startActivity(intent)
        }


        var delete=view.findViewById<Button>(R.id.b5)
        delete.setOnClickListener {
            val currentUser=FirebaseAuth.getInstance().currentUser
            if(currentUser != null) {
                currentUser.delete().addOnSuccessListener {
                    Toast.makeText(requireContext(),"ACCOUNT DELETED",Toast.LENGTH_SHORT).show()
                }
            }
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        
        return view
    }
}