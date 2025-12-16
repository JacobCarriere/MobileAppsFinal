package com.example.finalproject.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.viewmodel.AppViewModel
import java.util.Locale

@Composable
fun CheckoutScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val selectedMovie = appViewModel.selectedMovie.collectAsState().value
    val selectedDate = appViewModel.selectedDate.collectAsState().value

    // Observe snacks as state so Compose recomposes automatically
    val snacksInCart = appViewModel.snacks
        .collectAsState()
        .value
        .filter { it.amountInCart > 0 }

    val totalPrice = snacksInCart.sumOf { it.price * it.amountInCart }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.checkout_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedMovie != null && selectedDate != null) {
            Text(
                text = stringResource(
                    R.string.checkout_movie,
                    selectedMovie.title,
                    selectedMovie.year
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.checkout_date, selectedDate),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (snacksInCart.isNotEmpty()) {
            Text(
                text = stringResource(R.string.checkout_snacks),
                style = MaterialTheme.typography.titleMedium
            )
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
                        Text(
                            text = "${snack.name} x${snack.amountInCart}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$${String.format(Locale.US, "%.2f", snack.price * snack.amountInCart)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.checkout_total, totalPrice),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Clear cart after confirming purchase
                appViewModel.clearCart()
                navController.navigate("thank_you")
            },
            enabled = snacksInCart.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_confirm_purchase))
        }
    }
}
