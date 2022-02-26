package com.example.techexactlytest.data.dataSource.repository

import com.example.techexactlytest.data.dataSource.ApiService

class ApplicationRepository(private val apiService: ApiService) {

    suspend fun getApplications(kid_id: Int) = apiService.getApplications(kid_id)

}