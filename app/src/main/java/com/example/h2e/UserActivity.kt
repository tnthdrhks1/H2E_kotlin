package com.example.h2e

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_meal.*
import kotlinx.android.synthetic.main.activity_check_meal.dish1
import kotlinx.android.synthetic.main.activity_check_meal.dish2
import kotlinx.android.synthetic.main.activity_check_meal.dish3
import kotlinx.android.synthetic.main.activity_check_meal.dish4
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*

class diamatchup (val yourchart : String? = null)

class UserActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    lateinit var type : String

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    lateinit var ResultSplitString : String

    lateinit var dishnumber : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        ButtonNextTTTT.setOnClickListener{
            startActivity(Intent(this, CheckSugarActivity::class.java))
        }
    }

    fun SpeechBefore1(view : View?){
        dishnumber = dish1
        type = "dish1"
        getSpeechInput()
    }

    fun SpeechBefore2(view : View?){
        dishnumber = dish2
        type = "dish2"
        getSpeechInput()
    }

    fun SpeechBefore3(view : View?){
        dishnumber = dish3
        type = "dish3"
        getSpeechInput()
    }

    fun SpeechBefore4(view : View?) {
        dishnumber = dish4
        type = "dish4"
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

                dishnumber.setText(ResultSplitString)
                gotoserver(type, ResultSplitString)
            }
        }
    }

    fun gotoserver(Dishnumber : String, resultSplitString: String){
        firestore?.collection("xcorps")?.document("sugar")?.update(Dishnumber, resultSplitString)
    }
}