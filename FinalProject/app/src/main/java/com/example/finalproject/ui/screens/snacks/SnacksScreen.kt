package com.example.finalproject.ui.screens.snacks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.data.snacks.SnackEntity
import com.example.finalproject.viewmodel.AppViewModel
import java.util.Locale

@Composable
fun SnacksScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    // Populate snacks ONCE safely
    LaunchedEffect(Unit) {
        appViewModel.ensureSnacksExist()
    }

    val snacks by appViewModel.snacks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Snacks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(snacks, key = { it.id }) { snack ->
                SnackItem(
                    snack = snack,
                    onAdd = { appViewModel.increaseSnack(snack) },
                    onRemove = { appViewModel.decreaseSnack(snack) }
                )
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $${String.format(Locale.US, "%.2f", appViewModel.snacksTotal())}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("checkout") },
            modifier = Modifier.fillMaxWidth(),
            enabled = appViewModel.snacksInCart().isNotEmpty()
        ) {
            Text("Continue to Checkout")
        }
    }
}

@Composable
private fun SnackItem(
    snack: SnackEntity,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(snack.name, style = MaterialTheme.typography.bodyLarge)
            Text(snack.description, style = MaterialTheme.typography.bodyMedium)
            Text(
                "$${String.format(Locale.US, "%.2f", snack.price)}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onRemove) {
                Text("−", style = MaterialTheme.typography.headlineSmall)
            }
            Text(
                snack.amountInCart.toString(),
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = onAdd) {
                Text("+", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
