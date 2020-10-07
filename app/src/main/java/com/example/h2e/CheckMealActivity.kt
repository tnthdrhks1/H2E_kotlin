package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_profile.*

class CheckMealActivity : AppCompatActivity() {

    lateinit var Meal_time: String
    lateinit var nameDishNumber: String
    lateinit var nameDishNumber_out: String
    var DishName: String = "null"
    val firestore = FirebaseFirestore.getInstance()
    lateinit var Meal0: Intent

    lateinit var test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_meal)

        if (intent.hasExtra("meal")) {
            Meal_time = intent.getStringExtra("meal")
        }

        if (intent.hasExtra("nameDishNumber_out")) {
            nameDishNumber_out = intent.getStringExtra("nameDishNumber_out")

        } else {
        }

        //////////////////////////
        //     음식이름 settext
        /////////////////////////

        if (intent.hasExtra("DishName")) {
            DishName = intent.getStringExtra("DishName")

            if (nameDishNumber_out == "nameDish1") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish1", DishName)
            }
            else if (nameDishNumber_out == "nameDish2") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish2", DishName)
            }
            else if (nameDishNumber_out == "nameDish3") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish3", DishName)
            }
            else if (nameDishNumber_out == "nameDish4") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish4", DishName)
            }
            else if (nameDishNumber_out == "nameDish5") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish5", DishName)
            }
            else if (nameDishNumber_out == "nameDish6") {
                firestore?.collection("user")?.document("DishName")?.update("nameDish6", DishName)
            }
        } else {
        }

        ButtonCal.setOnClickListener {
            receiveData()
        }

        ButtonZeroset.setOnClickListener {
            DishReset(dish1, "nameDish1")
            DishReset(dish2, "nameDish2")
            DishReset(dish3, "nameDish3")
            DishReset(dish4, "nameDish4")
            DishReset(dish5, "nameDish5")
            DishReset(dish6, "nameDish6")
        }

        ButtonNext.setOnClickListener {
            Meal0 = Intent(this, IngredientActivity::class.java)
            Meal0.putExtra("meal_time", Meal_time)

            startActivity(Meal0)
        }
    }

    private fun DishReset(dish:TextView, dishnum : String) {
        dish.setText("")
        firestore?.collection("user")?.document("DishName")?.update(dishnum, dish.text.toString())
    }

    fun dish1Onclick1(v: View) {
        DishList("nameDish1")
    }

    fun dish1Onclick2(v: View) {
        DishList("nameDish2")
    }
    fun dish1Onclick3(v: View) {
        DishList("nameDish3")
    }
    fun dish1Onclick4(v: View) {
        DishList("nameDish4")
    }
    fun dish1Onclick5(v: View) {
        DishList("nameDish5")
    }
    fun dish1Onclick6(v: View) {
        DishList("nameDish6")
    }

    private fun DishList(namedish : String) {

        nameDishNumber = namedish
        Meal0 = Intent(this, RecyclerActivity::class.java)
        Meal0.putExtra("meal_time", Meal_time)
        Meal0.putExtra("nameDishNumber", nameDishNumber)

        startActivity(Meal0)
    }

    private fun receiveData() {
        firestore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.id == "rasbian") {
                            var weight1 = document.data["dish1"]
                            dish_weight1.setText(weight1.toString())

                            var weight2 = document.data["dish2"]
                            dish_weight2.setText(weight2.toString())

                            var weight3 = document.data["dish3"]
                            dish_weight3.setText(weight3.toString())

                            var weight4 = document.data["dish4"]
                            dish_weight4.setText(weight4.toString())

                            var weight5 = document.data["dish5"]
                            dish_weight5.setText(weight5.toString())

                            var weight6 = document.data["dish6"]
                            dish_weight6.setText(weight6.toString())
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

    public override fun onStart() {
        super.onStart()

        firestore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.id == "DishName") {
                            var nameDish1 = document.data["nameDish1"]
                            dish1.setText(nameDish1.toString())

                            var nameDish2 = document.data["nameDish2"]
                            dish2.setText(nameDish2.toString())

                            var nameDish3 = document.data["nameDish3"]
                            dish3.setText(nameDish3.toString())

                            var nameDish4 = document.data["nameDish4"]
                            dish4.setText(nameDish4.toString())

                            var nameDish5 = document.data["nameDish5"]
                            dish5.setText(nameDish5.toString())

                            var nameDish6 = document.data["nameDish6"]
                            dish6.setText(nameDish6.toString())
                    }
                }
            }
        }
    }
}