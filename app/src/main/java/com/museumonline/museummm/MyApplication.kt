package com.museumonline.museummm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
@HiltAndroidApp
class MyApplication : Application() {
}