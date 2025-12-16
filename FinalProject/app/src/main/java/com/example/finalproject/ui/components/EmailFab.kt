package com.example.finalproject.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.finalproject.R

@Composable
fun EmailFab() {
    val context = LocalContext.current
    val email = stringResource(R.string.email_address)

    FloatingActionButton(
        onClick = {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:$email")
            )
            context.startActivity(intent)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = stringResource(R.string.send_email)
        )
    }
}
