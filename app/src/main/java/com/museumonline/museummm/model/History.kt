package com.museumonline.museummm.model

import com.google.gson.annotations.SerializedName
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class History(
	@SerializedName("tarix")
	val historyList: List<HistoryItem?>? = null
)
@Obfuscate
data class HistoryItem(
	val id: Int? = null,
	val content: String? = null
)


