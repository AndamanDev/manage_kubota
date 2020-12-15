package com.therdsak.kubota_manage_android.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.therdsak.kubota_manage_android.R


class ItemCarAdapter  (
) : RecyclerView.Adapter<ItemCarAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(p1 )
    }

    override fun getItemCount(): Int {
        return 3
    }


    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(pos : Int ) {
            itemView.apply {




            }
        }



    }




}