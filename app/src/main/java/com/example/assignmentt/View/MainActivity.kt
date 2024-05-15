package com.example.assignmentt.View

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentt.R
import com.example.assignmentt.Adapter.UserAdapter
import com.example.assignmentt.Model.UserDatabase
import com.example.assignmentt.ViewModel.UserViewModel
import com.example.assignmentt.ViewModel.UserViewModelFactory
import com.example.assignmentt.repository.UserRepository

//class MainActivity : AppCompatActivity() {
//    private val viewModel: UserViewModel by viewModels()
//    private lateinit var progressBar: ProgressBar
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//      progressBar = findViewById(R.id.progressBar)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//
//        viewModel.users.observe(this, Observer { users ->
//            recyclerView.adapter = users?.let { UserAdapter(it) }
//        })
//
//        //observing error message and displaying it as the toast message
//        viewModel.errorMessage.observe(this, Observer { errorMessage ->
//            errorMessage?.let {
//                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//            }
//        })
//
//        //observing the loading status and displaying the progress dialog
//        viewModel.isLoading.observe(this, Observer { isLoading ->
//            if (isLoading) {
//                progressBar.visibility = ProgressBar.VISIBLE
//            } else {
//                 progressBar.visibility = ProgressBar.GONE
//            }
//        })
//    }
//}

class MainActivity : AppCompatActivity() {
    private val userRepository by lazy { UserRepository(UserDatabase.getDatabase(this).userDao()) }
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(userRepository) }
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        userViewModel.users.observe(this, Observer { users ->
            // Update your UI here with the users
            recyclerView.adapter = users?.let { UserAdapter(it) }
        })

        userViewModel.errorMessage.observe(this, Observer { errorMessage ->
            // Handle the error message here
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        userViewModel.isLoading.observe(this, Observer { isLoading ->
            // Handle the loading state here
            progressBar.visibility = if (isLoading) ProgressBar.VISIBLE else ProgressBar.GONE
        })
    }
}