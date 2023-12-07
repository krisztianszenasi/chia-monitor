package com.example.chiamonitor.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.R
import com.example.chiamonitor.presentation.shared.ChiaButton
import com.example.chiamonitor.presentation.shared.ChiaInputField
import com.example.chiamonitor.presentation.shared.ChiaPasswordInputField
import com.example.chiamonitor.settings.ConnectionSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConnectionSettingsScreen(
    settings: ConnectionSettings,
    onSave: (ConnectionSettings) -> Unit,
) {
    var url by remember { mutableStateOf(settings.host) }
    var apiKey by remember { mutableStateOf(settings.apiKey) }
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                ChiaInputField(
                    label = stringResource(R.string.host),
                    value = url,
                    onValueChange = { url = it}
                )
                ChiaPasswordInputField(
                    label = stringResource(R.string.api_key),
                    value = apiKey,
                    onValueChange = { apiKey = it}
                )
            }
        }
        Spacer(modifier = Modifier.height(120.dp))
        ChiaButton(text = stringResource(R.string.save)) {
            onSave(ConnectionSettings(host = url, apiKey = apiKey))
            keyboardController?.hide()
        }
    }
}