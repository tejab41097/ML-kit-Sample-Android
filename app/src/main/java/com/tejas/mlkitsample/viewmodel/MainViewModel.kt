package com.tejas.mlkitsample.viewmodel

import androidx.lifecycle.ViewModel
import com.tejas.mlkitsample.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getHistory() = mainRepository.getListOfStoredData()
}