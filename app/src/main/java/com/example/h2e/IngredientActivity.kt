package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.android.synthetic.main.activity_ingredient.ButtonCal
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight1
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight2
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight3
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight4
import java.time.LocalDate
import kotlin.math.roundToInt

var namedish1: Any? = null
var namedish2: Any? = null
var namedish3: Any? = null
var namedish4: Any? = null
var namedish5: Any? = null
var namedish6: Any? = null

var tan : Double = 0.0
var dan : Double = 0.0
var ji : Double = 0.0
var kcal : Double = 0.0

var ingre1 : Any? = '0'
var ingre2 : Any? = '0'
var ingre3 : Any? = '0'
var ingre4 : Any? = '0'
var ingre5 : Any? = '0'
var ingre6 : Any? = '0'
var ingre7 : Any? = '0'
var ingre8 : Any? = '0'
var ingre9 : Any? = '0'
var ingre10 : Any? = '0'
var ingre11 : Any? = '0'
var ingre12 : Any? = '0'
var ingre13 : Any? = '0'
var ingre14 : Any? = '0'
var ingre15 : Any? = '0'

var Weight_of_Food1 : Any? = "0"
var Weight_of_Food2 : Any? = '0'
var Weight_of_Food3 : Any? = '0'
var Weight_of_Food4 : Any? = '0'
var Weight_of_Food5 : Any? = '0'
var Weight_of_Food6 : Any? = '0'
var Weight_of_Food7 : Any? = '0'
var Weight_of_Food8 : Any? = '0'
var Weight_of_Food9 : Any? = '0'
var Weight_of_Food10 : Any? = '0'
var Weight_of_Food11 : Any? = '0'
var Weight_of_Food12 : Any? = '0'
var Weight_of_Food13 : Any? = '0'
var Weight_of_Food14 : Any? = '0'
var Weight_of_Food15 : Any? = '0'

var Weight_of_Food_sum : Double  = 0.0

var gok_data : Double = 0.0
var uju_data : Double = 0.0
var ujung_data : Double = 0.0
var veg_data : Double = 0.0
var fruit_data : Double = 0.0
var milk_data : Double = 0.0
var fat_data : Double = 0.0

class IngredientActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    lateinit var Meal : String

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    var onlyDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)

        if (intent.hasExtra("meal")) {
            Meal = intent.getStringExtra("meal")
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

                    }
                }
            }
        }


        ButtonCal.setOnClickListener {

            FindIngredient(namedish1.toString(), "dish1")
            FindIngredient(namedish2.toString(), "dish2")
            FindIngredient(namedish3.toString(), "dish3")
            FindIngredient(namedish4.toString(), "dish4")
            FindIngredient(namedish5.toString(), "dish5")
            FindIngredient(namedish6.toString(), "dish6")

            dish_weight1.setText(tan.toString())
            dish_weight2.setText(dan.toString())
            dish_weight3.setText(ji.toString())
            dish_weight4.setText(kcal.toString())

            gok_data = 0.0

            GiveData()

            tan = 0.0
            dan = 0.0
            ji = 0.0
            kcal = 0.0
        }

        Button_reset.setOnClickListener {
            tan = 0.0
            dan = 0.0
            ji = 0.0
            kcal = 0.0

            dish_weight1.setText(tan.toString())
            dish_weight2.setText(dan.toString())
            dish_weight3.setText(ji.toString())
            dish_weight4.setText(kcal.toString())
        }

        ingredient_button_next.setOnClickListener {
            startActivity(Intent(this, DayActivity::class.java))
        }
    }

    private fun FindIngredient(FoodName: Any? , dish : String) {
        firestore.collection("meal")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.id == FoodName) {
                            ingre1 = document.data["ingre1"]
                            ingre2 = document.data["ingre2"]
                            ingre3 = document.data["ingre3"]
                            ingre4 = document.data["ingre4"]
                            ingre5 = document.data["ingre5"]
                            ingre6 = document.data["ingre6"]
                            ingre7 = document.data["ingre7"]
                            ingre8 = document.data["ingre8"]
                            ingre9 = document.data["ingre7"]
                            ingre10 = document.data["ingre10"]
                            ingre11 = document.data["ingre11"]
                            ingre12 = document.data["ingre12"]
                            ingre13 = document.data["ingre13"]
                            ingre14 = document.data["ingre14"]
                            ingre15 = document.data["ingre15"]

                            Weight_of_Food1 = document.data["num1"]
                            Weight_of_Food2 = document.data["num2"]
                            Weight_of_Food3 = document.data["num3"]
                            Weight_of_Food4 = document.data["num4"]
                            Weight_of_Food5 = document.data["num5"]
                            Weight_of_Food6 = document.data["num6"]
                            Weight_of_Food7 = document.data["num7"]
                            Weight_of_Food8 = document.data["num8"]
                            Weight_of_Food9 = document.data["num9"]
                            Weight_of_Food10 = document.data["num10"]
                            Weight_of_Food11 = document.data["num11"]
                            Weight_of_Food12 = document.data["num12"]
                            Weight_of_Food13 = document.data["num13"]
                            Weight_of_Food14 = document.data["num14"]
                            Weight_of_Food15 = document.data["num15"]

                            if (Weight_of_Food1 == null) {
                                Weight_of_Food1 = 0
                            }
                            if (Weight_of_Food2 == null) {
                                Weight_of_Food2 = 0
                            }
                            if (Weight_of_Food3 == null) {
                                Weight_of_Food3 = 0
                            }
                            if (Weight_of_Food4 == null) {
                                Weight_of_Food4 = 0
                            }
                            if (Weight_of_Food5 == null) {
                                Weight_of_Food5 = 0
                            }
                            if (Weight_of_Food6 == null) {
                                Weight_of_Food6 = 0
                            }
                            if (Weight_of_Food7 == null) {
                                Weight_of_Food7 = 0
                            }
                            if (Weight_of_Food8 == null) {
                                Weight_of_Food8 = 0
                            }
                            if (Weight_of_Food9 == null) {
                                Weight_of_Food9 = 0
                            }
                            if (Weight_of_Food10 == null) {
                                Weight_of_Food10 = 0
                            }
                            if (Weight_of_Food11 == null) {
                                Weight_of_Food11 = 0
                            }
                            if (Weight_of_Food12 == null) {
                                Weight_of_Food12 = 0
                            }
                            if (Weight_of_Food13 == null) {
                                Weight_of_Food13 = 0
                            }
                            if (Weight_of_Food14 == null) {
                                Weight_of_Food14 = 0
                            }
                            if (Weight_of_Food15 == null) {
                                Weight_of_Food15 = 0
                            }

                            Weight_of_Food_sum = Weight_of_Food1.toString().toDouble() + Weight_of_Food2.toString().toDouble() + Weight_of_Food3.toString().toDouble()
                            +Weight_of_Food4.toString().toDouble() + Weight_of_Food5.toString().toDouble() + Weight_of_Food6.toString().toDouble() + Weight_of_Food7.toString().toDouble()
                            +Weight_of_Food8.toString().toDouble() + Weight_of_Food9.toString().toDouble() + Weight_of_Food10.toString().toDouble() + Weight_of_Food11.toString().toDouble()
                            +Weight_of_Food12.toString().toDouble() + Weight_of_Food13.toString().toDouble() + Weight_of_Food14.toString().toDouble() + Weight_of_Food15.toString().toDouble()

                            BeforeGetIngredient(dish) // 라즈베리파이 무게 가져오기
                    }
                }
            }
        }
    }

    fun BeforeGetIngredient(Field_name: String) {

        GetIngredinet(ingre1.toString(), Field_name, Weight_of_Food1.toString())
        GetIngredinet(ingre2.toString(), Field_name, Weight_of_Food2.toString())
        GetIngredinet(ingre3.toString(), Field_name, Weight_of_Food3.toString())
        GetIngredinet(ingre4.toString(), Field_name, Weight_of_Food4.toString())
        GetIngredinet(ingre5.toString(), Field_name, Weight_of_Food5.toString())
        GetIngredinet(ingre6.toString(), Field_name, Weight_of_Food6.toString())
        GetIngredinet(ingre7.toString(), Field_name, Weight_of_Food7.toString())
        GetIngredinet(ingre8.toString(), Field_name, Weight_of_Food8.toString())
        GetIngredinet(ingre9.toString(), Field_name, Weight_of_Food9.toString())
        GetIngredinet(ingre10.toString(), Field_name, Weight_of_Food10.toString())
        GetIngredinet(ingre11.toString(), Field_name, Weight_of_Food11.toString())
        GetIngredinet(ingre12.toString(), Field_name, Weight_of_Food12.toString())
        GetIngredinet(ingre13.toString(), Field_name, Weight_of_Food13.toString())
    }

    fun GetIngredinet(Docu: String?, Field: String, gram: String) {

        if (gram is String) {
            var wine0 = gram.toDouble()

            firestore.collection("user")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            if (document.id == "ras") {
                                var weight1 = document.data[Field]
                                if (weight1 is String) {
                                    weight1 = weight1.toDouble()

                                    firestore.collection("food")
                                        .get()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                for (document in task.result!!) {
                                                    if (document.id == Docu) {
                                                        var tan0: Any? = document.data["tan"]
                                                        if (tan0 is String) {
                                                            tan0 = tan0.toDouble()
                                                            tan0 = tan0 * wine0 / 100
                                                            tan0 = tan0  / Weight_of_Food_sum * weight1
                                                            tan = tan + tan0
                                                            tan = Math.round(tan).toDouble()
                                                        }

                                                        var dan0 = document.data["dan"]
                                                        if (dan0 is String) {
                                                            dan0 = dan0.toDouble()
                                                            dan0 = dan0 * wine0 / 100
                                                            dan0 = dan0  / Weight_of_Food_sum * weight1
                                                            dan = dan + dan0

                                                            dan = Math.round(dan).toDouble()
                                                        }

                                                        var ji0 = document.data["ji"]
                                                        if (ji0 is String) {
                                                            ji0 = ji0.toDouble()
                                                            ji0 = ji0 * wine0 / 100
                                                            ji0 = ji0  / Weight_of_Food_sum * weight1
                                                            ji = ji + ji0

                                                            ji = Math.round(ji).toDouble()
                                                        }

                                                        var kcal0 = document.data["kcal"]
                                                        if (kcal0 is String) {
                                                            kcal0 = kcal0.toDouble()
                                                            kcal0 = kcal0 * wine0 / 100
                                                            kcal0 = kcal0  / Weight_of_Food_sum * weight1
                                                            kcal = kcal + kcal0

                                                            kcal = Math.round(kcal).toDouble()
                                                             }


                                                        if (document.data["group"] == "곡류군"){
                                                            var gok_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 100
                                                            gok_data = gok_data + gok_data0
                                                            gok_data = (gok_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "저지방 어육류군"){
                                                            var uju_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 50
                                                            uju_data = uju_data + uju_data0
                                                            uju_data = (uju_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "중지방 어육류군"){
                                                            var ujung_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 75
                                                            ujung_data = ujung_data + ujung_data0
                                                            ujung_data = (ujung_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "고지방 어육류군"){
                                                            var ugo_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 100
                                                            ujung_data = ujung_data + ugo_data0
                                                            ujung_data = (ujung_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "채소군"){
                                                            var veg_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 20
                                                            veg_data = veg_data + veg_data0
                                                            veg_data = (veg_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "지방군"){
                                                            var fat_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 45
                                                            fat_data = fat_data + fat_data0
                                                            fat_data = (fat_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "지방군"){
                                                            var fat_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 45
                                                            fat_data = fat_data + fat_data0
                                                            fat_data = (fat_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "우유군"){
                                                            var milk_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 125
                                                            milk_data = milk_data + milk_data0
                                                            milk_data = (milk_data*100).roundToInt() / 100.0
                                                            }
                                                        else if (document.data["group"] == "과일군"){
                                                            var fruit_data0 : Double = wine0 * weight1 / Weight_of_Food_sum / 125
                                                            fruit_data = fruit_data + fruit_data0
                                                            fruit_data = (fruit_data*100).roundToInt() / 100.0
                                                            }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun GiveData(){
        var setmealdata = SetMeatData(namedish1.toString(), namedish2.toString(), namedish3.toString(), namedish4.toString(), namedish5.toString(), namedish6.toString(),
        kcal, tan, dan, ji, gok_data, uju_data, ujung_data, veg_data, fat_data, milk_data, fruit_data)
        firestore?.collection(user)?.document(onlyDate.toString() + Meal)?.set(setmealdata)
    }
}
