package com.sample.toyotacomic.repsoitiry

import com.sample.toyotacomic.service.ComicApiService
import com.sample.toyotacomic.model.ComicInfo
import javax.inject.Inject

interface ComicRepository {
    suspend fun getComicInfo(): ComicInfo
}

class ComicRepositoryImpl @Inject constructor(
    private val comicApiService: ComicApiService
) : ComicRepository {
    override suspend fun getComicInfo(): ComicInfo {
        return comicApiService.getComicInfo()
    }
}