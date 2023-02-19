package com.museumonline.museummm.model

import com.google.gson.annotations.SerializedName
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class Arts(
    @SerializedName("asars")
    val arts: List<Art>? = null
)
@Obfuscate
data class Art(
    val img: String? = null,
    val id: Int? = null,
    val avtor: String? = null,
    val nomi: String? = null
)

