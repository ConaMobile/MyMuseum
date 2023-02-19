package com.museumonline.museummm.ui.langugae

import android.content.Context
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class BaseRepository {

    companion object {
        fun getLang(context: Context) = StockPreference(context).lang

        fun setLang(lang: String, context: Context) {
            StockPreference(context).lang = lang
        }
    }
}
