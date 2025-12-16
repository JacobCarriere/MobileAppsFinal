package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.data.snacks.SnackDatabase
import com.example.finalproject.data.snacks.SnackRepository
import com.example.finalproject.navigation.NavGraph
import com.example.finalproject.ui.components.AppTopBar
import com.example.finalproject.ui.components.EmailFab
import com.example.finalproject.viewmodel.AppViewModel
import com.example.finalproject.viewmodel.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Database & Repository ---
        val database = SnackDatabase.getDatabase(applicationContext)
        val repository = SnackRepository(database.snackDao())

        setContent {
            val navController = rememberNavController()

            val appViewModel: AppViewModel = viewModel(
                factory = AppViewModelFactory(repository)
            )

            Scaffold(
                topBar = { AppTopBar(navController) },
                floatingActionButton = { EmailFab() }
            ) { paddingValues ->
                NavGraph(
                    navController = navController,
                    appViewModel = appViewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
