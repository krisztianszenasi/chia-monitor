package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun LargeInfoTextScreen(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun LargeInfoTextScreenPreview() {
    ChiaMonitorTheme {
        LargeInfoTextScreen(
            title = "Can not retrieve blockchain state.\nThis is normal if it still booting up.",
            modifier = Modifier.fillMaxSize()
        )
    }
}