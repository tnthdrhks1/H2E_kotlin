package com.example.h2e

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

data class ProfileActivity(var name : String? = null, var phoneNumber : String? = null) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}