package com.example.h2e

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import java.time.LocalDate

class Profile2Activity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    var bmi: Double = 0.0
    lateinit var arrlist : String
    lateinit var gender: String
    lateinit var exce : String

    var CanEatKcal: Double = 0.0

    lateinit var system1 : String
    lateinit var system2 : String
    lateinit var system3 : String

    lateinit var bmigroup : Any
    var onlyDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile2)

//        var thelist =listOf(0, 1, 0, 2, 1, 0)
//        var name00 = mutableListOf<String>()
//
//        firestore.collection("xcorps").get().addOnSuccessListener { task ->
//            for (document in task) {
//                if (document.id == "hearth") {
//                    var listsu = document.data.size
//                    for (i in 0..listsu - 1) {
//                        arrlist = document.data["$i"].toString()
//                        name00.add(arrlist)
//                    }
//                }
//            }
//            text_dev.setText(name00.toString())
//        }
        appCompatButton.setOnClickListener {
            send_dev_data()
        }

        button_next.setOnClickListener {
            startActivity(Intent(this, ErgometerActivity::class.java))
        }
    }

    private fun send_dev_data() {

        if(text_dev.text.toString().isEmpty() || EditText_height.text.toString().isEmpty() || EditText_weight.text.toString().isEmpty() || EditText_age.text.toString().isEmpty()
           ){

            EditText_height.error = "id is empty"
            EditText_height.requestFocus()

            return
        }

        var userdata = userData(
            EditText_height.text.toString(),
            EditText_weight.text.toString(),
            EditText_age.text.toString(),
            bmi.toString(),
            "gender",
            "dd", "bb"
        )

        firestore?.collection(user)?.document("profile")?.set(userdata)
    }
}
