package com.example.login_page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class home : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<com.example.login_page.List>
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        newRecyclerView = view.findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)

        /**
        imageId = arrayOf(
        R.drawable.p6,
        R.drawable.p4,
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p4,
        )

        heading = arrayOf(
        "PROTINE",
        "CAR",
        "CHAIR",
        "DRESS",
        "CAR"
        )
         */

        newArrayList = arrayListOf<com.example.login_page.List>()
        getUserData()

        var adapter = MyAdapter(newArrayList,requireContext())
        newRecyclerView.adapter = adapter//MyAdapter(newArrayList)
        adapter.onItemClick= {

            val intent = Intent(requireContext(), ProductClicked::class.java)
            startActivity(intent)

        }

        return view
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("ProductDataStore")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val list = userSnapshot.getValue(com.example.login_page.List::class.java)
                        newArrayList.add(list!!)
                        Log.e("newArrayList", newArrayList.toString())

                    }

                    newRecyclerView.adapter = MyAdapter(newArrayList, requireContext())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
            }


        })
        /**

        for (i in imageId.indices) {
        val news = com.example.login_page.List( heading[i])
        newArrayList.add(news)
        }
        */
        }
}
