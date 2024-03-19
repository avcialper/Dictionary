package com.avcialper.dictionary.data.remote.dto


import com.avcialper.dictionary.data.local.entity.WordInfoEntity
import com.avcialper.dictionary.domain.model.Meaning
import com.google.gson.annotations.SerializedName

data class WordInfoDto(
    @SerializedName("meanings")
    val meanings: List<MeaningDto>,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticDto>?,
    @SerializedName("word")
    val word: String
) {
    fun toWordInfo(): WordInfoEntity {
        val filteredPhonetics = phonetics?.filter {
            it.audio?.isNotBlank() == true && it.text?.isNotBlank() == true
        }

        return WordInfoEntity(
            word = word,
            phonetic = filteredPhonetics!![0].text!!,
            meanings = meanings.map { it.toMeaning() },
            audio = filteredPhonetics[0].audio!!
        )
    }
}