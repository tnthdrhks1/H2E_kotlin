package com.example.h2e

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_recycle.view.*

class Data (val Aname : String)

class CustomViewHolder(v : View) : RecyclerView.ViewHolder(v){
    //val name = v.Recycle_View
}

class CustomAdapter(val DataList: ArrayList<Data>, val context : Context) : RecyclerView.Adapter<CustomViewHolder> () {

    var telephoneBook : ArrayList<Data> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val CellForRow = LayoutInflater.from(context).inflate(R.layout.custom_recycle, parent, false)
        return CustomViewHolder(CellForRow)
    }

    override fun getItemCount() = DataList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.name.text = DataList[position].Aname
    }
}