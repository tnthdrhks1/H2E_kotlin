package com.example.h2e

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_day.*
import java.time.LocalDate
import java.time.LocalDateTime

class DayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        val dateAndtime: LocalDateTime = LocalDateTime.now()
        val onlyDate: LocalDate = LocalDate.now()

        dish_weight1.setText(onlyDate.toString())
        dish_weight2.setText()
    }
}