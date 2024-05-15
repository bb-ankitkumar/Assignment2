package com.example.assignmentt.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignmentt.Model.User
import com.example.assignmentt.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val users = MutableLiveData<List<User>?>()
    val errorMessage = MutableLiveData<String>()

    init {
        loadUsers()
    }

    //using enqueue and callback approach to fetch the response data from the server

//    private fun loadUsers() {
//        UserRepository.getUsers().enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                val originalUsers = response.body()?.data
//                val allUsers = originalUsers?.let { ArrayList(it) }
//                allUsers?.addAll(originalUsers)
//                users.value = allUsers
//            }
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                // Handle failure
//                errorMessage.value = t.message
//
//            }
//        })
//    }


    //using coroutine approach to fetch the response data from the server
    private fun loadUsers() {
        viewModelScope.launch {
            try {
                val response = UserRepository.getUsers()
                if (response.isSuccessful) {
                    val originalUsers = response.body()?.data
                    val allUsers = originalUsers?.let { ArrayList(it) }
                    allUsers?.addAll(originalUsers)
                    users.value = allUsers
                } else {
                    errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            }
        }
    }

}
