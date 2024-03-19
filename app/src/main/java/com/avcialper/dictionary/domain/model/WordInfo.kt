package com.avcialper.dictionary.domain.model

data class WordInfo(
    val meanings: List<Meaning>?,
    val phonetic: String?,
    val word: String?,
    val audio: String?
)
