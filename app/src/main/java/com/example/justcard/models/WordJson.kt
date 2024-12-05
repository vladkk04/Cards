package com.example.justcard.models


data class WordJson(
    val id : Int,
    val wordFirstLang: String,
    val sentenceFirstLang: String,
    val wordSecondLang: String,
    val sentenceSecondLang: String,
)

data class Word(
    val id : Int,
    val firstLang: String,
    val secondLang: String,
    val isWord: Boolean,
)
