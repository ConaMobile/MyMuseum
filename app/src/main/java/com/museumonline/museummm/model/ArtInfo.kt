package com.museumonline.museummm.model

import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class ArtInfo(
	val img: String? = null,
	val texnika: String? = null,
	val year: String? = null,
	val razmer: String? = null,
	val about: String? = null,
	val id: Int? = null,
	val avtor: String? = null,
	val nomi: String? = null
)

