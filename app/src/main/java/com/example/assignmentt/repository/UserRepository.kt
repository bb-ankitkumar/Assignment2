package com.example.assignmentt.repository

import com.example.assignmentt.Model.ApiInterface
import com.example.assignmentt.Model.ApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepository {
    private val apiInterface: ApiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gorest.co.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getUsers(): Response<ApiResponse> {
        return apiInterface.getUsers()
    }
}
