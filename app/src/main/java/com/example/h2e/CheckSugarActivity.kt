package com.example.h2e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_sugar.*
import kotlinx.android.synthetic.main.activity_ergometer.*
import kotlinx.android.synthetic.main.activity_ergometer.ButtonNexttwo
import kotlinx.android.synthetic.main.activity_ergometer.lineChart
import kotlinx.android.synthetic.main.activity_ergometer.startButton
import kotlinx.android.synthetic.main.activity_profile.*

class CheckSugarActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()
    lateinit var arrlist: String
    var name00 = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_sugar)

        startButton0000.setOnClickListener {
            val thread = ThreadClass()
            thread.start()
        }
    }

    inner class ThreadClass : Thread() {
        override fun run() {
            var entries: ArrayList<Entry> = ArrayList()
            entries.add(Entry(0F, 60F))

            var dataset: LineDataSet = LineDataSet(entries, "input")
            var data0: LineData = LineData(dataset)
            yourchart.data = data0

            runOnUiThread {
                yourchart.animateXY(1, 1)
            }

            firestore.collection("xcorps").get().addOnSuccessListener { task ->
                for (document in task) {
                    if (document.id == "sugar") {
                        var listsu = document.data.size
                        for (i in 1..listsu) {
                            arrlist = document.data["dish$i"].toString()
                            name00.add(arrlist)
                        }
                    }

                    for (i in 0 until name00.size) {

                        SystemClock.sleep(10)
                        data0.addEntry(Entry(i.toFloat(), name00[i].toFloat()), 0)
                        data0.notifyDataChanged()


                        yourchart.notifyDataSetChanged()
                        yourchart.invalidate()

                    }
                }
            }
        }
    }
}
