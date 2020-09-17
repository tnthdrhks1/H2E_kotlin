package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        button_signup.setOnClickListener {
            Signup()
        }
        button_home.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

    }

    private fun Signup(){
        if(signup_id.text.toString().isEmpty()){
            signup_id.error = "id is empty"
            signup_id.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(signup_id.text.toString()).matches()){
            signup_id.error = "id is not Emeail type"
            signup_id.requestFocus()
            return
        }

        if(signup_password.text.toString().isEmpty()){
            signup_password.error = "password is empty"
            signup_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(signup_id.text.toString(), signup_password.text.toString())
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Authentication successful\n welcome.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,  MainActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}