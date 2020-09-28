package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_day.*
import java.time.LocalDate
import java.time.LocalDateTime

class DayActivity : AppCompatActivity() {
    lateinit var time : String
    lateinit var Meal : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        var dateAndtime: LocalDateTime = LocalDateTime.now()
        var onlyDate: LocalDate = LocalDate.now()

        var onlyDate2 = onlyDate.plusDays(1)
            }

    fun get_morning(v : View){
        time = "morning"
        Meal = Intent(this, IngredientActivity::class.java)
        Meal.putExtra("meal", time)

        startActivity(Meal)
    }

    fun get_lunch(v : View){
        time = "lunch"
        Meal = Intent(this, IngredientActivity::class.java)
        Meal.putExtra("meal", time)

        startActivity(Meal)
    }

    fun get_dinner(v : View){
        time = "dinner"
        Meal = Intent(this, IngredientActivity::class.java)
        Meal.putExtra("meal", time)

        startActivity(Meal)
    }

}