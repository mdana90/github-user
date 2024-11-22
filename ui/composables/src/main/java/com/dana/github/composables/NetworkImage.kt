package com.dana.github.composables

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

/**
 * Displays an image from a network URL with placeholder and error images.
 *
 * @param modifier Modifier for the image.
 * @param contentScale The scaling method for the image content.
 * @param placeholderDrawableRes Resource ID for the placeholder image.
 * @param errorDrawableRes Resource ID for the error image.
 * @param imageUrl The URL of the image to load.
 * @param contentDescription The content description for accessibility.
 */
@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale =  ContentScale.Crop,
    @DrawableRes placeholderDrawableRes: Int = R.drawable.ic_github_gray,
    @DrawableRes errorDrawableRes: Int = R.drawable.ic_github_gray,
    imageUrl: String,
    contentDescription: String = ""
) {
    CoilImage(
        modifier = modifier.semantics {
            this.contentDescription = contentDescription
        },
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