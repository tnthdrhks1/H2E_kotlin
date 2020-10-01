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
import kotlinx.android.synthetic.main.custom_recycle.*
import kotlinx.android.synthetic.main.custom_recycle.view.*

class RecyclerActivity : AppCompatActivity() {

    var DishName : String? = null
    lateinit var DishNameActivity : Intent

    val firestore = FirebaseFirestore.getInstance()

    lateinit var Meal_time : String
    lateinit var nameDishNumber : String

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var user = mAuth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        if (intent.hasExtra("meal_time")) {
            Meal_time = intent.getStringExtra("meal_time")
        }

        if (intent.hasExtra("nameDishNumber")) {
            nameDishNumber = intent.getStringExtra("nameDishNumber")
        }

        recyclerView.adapter = RecyclerViewAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var searchOption = "Aname"

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
                        searchOption = "Aname"
                    }
                }
            }
        }

        searchBtn.setOnClickListener {
            (recyclerView.adapter as RecyclerViewAdapter).search(searchWord.text.toString(), searchOption)
        }
    }



    inner class RecyclerViewAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var MealArray : ArrayList<MyMeal> = arrayListOf()

        init {
            firestore?.collection("meal")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                MealArray.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(MyMeal::class.java)
                    MealArray.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_recycle, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            viewHolder.name.text = MealArray[position].Aname

            viewHolder.setOnClickListener {
                Toast.makeText(context, viewHolder.name.text , Toast.LENGTH_SHORT).show()

                DishNameActivity = Intent(context, CheckMealActivity::class.java)
                DishName = viewHolder.name.text.toString()

                DishNameActivity.putExtra("DishName", DishName)
                DishNameActivity.putExtra("meal", Meal_time)
                DishNameActivity.putExtra("nameDishNumber_out", nameDishNumber)

                startActivity(DishNameActivity)
            }

        }
        override fun getItemCount(): Int {
            return MealArray.size
        }

        fun search(searchWord : String, option : String) {
            firestore?.collection("meal")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                MealArray.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString(option)!!.contains(searchWord)) {
                        var item = snapshot.toObject(MyMeal::class.java)
                        MealArray.add(item!!)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}