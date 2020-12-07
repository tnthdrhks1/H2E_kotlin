package com.example.h2e

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import java.time.LocalDate

class userData(
    var height: String? = null,
    var weight: String? = null,
    var age: String? = null,
    var bmi: String? = null,
    var gender: String?,
    var want_day: String?,
    var want_weight: String?
)

class BmiGroup(
    val Name: String? = null,
    val DietSystem: String? = null,
    var gok: String? = null,
    var uju: String? = null,
    var ujung: String? = null,
    var ugo: String? = null,
    var veg: String? = null,
    var fat: String? = null,
    var milk: String? = null,
    var fruit: String? = null,
    var CanEatKcal: String?
)
class updateweight(val LoseWeight: String? = null, val LoseDate: String? = null)

class ProfileActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_profile)

        var thelist =listOf(0, 1, 0, 2, 1, 0)
        var name00 = mutableListOf<String>()

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.radioman -> gender = "남자"
                R.id.radiowoman -> gender = "여자"
            }
        }

        radioGroup_diet.setOnCheckedChangeListener { radioGroup_diet, i ->
            when(i){
                R.id.radiobasic -> {
                    system1 = "profile_ingre_adopted"; system2 = "profile_ingre_second"; system3 =
                        "profile_ingre_third"
                }
                R.id.radiogodan -> {
                    system1 = "profile_ingre_second"; system2 = "profile_ingre_adopted"; system3 =
                        "profile_ingre_third"
                }
                R.id.radiowogoji -> {
                    system1 = "profile_ingre_second"; system2 = "profile_ingre_third"; system3 =
                        "profile_ingre_adopted"
                }
            }
        }
        RadioGroupExce.setOnCheckedChangeListener { RadioGroupExce, i ->
            when(i){
                R.id.ExcerVeryLow -> exce = "verylow"
                R.id.ExcerLow -> exce = "low"
                R.id.Excernormal -> exce = "normal"
                R.id.Excerhigh -> exce = "high"
                R.id.ExcerVeryhigh -> exce = "veryhigh"
            }
        }

        appCompatButton.setOnClickListener {
            send_dev_data()
        }

        button_next.setOnClickListener {
            startActivity(Intent(this, ErgometerActivity::class.java))
        }
    }

    private fun send_dev_data() {

        if(text_dev.text.toString().isEmpty() || EditText_height.text.toString().isEmpty() || EditText_weight.text.toString().isEmpty() || EditText_age.text.toString().isEmpty()
            || EditText_day.text.toString().isEmpty() || EditText_want_day.text.toString().isEmpty()){

            EditText_height.error = "id is empty"
            EditText_height.requestFocus()

            return
        }

        CalculateBmi()

        var userdata = userData(
            EditText_height.text.toString(),
            EditText_weight.text.toString(),
            EditText_age.text.toString(),
            bmi.toString(),
            gender,
            EditText_day.text.toString(),
            EditText_want_day.text.toString()
        )

        CanEatKcal = Math.round(
            bmi - ((EditText_weight.text.toString().toDouble() - EditText_want_day.text.toString()
                .toDouble()) * 6600 / EditText_day.text.toString().toDouble())
        ).toDouble()

        for(i in 0..EditText_day.text.toString().toInt()) {
            var LoseWeight = (EditText_weight.text.toString().toDouble() - EditText_want_day.text.toString().toDouble()) * 6600 / EditText_day.text.toString().toDouble() / 6600
            var loseweight = updateweight(
                String.format(
                    "%.2f",
                    EditText_weight.text.toString().toDouble() - LoseWeight * i
                ), onlyDate.plusDays(i.toLong()).toString()
            )
            firestore.collection(user + "DayWeight").document(
                onlyDate.plusDays(i.toLong()).toString()
            ).set(loseweight)
        }

        CalBmi(system1)
        godanCalBmi(system2)
        gojiCalBmi(system3)

        firestore?.collection(user)?.document("profile")?.set(userdata)
        firestore.collection(user + "DayWeight").document(onlyDate.toString()).update(
            "RealWeight",
            EditText_weight.text.toString()
        )
    }

    private fun CalculateBmi() {
        var height = EditText_height.text.toString().toInt()
        var weight = EditText_weight.text.toString().toInt()
        var age = EditText_age.text.toString().toInt()

        when(gender){
            "남자" -> bmi =
                Math.round(66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)).toDouble()
            "여자" -> bmi =
                Math.round(655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)).toDouble()
        }

        when(exce){
            "verylow" -> bmi *= 1.2
            "low" -> bmi *= 1.375
            "normal" -> bmi *= 1.55
            "high" -> bmi *= 1.725
            "veryhigh" -> bmi *= 1.9
        }
    }

    private fun CalBmi(system: String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "8",
                "0",
                "1",
                "0",
                "6",
                "3",
                "0",
                "1",
                CanEatKcal.toString()
            )
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "9",
                "0",
                "1",
                "0",
                "6",
                "3",
                "0",
                "1",
                CanEatKcal.toString()
            )
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "9",
                "0",
                "1",
                "0",
                "7",
                "4",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "10",
                "0",
                "1",
                "0",
                "7",
                "4",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "10",
                "0",
                "1",
                "0",
                "8",
                "5",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "11",
                "0",
                "1",
                "0",
                "8",
                "5",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "11",
                "0",
                "2",
                "0",
                "8",
                "5",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "12",
                "0",
                "2",
                "0",
                "8",
                "5",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "12",
                "0",
                "2",
                "0",
                "9",
                "6",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "13",
                "0",
                "2",
                "0",
                "9",
                "6",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "14",
                "0",
                "2",
                "0",
                "9",
                "6",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "14",
                "0",
                "3",
                "0",
                "9",
                "7",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "15",
                "0",
                "3",
                "0",
                "9",
                "7",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "16",
                "0",
                "3",
                "0",
                "9",
                "7",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        }
        else{
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "당뇨병학회 방식",
                "16",
                "0",
                "3",
                "0",
                "9",
                "7",
                "0.5",
                "3",
                CanEatKcal.toString()
            )
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }

    private fun godanCalBmi(system: String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "4",
                "0",
                "6",
                "3",
                "3",
                "0",
                "0",
                "1",
                CanEatKcal.toString()
            )
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "5",
                "0",
                "6",
                "3",
                "3",
                "0",
                "0",
                "1",
                CanEatKcal.toString()
            )
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "5",
                "0",
                "7",
                "3",
                "4",
                "0",
                "0",
                "1",
                CanEatKcal.toString()
            )
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "5",
                "0",
                "7",
                "3",
                "4",
                "1",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "6",
                "0",
                "7",
                "3",
                "4",
                "1",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "6",
                "0",
                "8",
                "3",
                "5",
                "1",
                "0.5",
                "2",
                CanEatKcal.toString()
            )
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "6",
                "0",
                "8",
                "3",
                "5",
                "1",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "6",
                "0",
                "9",
                "4",
                "6",
                "1",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "7",
                "0",
                "9",
                "4",
                "6",
                "1",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "7",
                "0",
                "10",
                "4",
                "7",
                "1",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "7",
                "0",
                "11",
                "4",
                "7",
                "2",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "8",
                "0",
                "11",
                "4",
                "7",
                "2",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "8",
                "0",
                "12",
                "4",
                "7",
                "2",
                "1",
                "3",
                CanEatKcal.toString()
            )
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "8",
                "0",
                "11",
                "4",
                "8",
                "2",
                "1.5",
                "4",
                CanEatKcal.toString()
            )
        }
        else{
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 저지방",
                "8",
                "0",
                "15",
                "4",
                "8",
                "2",
                "1.5",
                "4",
                CanEatKcal.toString()
            )
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }

    private fun gojiCalBmi(system: String) {
        if (1200 <= CanEatKcal && CanEatKcal < 1300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "0",
                "7",
                "3",
                "1",
                "8",
                "0",
                "0",
                CanEatKcal.toString()
            )
        } else if (1300 <= CanEatKcal && CanEatKcal < 1400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "0",
                "8",
                "3",
                "1",
                "8",
                "0",
                "0",
                CanEatKcal.toString()
            )
        } else if (1400 <= CanEatKcal && CanEatKcal < 1500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "1",
                "8",
                "3",
                "2",
                "9",
                "0",
                "0",
                CanEatKcal.toString()
            )
        } else if (1500 <= CanEatKcal && CanEatKcal < 1600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "1",
                "9",
                "3",
                "2",
                "9",
                "0",
                "0",
                CanEatKcal.toString()
            )
        } else if (1600 <= CanEatKcal && CanEatKcal < 1700) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "1",
                "9",
                "3",
                "2",
                "9",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (1700 <= CanEatKcal && CanEatKcal < 1800) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "1",
                "10",
                "3",
                "2",
                "9",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (1800 <= CanEatKcal && CanEatKcal < 1900) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "10",
                "3",
                "3",
                "10",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (1900 <= CanEatKcal && CanEatKcal < 2000) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "11",
                "3",
                "3",
                "10",
                "0.5",
                "1",
                CanEatKcal.toString()
            )
        } else if (2000 <= CanEatKcal && CanEatKcal < 2100) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "11",
                "3",
                "3",
                "11",
                "1",
                "1",
                CanEatKcal.toString()
            )
        } else if (2100 <= CanEatKcal && CanEatKcal < 2200) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "12",
                "3",
                "3",
                "11",
                "1",
                "1",
                CanEatKcal.toString()
            )
        } else if (2200 <= CanEatKcal && CanEatKcal < 2300) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "12",
                "3",
                "3",
                "12",
                "1",
                "2",
                CanEatKcal.toString()
            )
        } else if (2300 <= CanEatKcal && CanEatKcal < 2400) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "2",
                "13",
                "3",
                "3",
                "12",
                "1",
                "2",
                CanEatKcal.toString()
            )
        } else if (2400 <= CanEatKcal && CanEatKcal < 2500) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "3",
                "13",
                "3",
                "4",
                "13",
                "1",
                "2",
                CanEatKcal.toString()
            )
        } else if (2500 <= CanEatKcal && CanEatKcal < 2600) {
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "1",
                "3",
                "14",
                "3",
                "4",
                "13",
                "1",
                "2",
                CanEatKcal.toString()
            )
        }
        else{
            bmigroup = BmiGroup(
                text_dev.text.toString(),
                "고단백 고지방",
                "8",
                "0",
                "15",
                "3",
                "8",
                "2",
                "1.5",
                "4",
                CanEatKcal.toString()
            )
        }
        firestore?.collection(user)?.document(system)?.set(bmigroup)
    }
}
