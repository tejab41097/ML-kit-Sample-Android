package com.tejas.mlkitsample.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.tejas.mlkitsample.MainActivity
import com.tejas.mlkitsample.databinding.FragmentShowBarcodeDataBinding


class ShowBarcodeDataFragment : DialogFragment() {

    private var binding: FragmentShowBarcodeDataBinding? = null
    private val fragmentBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowBarcodeDataBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding.root.setOnClickListener {
            findNavController().popBackStack()
        }
        val mainViewModel = (requireActivity() as MainActivity).mainViewModel
        mainViewModel.getScannedData().observe(viewLifecycleOwner) { item ->
            fragmentBinding.tvData.text = item.data
            fragmentBinding.tvType.text = item.type.toString()
            fragmentBinding.tvIndex.text = item.id.toString()
        }
    }


}