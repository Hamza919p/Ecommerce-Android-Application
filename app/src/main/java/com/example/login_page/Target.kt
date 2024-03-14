package com.example.login_page

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page.Target
import com.example.login_page.databinding.ActivityTargetBinding
import java.util.Locale
import kotlin.collections.List

class Target : AppCompatActivity() {

    private lateinit var binding:ActivityTargetBinding
    private lateinit var recyclerView: RecyclerView
    //private lateinit var searchView: SearchView
    //private var mList= ArrayList<com.example.login_page.List>()
    //private lateinit var adapter: MyAdapter

        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            var binding=ActivityTargetBinding.inflate(layoutInflater)
            setContentView(binding.root)
            replaceFragment(home())

            var img_noti = findViewById<ImageButton>(R.id.cart)
            img_noti.setOnClickListener {
                val intent = Intent(this, Cart::class.java)
                startActivity(intent)
            }

            binding.bottomNavigationView.setOnItemSelectedListener{
                when(it.itemId){
                    R.id.navHome->
                        replaceFragment(home())
                    R.id.navSearch->
                        replaceFragment(search())
                    R.id.navNotification->replaceFragment(notification())
                    R.id.navProfile->replaceFragment(profile())

                    else->{

                    }
                }
                true
            }

            /**searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }
            })*/
    }

    /**private fun filterList(query: String?){

        if(query!=null){
            val filteredList=ArrayList<com.example.login_page.List>()
            for(i in mList){
                if(i.pname?.toLowerCase(Locale.ROOT)!!.contains(query)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show()
            }
            else{
                adapter.setFilteredList(filteredList)
            }
        }

    }*/

    private fun replaceFragment(fragment : Fragment){
        Log.e("enter ","into search")
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

}

