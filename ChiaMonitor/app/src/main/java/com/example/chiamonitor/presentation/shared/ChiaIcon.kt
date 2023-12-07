package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.presentation.dpToSp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun ChiaServiceIcon(
    modifier: Modifier = Modifier,
    size: Dp,
    service: String
) {
    val iconText = when(service) {
        "chia_full_node" -> "ND"
        "chia_farmer" -> "FM"
        "chia_harvester" -> "HW"
        "chia_wallet" -> "WL"
        else -> "?"
    }
    ChiaTextIcon(
        size = size,
        text = iconText,
        modifier = modifier,
    )
}


@Composable
fun ChiaTextIcon(
    modifier: Modifier = Modifier,
    size: Dp,
    text: String,
) {
    ChiaIcon(modifier = modifier, size = size) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = dpToSp(Dp(size.value / 3))
        )
    }
}

@Composable
fun ChiaIcon(
    modifier: Modifier = Modifier,
    size: Dp,
    content: @Composable() () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(size),
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            content()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChiaTextIconPreview() {
    ChiaMonitorTheme {
        ChiaTextIcon(size = 80.dp, text = "BK", modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun IconPreview() {
    ChiaMonitorTheme {
        ChiaIcon(size = 80.dp, modifier = Modifier.padding(8.dp)) {
            Text(text = "Hey")
        }
    }
}