package com.example.justcard

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.IOException


enum class CardSides(val angle: Float) {
    Front(0f) {
        override val next: CardSides
            get() = Back
    },
    Back(180f) {
        override val next: CardSides
            get() = Front
    };

    abstract val next: CardSides
}


@Composable
fun WordCard(
    context: Context,
    cardFace: CardSides,
    onClick: (CardSides) -> Unit,
    onClickSound: () -> Unit = {},
    backText: String,
    frontText: String,
    id: Int,
    isWord: Boolean,
    modifier: Modifier
) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val language = if (cardFace == CardSides.Front) "fi" else "ru"
    val soundFileName = if (isWord) "sounds/${id}_w_${language}.mp3" else "sounds/${id}_s_${language}.mp3"

    mediaPlayer = createMediaPlayerFromAssets(context, soundFileName)

    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        ), label = ""
    )

    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            }
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_volume),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onClickSound()
                        mediaPlayer?.start()
                    }
                    .graphicsLayer {
                        rotationY = rotation.value
                    }
                    .align(
                        if (rotation.value <= 90f || rotation.value >= 270f) Alignment.TopEnd
                        else Alignment.TopStart
                    )
                    .padding(8.dp)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (rotation.value <= 90f) {
                    Text(text = frontText, fontSize = 24.sp)
                } else {
                    Text(
                        text = backText,
                        fontSize = 24.sp,
                        modifier = Modifier.graphicsLayer {
                            rotationY = 180f
                        }
                    )
                }
            }
        }
    }
}


fun createMediaPlayerFromAssets(context: Context, fileName: String): MediaPlayer? {
    return try {
        val assetFileDescriptor = context.assets.openFd(fileName)
        MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}