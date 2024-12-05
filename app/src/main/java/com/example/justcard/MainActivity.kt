@file:OptIn(ExperimentalFoundationApi::class)

package com.example.justcard

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.justcard.models.Word
import com.example.justcard.models.WordJson
import com.example.justcard.ui.theme.JustCardTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustCardTheme {
                GreetingPreview()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JustCardTheme {
        val jsonString = readJsonFromAssets(LocalContext.current, "sm1_new_kap1.json")
        val words = parseJsonToModel(jsonString)

        val pages = words.flatMap { word ->
            mutableListOf<Word>().apply {
                if (word.wordFirstLang.isNotEmpty() && word.wordSecondLang.isNotEmpty()) {
                    add(Word(word.id, word.wordFirstLang, word.wordSecondLang, true))
                }
                if (word.sentenceFirstLang.isNotEmpty() && word.sentenceSecondLang.isNotEmpty()) {
                    add(Word(word.id, word.sentenceFirstLang, word.sentenceSecondLang, false))
                }
            }
        }

        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { pages.size }
        )

        val cardFaceStateList = remember { pages.map { mutableStateOf(CardSides.Front) } }
        val scope = rememberCoroutineScope()
        
        var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
        val context = LocalContext.current

        LaunchedEffect(pagerState.currentPage) {
            val word = pages[pagerState.currentPage]
            val language = if (cardFaceStateList[pagerState.currentPage].value == CardSides.Front) "fi" else "ru"
            val soundFileName = if (word.isWord) {
                "sounds/${word.id}_w_${language}.mp3"
            } else {
                "sounds/${word.id}_s_${language}.mp3"
            }

            mediaPlayer?.release()

            mediaPlayer = createMediaPlayerFromAssets(context, soundFileName)

            mediaPlayer?.start()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
            ) { index ->
                val word = pages[index]
                val cardFace = cardFaceStateList[index].value
                WordCard(
                    context = LocalContext.current,
                    cardFace = cardFace,
                    onClick = {  cardFaceStateList[index].value = cardFace.next },
                    backText = word.firstLang,
                    frontText = word.secondLang,
                    id = word.id,
                    isWord = word.isWord,
                    modifier = Modifier
                        .padding(32.dp)
                        .aspectRatio(1.5f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


fun readJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun parseJsonToModel(jsonString: String): List<WordJson> {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<List<WordJson>>() {}.type)
}