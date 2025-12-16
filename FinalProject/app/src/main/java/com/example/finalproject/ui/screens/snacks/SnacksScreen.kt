package com.example.finalproject.ui.screens.snacks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.data.snacks.SnackEntity
import com.example.finalproject.viewmodel.AppViewModel
import java.util.Locale

@Composable
fun SnacksScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    // Observe snacks reactively
    val snacks = appViewModel.snacks.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.snacks_title),
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
            text = stringResource(R.string.snacks_total, appViewModel.snacksTotal()),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("checkout") },
            modifier = Modifier.fillMaxWidth(),
            enabled = appViewModel.snacksInCart().isNotEmpty()
        ) {
            Text(text = stringResource(R.string.continue_to_checkout))
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
            Text("$${snack.price}", style = MaterialTheme.typography.bodySmall)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onRemove) { Text("−", style = MaterialTheme.typography.headlineSmall) }
            Text(snack.amountInCart.toString(), modifier = Modifier.padding(horizontal = 8.dp), style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onAdd) { Text("+", style = MaterialTheme.typography.headlineSmall) }
        }
    }
}
