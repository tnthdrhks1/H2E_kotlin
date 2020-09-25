package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signup.*

class MainActivity : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("oncreate")

        auth = FirebaseAuth.getInstance()

        button_makeid.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()  // 회원가입 버튼을 누르면 회원가입 액티비티로 이동
        }
        button_login.setOnClickListener {
            dologin()
        } // 로그인버튼을 누르면 로그인 함수로 이동
    }

    private fun dologin() {
        if(main_id.text.toString().isEmpty()){
            main_id.error = "id is empty"
            main_id.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(main_id.text.toString()).matches()){
            main_id.error = "id is not Emeail type"
            main_id.requestFocus()
            return
        }

        if(main_password.text.toString().isEmpty()){
            main_password.error = "password is empty"
            main_password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(main_id.text.toString(), main_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        println("onstart")
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser) //이새끼가 에러일으키는 씹새끼임
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        else{
            Toast.makeText(baseContext, "login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
}

