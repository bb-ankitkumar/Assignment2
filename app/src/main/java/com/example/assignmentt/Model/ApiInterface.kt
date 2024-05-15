package com.example.assignmentt.Model

import retrofit2.http.GET
import retrofit2.Response

interface ApiInterface {
    @GET("public/v1/users")
   suspend fun getUsers(): Response<ApiResponse>
}
