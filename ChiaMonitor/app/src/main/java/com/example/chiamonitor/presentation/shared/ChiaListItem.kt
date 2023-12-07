package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun ChiaListElement(
    modifier: Modifier = Modifier,
    icon: @Composable() () -> Unit,
    content: @Composable() () -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        }
    }
}

@Composable
fun TestIcon(modifier: Modifier = Modifier) {
    ChiaTextIcon(size = 80.dp, text = "Test", modifier = modifier)
}


@Preview(showBackground = true)
@Composable
fun ChiaListElementPreview() {
    ChiaMonitorTheme {
        ChiaListElement(
            icon = { TestIcon() },
            content = { Text("Test content") },
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChiaListElementPreviewShort() {
    ChiaMonitorTheme {
        ChiaListElement(
            icon = { TestIcon() },
            content = { Text("Test content") },
            modifier = Modifier
                .padding(8.dp)
                .width(200.dp)
        )
    }
}