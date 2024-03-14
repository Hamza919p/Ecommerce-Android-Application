package com.example.login_page

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlin.annotation.Target

class SellerPage : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var btnBrowse: Button
    private lateinit var btnUpload: Button

    private var storageRef= Firebase.storage

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_page)

        storageRef= FirebaseStorage.getInstance()

        image=findViewById(R.id.pimage)
        btnBrowse=findViewById(R.id.browse)
        btnUpload=findViewById(R.id.post)

        val galleryImage= registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback{
                image.setImageURI(it)
                if (it != null) {
                    uri=it
                }
            })

        btnBrowse.setOnClickListener{
            galleryImage.launch("image/*")
        }

        btnUpload.setOnClickListener{
            Toast.makeText(this,"working",Toast.LENGTH_SHORT).show()

            var pname = (findViewById<EditText>(R.id.pname)).text.toString()
            var pcost = (findViewById<EditText>(R.id.pprice)).text.toString()
            var pdiscount = (findViewById<EditText>(R.id.pdiscount)).text.toString()
            var pdescription = (findViewById<EditText>(R.id.pdescription)).text.toString()
            var pboxcontents = (findViewById<EditText>(R.id.pcontents)).text.toString()

            storageRef.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener {task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val userId=FirebaseAuth.getInstance().currentUser!!.uid
                            val mapImage= mapOf(
                                "url" to it.toString()
                            )

                            val databaseReference= FirebaseDatabase.getInstance().getReference("ProductDataStore")
                            val ProdutDataStore = ProductDataStore(mapImage,pname,pcost,pdiscount,pdescription,pboxcontents)
                            databaseReference.child(pname).setValue(ProdutDataStore).addOnSuccessListener {
                                Toast.makeText(this, "Product uploaded for sale", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, com.example.login_page.Target::class.java)
                                startActivity(intent)
                            }

                           /** databaseReference.child(userId).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this,"UnSuccessful",Toast.LENGTH_SHORT).show()
                                }
                           */
                        }
                }
        }

    }

}