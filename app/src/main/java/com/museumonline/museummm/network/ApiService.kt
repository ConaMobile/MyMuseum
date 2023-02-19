package com.museumonline.museummm.network

import androidx.annotation.Keep
import com.museumonline.museummm.model.ArtInfo
import com.museumonline.museummm.model.Arts
import com.museumonline.museummm.model.Directions
import com.museumonline.museummm.model.History
import io.michaelrocks.paranoid.Obfuscate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@Obfuscate
@Keep
interface ApiService {

    @GET("/index.php?r=site/tarjson")
    suspend fun getHistory(): Response<History>

    @GET("/index.php?r=site/tarjsonru")
    suspend fun getHistoryRu(): Response<History>

    @GET("/index.php?r=site/tarjsonen")
    suspend fun getHistoryEn(): Response<History>



    @GET("/index.php?r=yunalish/data")
    suspend fun getDirections(): Response<Directions>

    @GET("/index.php?r=yunalish/dataru")
    suspend fun getDirectionsRu(): Response<Directions>

    @GET("/index.php?r=yunalish/dataen")
    suspend fun getDirectionsEn(): Response<Directions>



    @GET("/index.php?r=asar%2Fdata")
    suspend fun getArts(@Query("id") id: String): Response<Arts>

    @GET("/index.php?r=asar%2Fdataru")
    suspend fun getArtsRu(@Query("id") id: String): Response<Arts>

    @GET("/index.php?r=asar%2Fdataen")
    suspend fun getArtsEn(@Query("id") id: String): Response<Arts>



    @GET("/index.php?r=asar%2Fone")
    suspend fun getArtInfo(@Query("id") id: String): Response<ArtInfo>

    @GET("/index.php?r=asar%2Foneru")
    suspend fun getArtInfoRu(@Query("id") id: String): Response<ArtInfo>

    @GET("/index.php?r=asar%2Foneen")
    suspend fun getArtInfoEn(@Query("id") id: String): Response<ArtInfo>
}