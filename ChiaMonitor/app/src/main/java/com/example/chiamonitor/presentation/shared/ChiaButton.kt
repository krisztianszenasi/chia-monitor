package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun ChiaButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
    ) {
        // button text
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ChiaButtonPreview() {
    ChiaMonitorTheme {
        ChiaButton(text = "save", onClick = {})
    }
}