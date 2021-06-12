package com.tejas.mlkitsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tejas.mlkitsample.MainActivity
import com.tejas.mlkitsample.databinding.FragmentHistoryBinding
import com.tejas.mlkitsample.ui.adapter.HistoryAdapter
import com.tejas.mlkitsample.viewmodel.MainViewModel

class HistoryFragment : Fragment() {

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
                fragmentBinding.rvHistory.adapter = adapter
                adapter.setData(it)
            }
        }
    }
}