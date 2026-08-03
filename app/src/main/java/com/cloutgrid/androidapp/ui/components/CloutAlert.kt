package com.cloutgrid.androidapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CloutAlert(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    body: String,
    action: String,
    icon: ImageVector? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(body) },
        confirmButton = {
            Button(onConfirm) {
                Text(action)
            }
        },
        dismissButton = {
            Button(onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = Color.White,
        icon = {
            Icon(
                icon ?: Icons.Rounded.Warning,
                contentDescription = "Alert Icon"
            )
        },
        iconContentColor = Color.Black
    )
}