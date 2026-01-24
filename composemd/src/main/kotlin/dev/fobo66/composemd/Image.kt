package dev.fobo66.composemd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun MarkdownImage(imageDestination: String, imageTitle: String?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(imageDestination)
                    .build(),
            contentDescription = imageTitle
        )
    }
}
