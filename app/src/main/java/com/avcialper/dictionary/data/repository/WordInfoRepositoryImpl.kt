package com.avcialper.dictionary.data.repository

import com.avcialper.dictionary.data.local.WordInfoDao
import com.avcialper.dictionary.data.remote.DictionaryApi
import com.avcialper.dictionary.domain.model.WordInfo
import com.avcialper.dictionary.domain.repository.WordInfoRepository
import com.avcialper.dictionary.util.constants.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfos(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())
        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }

        try {

            val remoteWordInfo = api.getWordInfos(word)
            dao.deleteWordInfos(remoteWordInfo.map { it.word })
            dao.insertWordInfos(remoteWordInfo.map { it.toWordInfo() })

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    wordInfos, e, "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    wordInfos, e,
                    "Couldn't reach server, check your internet connection!"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    wordInfos, e,
                    null
                )
            )
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}
