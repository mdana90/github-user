package com.dana.github.composables

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale =  ContentScale.Crop,
    @DrawableRes placeholderDrawableRes: Int = R.drawable.ic_github_gray,
    @DrawableRes errorDrawableRes: Int = R.drawable.ic_github_gray,
    imageUrl: String,
) {
    CoilImage(
        modifier = modifier,
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = contentScale,
        ),
        component = rememberImageComponent {
            +PlaceholderPlugin.Loading(painterResource(id = placeholderDrawableRes))
            +PlaceholderPlugin.Failure(painterResource(id = errorDrawableRes))
        },
    )
}