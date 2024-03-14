package com.example.login_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.login_page.databinding.ActivityMain2Binding
import com.example.login_page.databinding.ActivityTargetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        firebaseAuth = FirebaseAuth.getInstance()
        //loadData()

        var submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
          //  Toast.makeText(this,"Not Saved",Toast.LENGTH_SHORT).show()

            savedata()
        }
    }

    private fun savedata() {
        var name = (findViewById<EditText>(R.id.name)).text.toString()
        var address = (findViewById<EditText>(R.id.address)).text.toString()
        var email = (findViewById<EditText>(R.id.email)).text.toString()
        var password = (findViewById<EditText>(R.id.password)).text.toString()
        /**val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
        putString("STRING_fn",firstName)
        }.apply()

        editor.apply{
        putString("STRING_ln",lastName)
        }.apply()

        editor.apply{
        putString("STRING_em",email)
        }.apply()

        editor.apply{
        putString("STRING_pa",password)
        }.apply()

        Toast.makeText(this,"Data Saver",Toast.LENGTH_SHORT).show()
        }

        private fun loadData()
        {
        val sharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        }
         */

        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show()
        }
        else {
            Log.e("user",firebaseAuth.currentUser.toString())
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this,"Auth saved",Toast.LENGTH_SHORT).show()
                    database=FirebaseDatabase.getInstance().getReference("DatabaseStore")

                    val DatabaseStore = DatabaseStore(email,name,address,password)
                    database.child(name).setValue(DatabaseStore).addOnSuccessListener {//name replaced
                        Toast.makeText(this,"Saved to database",Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                   Toast.makeText(this,"Not Saved",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}