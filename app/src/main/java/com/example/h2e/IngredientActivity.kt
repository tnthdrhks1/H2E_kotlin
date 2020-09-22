package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.android.synthetic.main.activity_ingredient.ButtonCal
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight1
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight2
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight3
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight4
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight5
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight6

var namedish1: Any? = null
var namedish2: Any? = null
var namedish3: Any? = null
var namedish4: Any? = null
var namedish5: Any? = null
var namedish6: Any? = null

var dish01: Any? = null
var dish02: Any? = null
var dish03: Any? = null
var dish04: Any? = null
var dish05: Any? = null
var dish06: Any? = null

var tan : Double = 0.0
var dan : Double = 0.0
var ji : Double = 0.0
var kcal : Double = 0.0

var Weight_Of_Dish : Double = 0.0

class IngredientActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)

        if (intent.hasExtra("device_address")) {
            dev_address = intent.getStringExtra("device_address")
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
        }

        firestore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.id == "DishName") {
                            namedish1 = document.data["nameDish1"]
                            namedish2 = document.data["nameDish2"]
                            namedish3 = document.data["nameDish3"]
                            namedish4 = document.data["nameDish4"]
                            namedish5 = document.data["nameDish5"]
                            namedish6 = document.data["nameDish6"]
                        } else {
                            Toast.makeText(
                                baseContext, "error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        ButtonCal.setOnClickListener {
            GetIngredinet("user", namedish1.toString(), "dish1")
            GetIngredinet("user", namedish2.toString(), "dish2")
            dish_weight1.setText(tan.toString())
            dish_weight2.setText(dan.toString())
            dish_weight3.setText(ji.toString())
            dish_weight4.setText(kcal.toString())
            tan = 0.0
            dan = 0.0
            ji = 0.0
            kcal = 0.0
        }
    }

    private fun GetIngredinet(Coll: String, Docu: String, Field_weight: String) {
        firestore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.id == "ras") {
                            var dish_weight = document.data[Field_weight]

                            if(dish_weight is String){
                                Weight_Of_Dish = dish_weight.toDouble()
                            }


                            firestore.collection(Coll)
                                .get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        for (document in task.result!!) {
                                            if (document.id == Docu) {
                                                var tan0 = document.data["tan"]
                                                if (tan0 is String) {
                                                    tan = tan + tan0.toDouble()
                                                    tan =  Weight_Of_Dish * tan / 100
                                                    println(Weight_Of_Dish)
                                                }
                                                var dan0 = document.data["dan"]
                                                if (dan0 is String) {
                                                    dan = dan + dan0.toDouble()
                                                }
                                                var ji0 = document.data["ji"]
                                                if (ji0 is String) {
                                                    ji = ji + ji0.toDouble()
                                                }
                                                var kcal0 = document.data["kcal"]
                                                if (kcal0 is String) {
                                                    kcal = kcal + kcal0.toDouble()
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            baseContext, "error",
                                            Toast.LENGTH_SHORT
                                        ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}