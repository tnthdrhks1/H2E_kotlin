package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

var mainid : String = "null"

class ProfileActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    var bmi: Double = 0.0
    lateinit var gender: String
    var CanEatKcal: Double = 0.0

    lateinit var bmigroup : Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        appCompatButton.setOnClickListener {
            send_dev_data()
            CalCanEatBmi()
        }

        button_man.setOnClickListener {
            bmi_man()
        }

        button_woman.setOnClickListener {
            bmi_woman()
        }

        button_next.setOnClickListener {
            val nextIntent = Intent(this, CheckMealActivity::class.java)
            nextIntent.putExtra(
                "device_address",
                text_dev.text.toString()
            ) //key: "email", value: inputEmail
            startActivity(nextIntent)
        }
    }

    private fun bmi_man() {
        var height = EditText_height.text.toString().toInt()
        var weight = EditText_weight.text.toString().toInt()
        var age = EditText_age.text.toString().toInt()
        gender = "남자"

        bmi = 66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
        bmi = Math.round(bmi).toDouble()
    }

    private fun bmi_woman() {
        var height = EditText_height.text.toString().toInt()
        var weight = EditText_weight.text.toString().toInt()
        var age = EditText_age.text.toString().toInt()
        gender = "여자"

        bmi = 655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
        bmi = Math.round(bmi).toDouble()
    }

    private fun send_dev_data() {
        var userdata = userData(
            EditText_height.text.toString(),
            EditText_weight.text.toString(),
            EditText_age.text.toString(),
            bmi.toString(),
            gender
        )
        firestore?.collection("user")?.document(user)?.set(userdata)

    }

    private fun CalCanEatBmi() {
        CanEatKcal = bmi - ((EditText_weight.text.toString().toDouble() - EditText_want_day.text.toString().toDouble()) * 7700 / EditText_day.text.toString().toDouble())

        println(CanEatKcal)
        CalBmi()
    }

    private fun CalBmi() {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(8, 0, 1, 6, 3, 0.0, 1, CanEatKcal)
        }
        else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(9, 0, 1, 6, 3, 0.0, 1, CanEatKcal)
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(9, 0, 1, 7, 4, 0.5, 1, CanEatKcal)
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(10, 0, 1, 7, 4, 0.5, 1, CanEatKcal)
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(10, 0, 1, 8, 5, 0.5, 2, CanEatKcal)
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(11, 0, 1, 8, 5, 0.5, 2, CanEatKcal)
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(11, 0, 2, 8, 5, 0.5, 2, CanEatKcal)
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(12, 0, 2, 8, 5, 0.5, 2, CanEatKcal)
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(12, 0, 2, 9, 6, 0.5, 3, CanEatKcal)
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(13, 0, 2, 9, 6, 0.5, 3, CanEatKcal)
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(14, 0, 2, 9, 6, 0.5, 3, CanEatKcal)
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(14, 0, 3, 9, 7, 0.5, 3, CanEatKcal)
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(15, 0, 3, 9, 7, 0.5, 3, CanEatKcal)
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(16, 0, 3, 9, 7, 0.5, 3, CanEatKcal)
        }
        else{
            bmigroup = BmiGroup(1600, 0, 3, 9, 7, 0.5, 3, CanEatKcal)
        }
        firestore?.collection("user")?.document(user)?.set(bmigroup)
    }
}