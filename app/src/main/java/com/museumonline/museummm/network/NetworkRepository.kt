package com.museumonline.museummm.network

import androidx.annotation.Keep
import io.michaelrocks.paranoid.Obfuscate
import javax.inject.Inject


@Obfuscate
@Keep
class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getHistory() = apiService.getHistory()
    suspend fun getHistoryEn() = apiService.getHistoryEn()
    suspend fun getHistoryRu() = apiService.getHistoryRu()

    suspend fun getDirections() = apiService.getDirections()
    suspend fun getDirectionsEn() = apiService.getDirectionsEn()
    suspend fun getDirectionsRu() = apiService.getDirectionsRu()

    suspend fun getArts(id: String) = apiService.getArts(id)
    suspend fun getArtsEn(id: String) = apiService.getArtsEn(id)
    suspend fun getArtsRu(id: String) = apiService.getArtsRu(id)

    suspend fun getArtInfo(id: String) = apiService.getArtInfo(id)
    suspend fun getArtInfoEn(id: String) = apiService.getArtInfoEn(id)
    suspend fun getArtInfoRu(id: String) = apiService.getArtInfoRu(id)
}