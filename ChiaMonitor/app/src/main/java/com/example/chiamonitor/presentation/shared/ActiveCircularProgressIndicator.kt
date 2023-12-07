package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun ActiveCircularProgressIndicator(
    progress: Float,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    progressColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = strokeWidth,
            color = backgroundColor,
            modifier = Modifier.fillMaxSize()
        )
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = strokeWidth,
            color = progressColor,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview
@Composable
fun ActiveCircularProgressIndicatorPreview() {
    ChiaMonitorTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ){
            ActiveCircularProgressIndicator(
                progress = 0.3f,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}