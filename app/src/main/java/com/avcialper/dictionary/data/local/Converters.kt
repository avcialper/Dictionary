package com.avcialper.dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.avcialper.dictionary.data.util.JsonParser
import com.avcialper.dictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters (private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<List<Meaning>>(
            json,
            object : TypeToken<List<Meaning>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<List<Meaning>>() {}.type
        ) ?: "[]"
    }
}