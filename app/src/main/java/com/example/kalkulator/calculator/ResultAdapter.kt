package com.example.kalkulator.calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulator.R

class ResultAdapter(private var dataList: List<String>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    fun submitList(list: List<String>) {
        dataList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dialog_with_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewItem.text = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}