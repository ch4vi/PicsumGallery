package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

private const val RATIO_16_9 = 16f / 9f

@Composable
fun PictureItem(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    item: Picture
) {
    Card(
        modifier = modifier
            .aspectRatio(ratio = RATIO_16_9),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.downloadUrl)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                placeholder = painterResource(R.drawable.picture_placeholder),
                contentDescription = stringResource(R.string.description_picture),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = item.author,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 500)
@Composable
fun PictureItemPreview() {
    PicsumGalleryTheme {
        PictureItem(
            item = Picture(
                id = 1,
                author = "foo",
                width = 1,
                height = 1,
                url = "",
                downloadUrl = ""
            )
        )
    }
}
