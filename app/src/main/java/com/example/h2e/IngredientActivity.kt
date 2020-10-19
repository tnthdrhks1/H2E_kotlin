package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.Empty
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.android.synthetic.main.activity_ingredient.ButtonCal
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight1
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight2
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight3
import kotlinx.android.synthetic.main.activity_ingredient.dish_weight4
import kotlinx.android.synthetic.main.detailinreview.*
import java.time.LocalDate
import kotlin.math.roundToInt

var namedish1: Any? = null
var namedish2: Any? = null
var namedish3: Any? = null
var namedish4: Any? = null
var namedish5: Any? = null
var namedish6: Any? = null

var tan : String = "0"
var dan : String = "0"
var ji : String = "0"
var kcal : String = "0"

var tan_data_detail : Double = 0.0
var dan_data_detail : Double = 0.0
var ji_data_detail : Double = 0.0
var kcal_data_detail : Double = 0.0

var Weight_of_Food_sum : Double  = 0.0

var gok_data : String = "0"
var uju_data : String = "0"
var ujung_data : String = "0"
var ugo_data : String = "0"
var veg_data : String = "0"
var fruit_data : String = "0"
var milk_data : String = "0"
var fat_data : String = "0"
var Kcal_data : String = "0"

var gok_data_detail : Double = 0.0
var uju_data_detail : Double = 0.0
var ujung_data_detail : Double = 0.0
var ugo_data_detail : Double = 0.0
var veg_data_detail : Double = 0.0
var fruit_data_detail : Double = 0.0
var milk_data_detail : Double = 0.0
var fat_data_detail : Double = 0.0

class SetMeatData (var Dish1 : String? = null, var Dish2 : String? = null, var Dish3 : String? = null, var Dish4 : String? = null, var Dish5 : String? = null, var Dish6 : String? = null,
                   var Kcal : String? = null, var Tan : String? = null, var Dan : String? = null, var Ji : String? = null,
                   var Gok_data : String? = null, var Uju_data : String? = null, var Ujung_data : String? = null, var Ugo_data : String? = null, var Veg_data : String? = null,
                   var Fat_data : String? = null, var Milk_data : String? = null, var Fruit_data : String? = null)

class MealDetailClass (var Dish : String? = null, var Kcal : String? = null, var Gok_data : String? = null, var Uju_data : String? = null, var Ujung_data : String? = null, var Ugo_data : String? = null,
                       var Veg_data : String? = null, var Fat_data : String? = null, var Milk_data : String? = null, var Fruit_data : String? = null)

class IngredientActivity : AppCompatActivity() {

    lateinit var Meal : String

