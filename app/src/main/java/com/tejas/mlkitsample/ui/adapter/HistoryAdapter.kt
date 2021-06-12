package com.tejas.mlkitsample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tejas.mlkitsample.databinding.ItemHistoryBinding
import com.tejas.mlkitsample.model.ScannedData

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var list = mutableListOf<ScannedData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    fun setData(dataList: MutableList<ScannedData>) {
        list = dataList
        notifyDataSetChanged()
    }
}