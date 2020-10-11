package com.example.h2e

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_check_meal.ButtonCal
import kotlinx.android.synthetic.main.activity_check_meal.dish1
import kotlinx.android.synthetic.main.activity_check_meal.dish2
import kotlinx.android.synthetic.main.activity_check_meal.dish3
import kotlinx.android.synthetic.main.activity_check_meal.dish4
import kotlinx.android.synthetic.main.activity_check_meal.dish_weight1
import kotlinx.android.synthetic.main.activity_check_meal.dish_weight2
import kotlinx.android.synthetic.main.activity_check_meal.dish_weight3
import kotlinx.android.synthetic.main.activity_check_meal.dish_weight4
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class CheckMealActivity : AppCompatActivity() {

    lateinit var Meal_time: String
    lateinit var nameDishNumber: String
    lateinit var nameDishNumber_out: String

    var DishName: String = "null"
    val firestore = FirebaseFirestore.getInstance()
    lateinit var Meal0: Intent
    var ResultSplit = mutableListOf<List<String>>()

    var ResultSplitList = arrayListOf<String>()
    var MealNameArray : ArrayList<MyMeal> = arrayListOf()

    //var MealNameArray = mutableListOf<String>()

    var ResultSplitString : String = "null"
    var ResultForSearch : String = "null"

    var i = 1
    lateinit var dishnumber : TextView
    lateinit var FirebasenameDish : String

    var MatchUP = false

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

    ///////////////////////////////
    ///       음성인식 시작
    //////////////////////////////

    fun SpeechBefore1(view : View?){
        i = 0
        dishnumber = dish1
        FirebasenameDish = "nameDish1"
        getSpeechInput()
    }

    fun SpeechBefore2(view : View?){
        i = 1
        dishnumber = dish2
        FirebasenameDish = "nameDish2"
        getSpeechInput()
    }

    fun SpeechBefore3(view : View?){
        i = 2
        dishnumber = dish3
        FirebasenameDish = "nameDish3"
        getSpeechInput()
    }

    fun SpeechBefore4(view : View?){
        i = 3
        dishnumber = dish4
        FirebasenameDish = "nameDish4"
        getSpeechInput()
    }

    fun SpeechBefore5(view : View?){
        i = 4
        dishnumber = dish5
        FirebasenameDish = "nameDish5"
        getSpeechInput()
    }

    fun SpeechBefore6(view : View?){
        i = 5
        dishnumber = dish6
        FirebasenameDish = "nameDish6"
        getSpeechInput()
    }

    fun getSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 10) }
        else { }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ResultSplitString = result[0].replace(" ", "")

                DishMatchUp(FirebasenameDish, dishnumber)

            }
        }
    }

    private fun DishMatchUp(firebasenamedish : String,  Dishnumer: TextView) {

        MealNameArray.clear()
        ResultSplitList.clear()

        var IList = listOf(0, 1, 0, 0, 1, 2)
        var JList = listOf(0, 0, 1, 2, 1, 0)

        for (i in 0..6) {
            if (ResultSplitString.length <= 2 && i == 1) {
                break
            } else if (ResultSplitString.length == 3 && i == 3) {
                break
            } else if (ResultSplitString.length >= 3 && i == 6) {
                break
            }

            ResultForSearch = ResultSplitString.substring(IList[i], ResultSplitString.length.toString().toInt() - JList[i])

            println(ResultForSearch)

            firestore?.collection("meal")
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    for (snapshot in querySnapshot!!.documents) {
                        var add = true
                        if (snapshot.getString("Aname")!!.contains(ResultForSearch)) {
                            var item = snapshot.toObject(MyMeal::class.java)
//                                MealNameArray.add(item!!)
                        if(MealNameArray.size == 0) {
                            //MealNameArray.add(item!!)
                            MealNameArray.add(item!!)
                        }
                        else {
                            for(q in 0..MealNameArray.size-1) {
                                if (MealNameArray[q].Aname.toString() == item?.Aname.toString()){
                                    add = false
                                }
                            }
                            if(add) {
                                MealNameArray.add(item!!)
                            }
                            if (ResultSplitList.size == 0) {
                                //MealNameArray.add(item!!)
                                ResultSplitList.add(item?.Aname.toString())
                            } else {
                                for (q in 0..ResultSplitList.size - 1) {
                                    if (ResultSplitList[q] == item?.Aname.toString()) {
                                        add = false
                                    }
                                }
                                if (add) {
                                    ResultSplitList.add(item?.Aname.toString())
                                }
>>>>>>> master
                            }
                        }
                    }

<<<<<<< HEAD
                for (Nuum in 0..MealNameArray.size-1) {
                    if (MealNameArray[Nuum].Aname.toString() == ResultSplitString) { // 제육볶음이 있으면
                        Dishnumer.setText(MealNameArray[Nuum].Aname.toString())
                        newDish.setText(MealNameArray.toString())
                        firestore.collection("user").document("DishName").update(firebasenamedish, MealNameArray[Nuum].Aname.toString())
                        break
                    } else { // 제육볶음이 없으면
                        if (MealNameArray.size != 0) {
                            newDish.setText(MealNameArray.toString())

                            Dishnumer.setText(MealNameArray[0].Aname.toString())
                            firestore.collection("user").document("DishName").update(firebasenamedish, MealNameArray[0].Aname.toString())
                        } else {
                            Toast.makeText(baseContext, "매치업 실패.", Toast.LENGTH_SHORT)
                                .show()
=======
                    for (Nuum in 0..ResultSplitList.size - 1) {
                        if (ResultSplitList[Nuum] == ResultSplitString) { // 제육볶음이 있으면
                            Dishnumer.setText(ResultSplitList[Nuum])
                            newDish.setText(ResultSplitList.toString())
                            firestore.collection("user").document("DishName").update(firebasenamedish, ResultSplitList[Nuum])
                            break
                        } else { // 제육볶음이 없으면
                            if (ResultSplitList.size != 0) {
                                newDish.setText(ResultSplitList.toString())
                                Dishnumer.setText(ResultSplitList[0])

                                firestore.collection("user").document("DishName").update(firebasenamedish, ResultSplitList[0])
                            } else {
                                Toast.makeText(baseContext, "매치업 실패.", Toast.LENGTH_SHORT).show()
>>>>>>> master
                            }
                    }
                }
            }
        }
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