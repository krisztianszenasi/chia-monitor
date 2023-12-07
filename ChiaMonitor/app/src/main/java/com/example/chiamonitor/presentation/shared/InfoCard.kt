package com.example.chiamonitor.presentation.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.R
import com.example.chiamonitor.presentation.dpToSp
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun InfoCard(
    @DrawableRes icon: Int,
    title: String,
    text: String,
    iconSize: Dp = 50.dp,
    modifier: Modifier = Modifier,
) {
    ChiaListElement(
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        },
        modifier = modifier.width(IntrinsicSize.Max),
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(
                text = title,
                fontSize = dpToSp(dp = iconSize / 2),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = text,
                fontSize = dpToSp(dp = iconSize / 3),
            )
        }
    }
}


@Composable
fun InfoCard(
    title: String,
    fontSize: TextUnit = 20.sp,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
            )
            Spacer(Modifier.width(8.dp))
            content()
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun InfoCardPreview() {
//    ChiaMonitorTheme {
//        InfoCard(
//            icon = R.drawable.baseline_public_light,
//            title = "netspace",
//            text = "30.08 EiB",
//            modifier = Modifier.padding(4.dp)
//        )
//    }
//}


@Preview
@Composable
fun InfoCardWithoutIconPreview() {
    ChiaMonitorTheme {
        InfoCard(
            title = "amount",
            modifier = Modifier.padding(4.dp)
        ) {
            Text("4.5 XCH")
        }
    }
    
}