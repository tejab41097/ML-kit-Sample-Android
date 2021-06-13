package com.tejas.mlkitsample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tejas.mlkitsample.api.MainApiService
import com.tejas.mlkitsample.database.Database
import com.tejas.mlkitsample.model.ScannedData
import com.tejas.mlkitsample.model.UploadScannedDataModel
import com.tejas.mlkitsample.util.ApiResponse
import com.tejas.mlkitsample.util.Coroutine
import com.tejas.mlkitsample.util.ErrorBody
import com.tejas.mlkitsample.util.RequestHandler
import javax.inject.Inject

class MainRepository @Inject constructor(
    private var mainApiService: MainApiService,
    private var database: Database
) : RequestHandler() {

    private val mutGlobalErrorObserver = MutableLiveData<String?>()
    val globalErrorObserver: LiveData<String?> get() = mutGlobalErrorObserver

    suspend fun insertData(data: ScannedData) = database.getBaseDao().insertScannedData(data)

    fun getListOfStoredData() = database.getBaseDao().getAllData()

    fun syncData(): LiveData<ApiResponse> {
        val liveData = MutableLiveData<ApiResponse>()
        Coroutine.ioThenIO({
            database.getBaseDao().getDataToSync()
        }, {
            val list = it
            if (!list.isNullOrEmpty())
                Coroutine.ioThenIO({
                    apiRequest {
                        mainApiService.syncData(UploadScannedDataModel(list))
                    }
                }, { it2 ->
                    it2 as ApiResponse
                    liveData.postValue(it2)
                    if (it2.successBody != null)
                        database.getBaseDao().updateDataSync()
                    else
                        (it2.errorBody as ErrorBody).message?.let { error ->
                            mutGlobalErrorObserver.postValue(error)
                        }
                })
        })
        return liveData
    }
}