package com.tejas.mlkitsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tejas.mlkitsample.MainActivity
import com.tejas.mlkitsample.R
import com.tejas.mlkitsample.databinding.FragmentHistoryBinding
import com.tejas.mlkitsample.model.ScannedData
import com.tejas.mlkitsample.ui.adapter.HistoryAdapter
import com.tejas.mlkitsample.viewmodel.MainViewModel
import java.sql.Timestamp

class HistoryFragment : Fragment(), HistoryAdapter.ItemClickListener {

    private var binding: FragmentHistoryBinding? = null
    private val fragmentBinding get() = binding!!
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (requireActivity() as MainActivity).mainViewModel
        mainViewModel.getHistory().observe(viewLifecycleOwner) {
            if (it.isEmpty())
                fragmentBinding.tvNoHistory.isVisible = true
            else {
                fragmentBinding.tvNoHistory.isVisible = false
                val adapter = HistoryAdapter()
                adapter.itemClickListener = this
                fragmentBinding.rvHistory.adapter = adapter
                adapter.setData(it)
            }
        }
    }

    override fun onClick(barcode: ScannedData) {
        mainViewModel.saveBarcodeData(barcode)
        findNavController().navigate(R.id.navigation_show_barcode_details)
    }
}