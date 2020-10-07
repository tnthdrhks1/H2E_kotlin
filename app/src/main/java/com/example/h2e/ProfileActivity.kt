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

class ProfileActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    var bmi: Double = 0.0
    lateinit var gender: String
    var CanEatKcal: Double = 0.0

    lateinit var system1 : String
    lateinit var system2 : String
    lateinit var system3 : String

    lateinit var bmigroup : Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.radioman -> bmi_man()
                R.id.radiowoman -> bmi_woman()
            }
        }

        radioGroup_diet.setOnCheckedChangeListener { radioGroup_diet, i ->
            when(i){
                R.id.radiobasic -> {system1 = "profile_ingre_adopted"; system2 = "profile_ingre_second"; system3 = "profile_ingre_third"}
                R.id.radiogodan -> {system1 = "profile_ingre_second"; system2 = "profile_ingre_adopted"; system3 = "profile_ingre_third"}
                R.id.radiowogoji -> {system1 = "profile_ingre_second"; system2 = "profile_ingre_third"; system3 = "profile_ingre_adopted"}
            }
        }

        appCompatButton.setOnClickListener {
            send_dev_data()
            CalCanEatBmi()
        }

        button_next.setOnClickListener {
            startActivity(Intent(this, DayActivity::class.java))
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

        if(EditText_height.text.toString().isEmpty()){
            EditText_height.error = "id is empty"
            EditText_height.requestFocus()

            return
        }

        var userdata = userData(
            EditText_height.text.toString(),
            EditText_weight.text.toString(),
            EditText_age.text.toString(),
            bmi.toString(),
            gender,
            EditText_day.text.toString(),
            EditText_want_day.text.toString()
        )

        firestore?.collection(user)?.document("profile")?.set(userdata)

    }

    private fun CalCanEatBmi() {

        if(EditText_height.text.toString().isEmpty()){
            EditText_height.error = "height is empty"
            EditText_height.requestFocus()

            return
        }

        CanEatKcal = bmi - ((EditText_weight.text.toString().toDouble() - EditText_want_day.text.toString().toDouble()) * 7700 / EditText_day.text.toString().toDouble())
        CanEatKcal = Math.round(CanEatKcal).toDouble()

        CalBmi(system1)
        godanCalBmi(system2)
        gojiCalBmi(system3)
    }

    private fun CalBmi(system : String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"8", "0", "1", "6", "3", "0", "1", CanEatKcal.toString())
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"9", "0", "1", "6", "3", "0", "1", CanEatKcal.toString())
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"9", "0", "1", "7", "4", "0.5", "1", CanEatKcal.toString())
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"10", "0", "1", "7", "4", "0.5", "1", CanEatKcal.toString())
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"10", "0", "1", "8", "5", "0.5", "2", CanEatKcal.toString())
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"11", "0", "1", "8", "5", "0.5", "2", CanEatKcal.toString())
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"11", "0", "2", "8", "5", "0.5", "2", CanEatKcal.toString())
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"12", "0", "2", "8", "5", "0.5", "2", CanEatKcal.toString())
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"12", "0", "2", "9", "6", "0.5", "3", CanEatKcal.toString())
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"13", "0", "2", "9", "6", "0.5", "3", CanEatKcal.toString())
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"14", "0", "2", "9", "6", "0.5", "3", CanEatKcal.toString())
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"14", "0", "3", "9", "7", "0.5", "3", CanEatKcal.toString())
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"15", "0", "3", "9", "7", "0.5", "3", CanEatKcal.toString())
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" ,"16", "0", "3", "9", "7", "0.5", "3", CanEatKcal.toString())
        }
        else{
            bmigroup = BmiGroup(text_dev.text.toString(), "당뇨병학회 방식" , "1600", "0", "3", "9", "7", "0.5", "3", CanEatKcal.toString())
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }

    private fun godanCalBmi(system : String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","4", "0", "9", "3", "0", "0", "1", CanEatKcal.toString())
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","5", "0", "9", "3", "0", "0", "1", CanEatKcal.toString())
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","5", "0", "10", "4", "0", "0", "1", CanEatKcal.toString())
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","5", "0", "10", "4", "1", "0.5", "2", CanEatKcal.toString())
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","6", "0", "10", "4", "1", "0.5", "2", CanEatKcal.toString())
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","6", "0", "11", "5", "1", "0.5", "2", CanEatKcal.toString())
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","6", "0", "11", "5", "1", "1", "3", CanEatKcal.toString())
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","6", "0", "12", "6", "1", "1", "3", CanEatKcal.toString())
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","7", "0", "12", "6", "1", "1", "3", CanEatKcal.toString())
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","7", "0", "13", "7", "1", "1", "3", CanEatKcal.toString())
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","7", "0", "14", "7", "2", "1", "3", CanEatKcal.toString())
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","8", "0", "14", "7", "2", "1", "3", CanEatKcal.toString())
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","8", "0", "15", "7", "2", "1", "3", CanEatKcal.toString())
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","8", "0", "15", "8", "2", "1.5", "4", CanEatKcal.toString())
        }
        else{
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 저지방","8", "0", "15", "8", "2", "1.5", "4", CanEatKcal.toString())
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }

    private fun gojiCalBmi(system : String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "0", "7", "1", "8", "0", "0", CanEatKcal.toString())
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "0", "8", "1", "8", "0", "0", CanEatKcal.toString())
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "1", "8", "2", "9", "0", "0", CanEatKcal.toString())
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "1", "9", "2", "9", "0", "0", CanEatKcal.toString())
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "1", "9", "2", "9", "0.5", "1", CanEatKcal.toString())
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "1", "10", "2", "9", "0.5", "1", CanEatKcal.toString())
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "10", "3", "10", "0.5", "1", CanEatKcal.toString())
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "11", "3", "10", "0.5", "1", CanEatKcal.toString())
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "11", "3", "11", "1", "1", CanEatKcal.toString())
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "12", "3", "11", "1", "1", CanEatKcal.toString())
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "12", "3", "12", "1", "2", CanEatKcal.toString())
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "2", "13", "3", "12", "1", "2", CanEatKcal.toString())
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "3", "13", "4", "13", "1", "2", CanEatKcal.toString())
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "1", "3", "14", "4", "13", "1", "2", CanEatKcal.toString())
        }
        else{
            bmigroup = BmiGroup(text_dev.text.toString(), "고단백 고지방", "8", "0", "15", "8", "2", "1.5", "4", CanEatKcal.toString())
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }
}
