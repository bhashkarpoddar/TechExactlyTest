package com.example.techexactlytest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.techexactlytest.data.dataSource.Resource
import com.example.techexactlytest.data.dataSource.repository.ApplicationRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val applicationRepository: ApplicationRepository): ViewModel() {

    init {

    }

    fun getApplications(lastId: Int = 0) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = applicationRepository.getApplications(lastId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    override fun onCleared() {


    }

}