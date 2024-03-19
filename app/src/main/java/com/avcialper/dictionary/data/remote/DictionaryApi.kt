package com.avcialper.dictionary.data.remote

import com.avcialper.dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfos(@Path("word") word: String): List<WordInfoDto>

}