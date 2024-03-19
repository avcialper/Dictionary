package com.avcialper.dictionary.di

import android.content.Context
import androidx.room.Room
import com.avcialper.dictionary.data.local.Converters
import com.avcialper.dictionary.data.local.WordInfoDatabase
import com.avcialper.dictionary.data.remote.DictionaryApi
import com.avcialper.dictionary.data.repository.WordInfoRepositoryImpl
import com.avcialper.dictionary.data.util.GsonParser
import com.avcialper.dictionary.domain.repository.WordInfoRepository
import com.avcialper.dictionary.domain.use_case.GetWordInfo
import com.avcialper.dictionary.util.constants.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideRepository(api: DictionaryApi, db: WordInfoDatabase): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoDao(@ApplicationContext context: Context): WordInfoDatabase {
        return Room.databaseBuilder(
            context,
            WordInfoDatabase::class.java,
            "dictionary_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    fun provideGetWordInfo(wordInfoRepository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(wordInfoRepository)
    }
}