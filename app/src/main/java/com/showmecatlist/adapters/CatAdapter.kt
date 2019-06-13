package com.showmecatlist.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.showmecatlist.R
import com.showmecatlist.dataclasses.Pet
import com.showmecatlist.utils.TYPE_CAT


class CatAdapter(private val dataSet: List<Pet>) : RecyclerView.Adapter<CatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        // create a new view
        val viewGroup = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_view, parent, false) as ViewGroup

        return MyViewHolder(viewGroup as View)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {

        if (dataSet[p1].type == TYPE_CAT) {
            holder.textView.setPadding(60, 10, 10, 10)
        } else {
            holder.textView.setPadding(10, 20, 10, 20)
        }

        holder.textView.text = dataSet[p1].name
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView = view.findViewById(R.id.cat_name) as TextView

    }

}