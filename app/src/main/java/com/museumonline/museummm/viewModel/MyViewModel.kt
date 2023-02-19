package com.museumonline.museummm.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.museumonline.museummm.model.*
import com.museumonline.museummm.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.launch
import javax.inject.Inject

@Obfuscate
@HiltViewModel
class MyViewModel @Inject constructor(private var networkRepository: NetworkRepository) :
    ViewModel() {

    var history = MutableLiveData<History?>()
    fun getHistory(lang: String) = viewModelScope.launch {
        try {
            when (lang) {
                "uz" -> networkRepository.getHistory().let {
                    if (it.isSuccessful) {
                        history.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }

                "ru" -> networkRepository.getHistoryRu().let {
                    if (it.isSuccessful) {
                        history.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }

                "en" -> networkRepository.getHistoryEn().let {
                    if (it.isSuccessful) {
                        history.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            }

        } catch (e: Exception) {
            Log.d("aaa", "${e.message}")
        }
    }

    fun clearHistory(){
        history.postValue(null)
    }



    var directions = MutableLiveData<Directions>()
    fun getDirections(lang: String) = viewModelScope.launch {
        try {
            when (lang) {
                "uz" -> networkRepository.getDirections().let {
                    if (it.isSuccessful) {
                        directions.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
                "ru" -> networkRepository.getDirectionsRu().let {
                    if (it.isSuccessful) {
                        directions.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }

                "en" -> networkRepository.getDirectionsEn().let {
                    if (it.isSuccessful) {
                        directions.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            }

        } catch (e: Exception) {
            Log.d("kal", "${e.message}")
        }
    }

    var arts = MutableLiveData<Arts>()
    fun getArts(id: String, lang: String) = viewModelScope.launch {
        try {
            when (lang) {
                "uz" -> networkRepository.getArts(id).let {
                    if (it.isSuccessful) {
                        arts.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }

                "ru" -> networkRepository.getArtsRu(id).let {
                    if (it.isSuccessful) {
                        arts.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }

                "en" -> networkRepository.getArtsEn(id).let {
                    if (it.isSuccessful) {
                        arts.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            }


        } catch (e: Exception) {
            Log.d("aaa", "${e.message}")
        }
    }

    var artDesc = MutableLiveData<Direction>()
    fun setArtDescription(it: Direction) = viewModelScope.launch {
        artDesc.postValue(it)
    }

    var artInfo = MutableLiveData<ArtInfo>()
    fun getArtInfo(id: String, lang: String) = viewModelScope.launch {
        try {
            when (lang) {
                "uz" -> networkRepository.getArtInfo(id).let {
                    if (it.isSuccessful) {
                        artInfo.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
                "ru" -> networkRepository.getArtInfoRu(id).let {
                    if (it.isSuccessful) {
                        artInfo.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
                "en" -> networkRepository.getArtInfoEn(id).let {
                    if (it.isSuccessful) {
                        artInfo.postValue(it.body())
                    } else {
                        Log.d(
                            "aaa",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            }

        } catch (e: Exception) {
            Log.d("aaa", "${e.message}")
        }
    }

}