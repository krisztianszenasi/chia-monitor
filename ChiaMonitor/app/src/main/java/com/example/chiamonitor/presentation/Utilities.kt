package com.example.chiamonitor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }


fun intToCommaSeparatedString(number: Int): String {
    return  "%,d".format(number)
}

fun getShortenedAddress(address: String): String {
    if (address.length > 10) {
        return "${address.substring(0, 6)}...${address.substring(address.length - 4, address.length)}"
    }
    return address
}

fun Float.asString(decimalPlaces: Int = 2): String {
    val finalDecPlaces = if (this.rem(1) == 0f) 0 else decimalPlaces
    return "%.${finalDecPlaces}f".format(this)
}