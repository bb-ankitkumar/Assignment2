package com.example.assignmentt.ViewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentt.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users = userRepository.users
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        refreshUsers()
    }

    private fun refreshUsers() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.refreshUsers()
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            }
            isLoading.value = false
        }
    }
}
