package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private val TAG = "Firestore"
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        materialButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        db.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if(document.id == "rasp"){
                            var mData = document.data["weight"]
                            Log.d(TAG, mData.toString())
                            textView_temp.setText("not error" + "℃")
                            textView_temp.setText(mData.toString() + "℃")
                        }
                    }
                } else {
                    textView_temp.setText("error" + "℃")
                }
            }
    }
}