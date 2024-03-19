package com.avcialper.dictionary.domain.use_case

import com.avcialper.dictionary.domain.model.WordInfo
import com.avcialper.dictionary.domain.repository.WordInfoRepository
import com.avcialper.dictionary.util.constants.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordInfo(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }
        return repository.getWordInfos(word)
    }

}