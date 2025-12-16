package com.example.finalproject.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.data.snacks.SnackEntity
import com.example.finalproject.viewmodel.AppViewModel
import java.util.Locale

@Composable
fun CheckoutScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val selectedMovie = appViewModel.selectedMovie.collectAsState().value
    val selectedDate = appViewModel.selectedDate.collectAsState().value
    val snacksInCart = appViewModel.snacksInCart()
    val totalPrice = appViewModel.snacksTotal()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Checkout", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedMovie != null && selectedDate != null) {
            Text(
                "Movie: ${selectedMovie.title} (${selectedMovie.year})",
                style = MaterialTheme.typography.bodyLarge
            )
            Text("Date: $selectedDate", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (snacksInCart.isNotEmpty()) {
            Text("Snacks:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(snacksInCart, key = { it.id }) { snack ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${snack.name} x${snack.amountInCart}", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            "$${String.format(Locale.US, "%.2f", snack.price * snack.amountInCart)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Total: $${String.format(Locale.US, "%.2f", totalPrice)}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("thank_you") },
            enabled = snacksInCart.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Purchase")
        }
    }
}
