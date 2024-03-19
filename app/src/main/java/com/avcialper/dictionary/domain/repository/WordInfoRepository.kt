package com.avcialper.dictionary.domain.repository

import com.avcialper.dictionary.domain.model.WordInfo
import com.avcialper.dictionary.util.constants.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfos(word: String): Flow<Resource<List<WordInfo>>>
}