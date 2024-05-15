package com.example.assignmentt.repository


import com.example.assignmentt.Model.ApiInterface
import com.example.assignmentt.Model.ApiResponse
import com.example.assignmentt.Model.UserDao
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(private val userDao: UserDao) {
    val users = userDao.getUsers()
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

    suspend fun refreshUsers() {
        val response = apiInterface.getUsers()
        if (response.isSuccessful) {
            response.body()?.data?.let { userDao.insertUsers(it) }
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