    val firestore = FirebaseFirestore.getInstance()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    var onlyDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)

        if (intent.hasExtra("meal_time")) {
            Meal = intent.getStringExtra("meal_time")
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


            if(namedish1.toString().isNotEmpty()) {
                IngreDetail(namedish1.toString(), "dish1", Meal)
            } else{firestore.collection(user + Meal).document("dish1").delete() }
            if(namedish2.toString().isNotEmpty()) {
                IngreDetail(namedish2.toString(), "dish2", Meal)
            } else{firestore.collection(user + Meal).document("dish2").delete()}

            if(namedish3.toString().isNotEmpty()) {
                IngreDetail(namedish3.toString(), "dish3", Meal)
            } else{firestore.collection(user + Meal).document("dish3").delete()}

            if(namedish4.toString().isNotEmpty()) {
                IngreDetail(namedish4.toString(), "dish4", Meal)
            } else{firestore.collection(user + Meal).document("dish4").delete()}

            if(namedish5.toString().isNotEmpty()) {
                IngreDetail(namedish5.toString(), "dish5", Meal)
            } else{firestore.collection(user + Meal).document("dish5").delete()}

            if(namedish6.toString().isNotEmpty()) {
                IngreDetail(namedish6.toString(), "dish6", Meal)
            } else{firestore.collection(user + Meal).document("dish6").delete()}

            GetIngredinet(namedish1.toString(), "dishstart1", "dishstop1", "dish1", Meal)
            GetIngredinet(namedish2.toString(), "dishstart2", "dishstop2", "dish2", Meal)
            GetIngredinet(namedish3.toString(), "dishstart3", "dishstop3", "dish3", Meal)
            GetIngredinet(namedish4.toString(), "dishstart4", "dishstop4", "dish4", Meal)
            GetIngredinet(namedish5.toString(), "dishstart5", "dishstop5", "dish5", Meal)
            GetIngredinet(namedish6.toString(), "dishstart6", "dishstop6", "dish6", Meal)


            dish_weight1.setText(tan_data_detail.toString())
            dish_weight2.setText(dan_data_detail.toString())
            dish_weight3.setText(ji_data_detail.toString())
            dish_weight4.setText(kcal_data_detail.toString())

            GiveData()

            gok_data_detail = 0.0
            uju_data_detail  = 0.0
            ujung_data_detail  = 0.0
            ugo_data_detail  = 0.0
            veg_data_detail  = 0.0
            fruit_data_detail  = 0.0
            milk_data_detail  = 0.0
            fat_data_detail  = 0.0
            tan_data_detail  = 0.0
            dan_data_detail  = 0.0
            ji_data_detail  = 0.0
            kcal_data_detail  = 0.0

        }

        Button_reset.setOnClickListener {
            tan = "0"
            dan ="0"
            ji = "0"
            kcal = "0"

            dish_weight1.setText(tan)
            dish_weight2.setText(dan)
            dish_weight3.setText(ji)
            dish_weight4.setText(kcal)
        }

        ingredient_button_next.setOnClickListener {
            startActivity(Intent(this, DayActivity::class.java))
        }
    }
    private fun IngreDetail(DishName : String, Dish : String, Time : String) {


        var mealdetaildata = MealDetailClass(DishName , kcal_data_detail.toString(), gok_data_detail.toString(), uju_data_detail.toString(),
            ujung_data_detail.toString(), veg_data_detail.toString(), fat_data_detail.toString(), milk_data_detail.toString(), fruit_data_detail.toString())

        firestore.collection(user + Time).document(Dish).set(mealdetaildata)
    }


    fun GetIngredinet(FoodName : String, FieldStart: String, FieldStop: String, Field : String,  Time : String) {
        firestore.collection("user").get().addOnSuccessListener { task ->
            for (document in task) {
                if (document.id == "H2E") {
                    var weight1 = document.data[FieldStart].toString().toDouble() - document.data[FieldStop].toString().toDouble()

                        firestore.collection("meal500").get().addOnSuccessListener { task ->
                            for (document in task) {
                                if (document.id == FoodName) {
                                    if (document.data["tan"] is String) {
                                        tan = String.format("%.2f", document.data["tan"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["dan"] is String) {
                                        dan = String.format("%.2f", document.data["dan"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["ji"] is String) {
                                        ji = String.format("%.2f", document.data["ji"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["kcal_"] is String) {
                                        kcal = String.format("%.2f", document.data["kcal_"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["gok"] is String) {
                                        gok_data = String.format("%.2f", document.data["gok"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["uju"] is String) {
                                        uju_data = String.format("%.2f", document.data["uju"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["ujung"] is String) {
                                        ujung_data = String.format("%.2f", document.data["ujung"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["ugo"] is String) {
                                        ugo_data = String.format("%.2f", document.data["ugo"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["veg"] is String) {
                                        veg_data = String.format("%.2f", document.data["veg"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["fat"] is String) {
                                        fat_data = String.format("%.2f", document.data["fat"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["milk"] is String) {
                                        milk_data = String.format("%.2f", document.data["milk"].toString().toDouble() / 100 * weight1)
                                    }
                                    if (document.data["fruit"] is String) {
                                        fruit_data = String.format("%.2f", document.data["fruit"].toString().toDouble() / 100 * weight1)
                                    }

                                    gok_data_detail += gok_data.toDouble()
                                    uju_data_detail += uju_data.toDouble()
                                    ujung_data_detail += ujung_data.toDouble()
                                    ugo_data_detail += ugo_data.toDouble()
                                    veg_data_detail += veg_data.toDouble()
                                    fat_data_detail += fat_data.toDouble()
                                    milk_data_detail += milk_data.toDouble()
                                    fruit_data_detail += fruit_data.toDouble()

                                    tan_data_detail += tan.toDouble()
                                    dan_data_detail += dan.toDouble()
                                    ji_data_detail += ji.toDouble()
                                    kcal_data_detail += kcal.toDouble()

                                    ujung_data_detail = String.format("%.2f", ujung_data_detail).toDouble()
                                    uju_data_detail = String.format("%.2f", uju_data_detail).toDouble()
                                    ugo_data_detail = String.format("%.2f", ugo_data_detail).toDouble()
                                    veg_data_detail = String.format("%.2f", veg_data_detail).toDouble()
                                    fat_data_detail = String.format("%.2f", fat_data_detail).toDouble()
                                    milk_data_detail = String.format("%.2f", milk_data_detail).toDouble()
                                    fruit_data_detail = String.format("%.2f", fruit_data_detail).toDouble()
                                    gok_data_detail = String.format("%.2f", gok_data_detail).toDouble()

                                    tan_data_detail = String.format("%.2f", tan_data_detail).toDouble()
                                    dan_data_detail = String.format("%.2f", dan_data_detail).toDouble()
                                    ji_data_detail = String.format("%.2f", ji_data_detail).toDouble()
                                    kcal_data_detail = String.format("%.2f", kcal_data_detail).toDouble()

                                    var mealdetaildata = MealDetailClass(FoodName , kcal, gok_data, uju_data,
                                        ujung_data, ugo_data, veg_data, fat_data, milk_data, fruit_data)

                                    firestore.collection(user + Time).document(Field).set(mealdetaildata)
                                }
                            }
                        }
                    }
                }
            }
        }

    fun GiveData(){
        var setmealdata = SetMeatData(namedish1.toString(), namedish2.toString(), namedish3.toString(), namedish4.toString(), namedish5.toString(), namedish6.toString(),
        kcal_data_detail.toString(), tan_data_detail.toString(), dan_data_detail.toString(), ji_data_detail.toString(), gok_data_detail.toString(), uju_data_detail.toString(),
            ujung_data_detail.toString(), ugo_data_detail.toString(), veg_data_detail.toString(), fat_data_detail.toString(),
            milk_data_detail.toString(), fruit_data_detail.toString())

        firestore?.collection(user)?.document(onlyDate.toString() + Meal)?.set(setmealdata)
    }
}