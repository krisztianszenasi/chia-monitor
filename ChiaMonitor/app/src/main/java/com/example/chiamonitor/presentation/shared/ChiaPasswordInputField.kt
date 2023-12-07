package com.example.chiamonitor.presentation.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun ChiaPasswordInputField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val image = if (passwordVisible)  Icons.Filled.Visibility  else Icons.Filled.VisibilityOff

    ChiaInputField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = { IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(imageVector = image, contentDescription = null)
        } }
    )
}

@Preview(showBackground = true)
@Composable
fun ChiaPasswordInputFieldPreview() {
    ChiaMonitorTheme {
        ChiaPasswordInputField(
            label = "api key",
            value = "",
            onValueChange = {},
        )
    }
}