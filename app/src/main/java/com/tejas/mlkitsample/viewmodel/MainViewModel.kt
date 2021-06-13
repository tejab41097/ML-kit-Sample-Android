package com.tejas.mlkitsample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejas.mlkitsample.model.ScannedData
import com.tejas.mlkitsample.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val mutGlobalErrorObserver = MutableLiveData<String?>()
    val globalErrorObserver: LiveData<String?> get() = mutGlobalErrorObserver

    init {
        mainRepository.globalErrorObserver.observeForever {
            mutGlobalErrorObserver.postValue(it)
        }
    }

    private val latestScannedLiveData = MutableLiveData<ScannedData>()

    private fun setScannedData(scannedData: ScannedData) {
        latestScannedLiveData.postValue(scannedData)
    }

    fun getScannedData() = latestScannedLiveData as LiveData<ScannedData>

    fun getHistory() = mainRepository.getListOfStoredData()

    fun saveBarcodeData(scannedData: ScannedData) {
        viewModelScope.launch {
            mainRepository.insertData(scannedData)
        }
        setScannedData(scannedData)
    }

    fun syncData() = mainRepository.syncData()

}