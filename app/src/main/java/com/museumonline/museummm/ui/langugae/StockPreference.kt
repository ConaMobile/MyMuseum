package com.museumonline.museummm.ui.langugae

import android.content.Context
import android.content.Context.MODE_PRIVATE
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class StockPreference(val context: Context) {

    private val mode = MODE_PRIVATE

    var lang: String
        get() = context.getSharedPreferences("Language", mode).getString("language", "uz") ?: "uz"
        set(value) {
            context.getSharedPreferences("Language", mode).edit().putString("language", value).apply()
        }
}