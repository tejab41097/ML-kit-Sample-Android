package com.tejas.mlkitsample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tejas.mlkitsample.R
import com.tejas.mlkitsample.databinding.ItemHistoryBinding
import com.tejas.mlkitsample.model.ScannedData

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var list = mutableListOf<ScannedData>()
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.itemBinding
        binding.tvText1.isVisible = !item.text1.isNullOrEmpty()
        binding.tvText1Title.isVisible = !item.text1.isNullOrEmpty()
        binding.tvText2.isVisible = !item.text2.isNullOrEmpty()
        binding.tvText2Title.isVisible = !item.text2.isNullOrEmpty()
        val barcodes = binding.root.context.resources.getStringArray(R.array.barcodes)
        binding.tvText1Title.text = getText1(item.type, barcodes, binding.root.context)
        binding.tvText2Title.text = getText2(item.type, barcodes, binding.root.context)
        binding.tvText1.text = item.text1
        binding.tvText2.text = item.text2
        binding.tvType.text = binding.root.context.getString(R.string.type, item.type)
        binding.tvIndex.text = (position + 1).toString()
        binding.root.setOnClickListener {
            itemClickListener.onClick(item)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val itemBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    fun setData(dataList: MutableList<ScannedData>) {
        list = dataList
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(barcode: ScannedData)
    }

    private fun getText1(type: String, barcodes: Array<String>, context: Context): String? {
        return when (type) {
            barcodes[1] -> context.getString(R.string.name)
            barcodes[2] -> context.getString(R.string.email)
            barcodes[4] -> context.getString(R.string.name_number)
            barcodes[6] -> context.getString(R.string.number)
            barcodes[7] -> context.getString(R.string.text)
            barcodes[8] -> context.getString(R.string.title)
            barcodes[9] -> context.getString(R.string.ssid)
            barcodes[10] -> context.getString(R.string.latitude)
            else -> null
        }
    }

    private fun getText2(type: String, barcodes: Array<String>, context: Context): String? {
        return when (type) {
            barcodes[1] -> context.getString(R.string.number)
            barcodes[2] -> context.getString(R.string.full_mail)
            barcodes[4] -> context.getString(R.string.type2)
            barcodes[6] -> context.getString(R.string.message)
            barcodes[7] -> null
            barcodes[8] -> context.getString(R.string.url)
            barcodes[9] -> context.getString(R.string.password)
            barcodes[10] -> context.getString(R.string.longitude)
            else -> null
        }
    }
}