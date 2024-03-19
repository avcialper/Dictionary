package com.avcialper.dictionary.data.remote.dto


import com.avcialper.dictionary.domain.model.Definition
import com.google.gson.annotations.SerializedName

data class DefinitionDto(
    @SerializedName("definition")
    val definition: String?,
    @SerializedName("example")
    val example: String?,
) {
    fun toDefinition(): Definition {
        return Definition(
            definition = definition,
            example = example
        )
    }
}