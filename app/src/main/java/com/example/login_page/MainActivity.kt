package com.example.login_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()

        //checkIfUserIsLogged()//checks if user is logged in

        var signUp = findViewById<Button>(R.id.signUP)
        signUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        var signIn = findViewById<Button>(R.id.signInN)
        signIn.setOnClickListener{
            check()
        }
    }
/**
    private fun checkIfUserIsLogged() {

        if (firebaseAuth.currentUser != null) {

            val intent = Intent(this, Target::class.java)
            startActivity(intent)
            finish()
        }
    }
    */

    private fun check(){
        validatedata()
        var email = (findViewById<EditText>(R.id.email)).text.toString()
        var password = (findViewById<EditText>(R.id.password)).text.toString()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful){
                val intent = Intent(this, Target::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Check Email or Password Again!!",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun validatedata(){
        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)
        val em:String =email.getText().toString()
        val pa:String =password.getText().toString()
        if(em.isEmpty()){
            email.setError("required field")
            email.requestFocus()
        }
        else if(pa.isEmpty()){
            password.setError("required field")
            password.requestFocus()
        }
    }

    /**
    private fun check()
    {
    validatedata()
    var email = findViewById<EditText>(R.id.email)
    var password = findViewById<EditText>(R.id.password)
    val em:String =email.getText().toString()
    val pa:String =password.getText().toString()
    val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    var emailSP:String = sharedPreferences.getString("STRING_em","").toString()

    var passwordSP:String = sharedPreferences.getString("STRING_pa","").toString()
    Log.e("passwordSP",passwordSP)
    if(em == emailSP)
    {
    if(pa == passwordSP) {
    val intent = Intent(this, Target::class.java)
    startActivity(intent)
    }
    else{
    Toast.makeText(this,"Check Email or Password Again!!",Toast.LENGTH_SHORT).show()
    }
    }
    else{
    Toast.makeText(this,"Check Email or Password Again!!",Toast.LENGTH_SHORT).show()
    }
    }
     */

}