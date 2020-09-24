package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    var naver = "naver"

    var bmi : Double = 0.0
    var height : Double = 0.0
    var weight : Double = 0.0
    var age : Double = 0.0
    lateinit var gender : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        appCompatButton.setOnClickListener {
            send_dev_data()
        }

        button_man.setOnClickListener {
            bmi_man()
        }

        button_woman.setOnClickListener {
            bmi_woman()
        }
        button_next.setOnClickListener {
            val nextIntent = Intent(this, CheckMealActivity::class.java)
            nextIntent.putExtra("device_address", text_dev.text.toString()) //key: "email", value: inputEmail
            startActivity(nextIntent)
        }
    }

    private fun bmi_man() {
        var height = EditText_height.text.toString().toInt()
        var weight = EditText_weight.text.toString().toInt()
        var age = EditText_age.text.toString().toInt()
        gender = "남자"

        bmi = 66.47 + (13.75*weight) + (5*height) - (6.76*age)
        bmi = Math.round(bmi).toDouble()
        firestore?.collection("user")?.document(text_dev.text.toString())?.update("bmi", bmi.toString(), "gender" , gender)
    }

    private fun bmi_woman() {
        var height = EditText_height.text.toString().toInt()
        var weight = EditText_weight.text.toString().toInt()
        var age = EditText_age.text.toString().toInt()
        gender = "여자"

        bmi = 655.1 + (9.56*weight) + (1.85*height) - (4.68*age)
        bmi = Math.round(bmi).toDouble()
        firestore?.collection("user")?.document(text_dev.text.toString())?.update("bmi", bmi.toString(), "gender", gender)
    }

    private fun send_dev_data() {
        var userdata = userData(EditText_height.text.toString(), EditText_weight.text.toString(), EditText_age.text.toString(), bmi.toString(), gender)
        firestore?.collection("user")?.document(text_dev.text.toString())?.set(userdata)

        }
    }