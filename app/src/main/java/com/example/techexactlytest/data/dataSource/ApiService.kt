package com.example.techexactlytest.data.dataSource

import com.example.techexactlytest.data.model.AppliactionsResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("apps/list")
    suspend fun getApplications(@Query("kid_id") kid_id: Int): AppliactionsResponse

}