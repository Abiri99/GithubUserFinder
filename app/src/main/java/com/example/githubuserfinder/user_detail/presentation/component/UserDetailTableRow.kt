package com.example.githubuserfinder.user_detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailTableRow(
    former: String,
    latter: String,
) {

    val surfaceColor = MaterialTheme.colors.surface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(surfaceColor)
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Text(
            former,
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            latter,
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
        )
    }
}
