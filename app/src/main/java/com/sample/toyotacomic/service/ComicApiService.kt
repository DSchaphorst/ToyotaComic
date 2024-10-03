package com.sample.toyotacomic.service

import com.sample.toyotacomic.model.ComicInfo
import retrofit2.http.GET

interface ComicApiService {
    @GET("info.0.json") // Fetch the latest comic
    suspend fun getComicInfo(): ComicInfo

}