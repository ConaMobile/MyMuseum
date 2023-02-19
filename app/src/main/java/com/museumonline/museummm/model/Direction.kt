package com.museumonline.museummm.model

import com.google.gson.annotations.SerializedName
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class Directions(
	@SerializedName("yuns")
	val directions: List<Direction>? = null
)
@Obfuscate
data class Direction(
	val img: String? = null,
	val cat: String? = null,
	val about: String? = null,
	val id: Int? = null,
	val nomi: String? = null
)

