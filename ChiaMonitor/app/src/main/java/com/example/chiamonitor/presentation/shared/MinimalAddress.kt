package com.example.chiamonitor.presentation.shared

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.presentation.getShortenedAddress
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun MinimalAddress(
    address: String,
    fontSize: TextUnit = 12.sp,
    modifier: Modifier = Modifier
) {
    Text(
        text = getShortenedAddress(address),
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
        fontSize = fontSize,
    )
}

@Preview(showBackground = true)
@Composable
fun MinimalAddressPreview() {
    ChiaMonitorTheme {
        MinimalAddress(address = "xch183rqja3f2ak4f72w9enc88v0788vjp5ns3c2n2sctxpcnewzh2yqtcu2rs")
    }
}