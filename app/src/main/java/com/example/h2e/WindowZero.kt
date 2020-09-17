package com.example.h2e

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_window_zero.*

class WindowZero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_zero)

        button1.setOnClickListener {
            setFrag(0)
        }

        button2.setOnClickListener {
            setFrag(1)
        }
        button3.setOnClickListener {
            setFrag(2)
        }
    }

    private fun setFrag(fragnum: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragnum)
        {
            0 -> {
                ft.replace(R.id.main_frame, Fragment1()).commit()
            }
            1-> {
                ft.replace(R.id.main_frame, Fragment2()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, Fragment3()).commit()
            }
        }
    }
}