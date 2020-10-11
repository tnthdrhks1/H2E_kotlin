package com.example.h2e

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.detailinreview.view.*

class DetailIngre (val dish : String? = null, val gok_data : String? = null, val uju_data : String? = null, val veg_data : String? = null,
                   val ujung_data : String? = null, val fat_data : String? = null, val milk_data : String? = null, val fruit_data : String? = null, val kcal : String? = null)

class DetailActivity : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()

    var whichtime : String = "null"

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra("whichtime")) {
            whichtime = intent.getStringExtra("whichtime")
        }

        recyclerView.adapter = RecyclerViewAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var searchOption = "dish"

        ResetBtn.setOnClickListener {
            searchWord.setText("")
            recyclerView.adapter = RecyclerViewAdapter(this)
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "이름" -> {
                        searchOption = "dish"
                    }
                }
            }
        }

        searchBtn.setOnClickListener {
            (recyclerView.adapter as RecyclerViewAdapter).search(searchWord.text.toString(), searchOption)
        }
    }

    inner class RecyclerViewAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var MealArray : ArrayList<DetailIngre> = arrayListOf()

        init {
            firestore?.collection(user + whichtime)?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                MealArray.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(DetailIngre::class.java)
                    MealArray.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.detailinreview, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            viewHolder.dishname.text = MealArray[position].dish
            viewHolder.gokdetailtext.text = MealArray[position].gok_data
            viewHolder.ujudetailtext.text = MealArray[position].uju_data
            viewHolder.ujungdetailtext.text = MealArray[position].ujung_data
            viewHolder.vegdetailtext.text = MealArray[position].veg_data
            viewHolder.fatdetailtext.text = MealArray[position].fat_data
            viewHolder.milkdetailtext.text = MealArray[position].milk_data
            viewHolder.fruitdetailtext.text = MealArray[position].fruit_data
            viewHolder.kcalDetailtext.text = MealArray[position].kcal
        }
        override fun getItemCount(): Int {
            return MealArray.size
        }

        fun search(searchWord : String, option : String) {
            firestore?.collection(user + whichtime)?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                MealArray.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString(option)!!.contains(searchWord)) {
                        var item = snapshot.toObject(DetailIngre::class.java)
                        MealArray.add(item!!)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}