package com.example.finalproject.ui.screens.movies

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val context = LocalContext.current
    val selectedMovie by appViewModel.selectedMovie.collectAsState()
    if (selectedMovie == null) {
        Text(
            text = context.getString(R.string.no_movie_selected),
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    // ---------- DATE STATE ----------
    val todayMillis = remember { System.currentTimeMillis() }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis
    )

    var selectedDate by remember {
        mutableStateOf(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(todayMillis))
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }

    // ---------- UI ----------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = context.getString(R.string.screen_movie_detail),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(selectedMovie!!.posterUrl),
            contentDescription = selectedMovie!!.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${selectedMovie!!.title} (${selectedMovie!!.year})",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- DATE FIELD ----------
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text(context.getString(R.string.select_date)) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ---------- CONFIRM ----------
        Button(
            onClick = {
                appViewModel.selectMovie(selectedMovie!!, selectedDate)
                Toast.makeText(
                    context,
                    context.getString(R.string.movie_date_saved),
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate("snacks")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.btn_confirm_movie))
        }
    }

    // ---------- MATERIAL 3 DATE PICKER ----------
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                            ).format(Date(millis))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = true
            )
        }
    }
}
