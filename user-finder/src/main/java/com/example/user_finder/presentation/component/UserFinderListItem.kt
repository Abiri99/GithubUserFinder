package com.example.user_finder.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_android.presentation.component.NetworkImage
import com.example.core_android.presentation.component.TouchableScale
import com.example.user_finder.data.model.GithubSearchItemModel

@Composable
internal fun UserFinderListItem(
    model: GithubSearchItemModel,
    onItemClicked: (String) -> Unit,
) {

    val onClicked: () -> Unit = {
        onItemClicked(model.login)
    }

    TouchableScale(
        onClick = onClicked,
        modifier = Modifier
            .requiredHeight(86.dp)
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = 0.dp,
            shape = RoundedCornerShape(6.dp),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                NetworkImage(
                    url = model.avatarUrl,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.requiredSize(48.dp),
                    imageModifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = model.login,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}