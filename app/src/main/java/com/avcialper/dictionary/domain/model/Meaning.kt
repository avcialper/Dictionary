package com.avcialper.dictionary.domain.model

data class Meaning(
    val definitions: List<Definition?>?,
    val partOfSpeech: String?
)