package com.example.chiamonitor.presentation.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChiaInputField(
    label: String,
    value: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            singleLine = true,
            onValueChange = { onValueChange(it) },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChiaInputFieldPreview() {
    ChiaMonitorTheme {
        ChiaInputField(label = "host", value = "", onValueChange = {})
    }
}