package com.sample.toyotacomic.states

import com.sample.toyotacomic.model.ComicInfo

sealed class ComicState {
    object Loading : ComicState()
    data class Success(val comicInfo: ComicInfo) : ComicState()
    data class Error(val message: String) : ComicState()
}

//sealed class ComicState<T> {
//    data class Success<T>(val data: T) : ComicState<T>()
//    data class Error<T>(val message: String) : ComicState<T>()
//    object Loading : ComicState<Nothing>()
//
// }