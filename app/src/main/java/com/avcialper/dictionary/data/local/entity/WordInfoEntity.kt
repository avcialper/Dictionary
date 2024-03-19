package com.avcialper.dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avcialper.dictionary.domain.model.Meaning
import com.avcialper.dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val word: String,
    val audio: String,
    val phonetic: String,
    val meanings: List<Meaning>
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            phonetic = phonetic,
            word = word,
            audio = audio
        )
    }
}