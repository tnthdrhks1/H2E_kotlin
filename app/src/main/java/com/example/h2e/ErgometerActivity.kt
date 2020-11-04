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
import kotlinx.android.synthetic.main.activity_ergometer.*
import kotlinx.android.synthetic.main.activity_profile.*

class ErgometerActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()
    lateinit var arrlist: String
    var name00 = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ergometer)

        ButtonNexttwo.setOnClickListener{
            startActivity(Intent(this, UserActivity::class.java))
        }

        startButton.setOnClickListener {
            val thread = ThreadClass()
            val td = ThreadClass2()

            td.start()
            thread.start()
        }
    }

    inner class ThreadClass : Thread() {
        override fun run() {
            var entries: ArrayList<Entry> = ArrayList()
            entries.add(Entry(0F, 60F))

            var dataset: LineDataSet = LineDataSet(entries, "input")
            var data0: LineData = LineData(dataset)
            lineChart.data = data0

            runOnUiThread {
                lineChart.animateXY(1, 1)
            }
            firestore.collection("xcorps").get().addOnSuccessListener { task ->
                for (document in task) {
                    if (document.id == "hearth") {
                        var listsu = document.data.size
                        for (i in 0..listsu - 1) {
                            arrlist = document.data["$i"].toString()
                            name00.add(arrlist)
                        }
                    }
                }

                for (i in 0 until name00.size) {

                    SystemClock.sleep(10)
                    data0.addEntry(Entry(i.toFloat(), name00[i].toFloat()), 0)
                    data0.notifyDataChanged()


                    lineChart.notifyDataSetChanged()
                    lineChart.invalidate()

                }
            }
        }
    }

    inner class ThreadClass2 : Thread() {
        override fun run() {

            var entries2: ArrayList<Entry> = ArrayList()
            entries2.add(Entry(0F, 200F))

            var dataset2: LineDataSet = LineDataSet(entries2, "input")

            var data1: LineData = LineData(dataset2)
            lineChart2.data = data1

            runOnUiThread {
                lineChart2.animateXY(1, 1)
            }

            firestore.collection("xcorps").get().addOnSuccessListener { task ->
                for (document in task) {
                    if (document.id == "speed") {
                        var listsu = document.data.size
                        for (i in 0..listsu - 1) {
                            arrlist = document.data["$i"].toString()
                            name00.add(arrlist)
                        }
                    }
                }

                for (i in 0 until name00.size) {

                    SystemClock.sleep(10)
                    data1.addEntry(Entry(i.toFloat(), name00[i].toFloat()), 0)
                    data1.notifyDataChanged()


                    lineChart2.notifyDataSetChanged()
                    lineChart2.invalidate()

                }
            }
        }
    }
}
