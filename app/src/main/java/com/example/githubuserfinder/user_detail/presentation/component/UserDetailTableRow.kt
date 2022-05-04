package com.example.githubuserfinder.user_detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.presentation.CustomTextStyle

@Composable
fun UserDetailTableRow(
    former: String,
    latter: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Text(
            former,
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
            style = CustomTextStyle.content,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            latter,
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
            style = CustomTextStyle.content,
        )
    }
}
