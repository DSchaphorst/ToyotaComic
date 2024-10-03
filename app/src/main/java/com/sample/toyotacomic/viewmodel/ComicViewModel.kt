package com.sample.toyotacomic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.toyotacomic.states.ComicState
import com.sample.toyotacomic.repsoitiry.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: inject dispatchers

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _comicState = MutableStateFlow<ComicState>(ComicState.Loading)
    val comicState: StateFlow<ComicState> = _comicState.asStateFlow()

    init {
        fetchComicInfo()
    }


    private fun fetchComicInfo() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val info = comicRepository.getComicInfo()
                _comicState.value = ComicState.Success(info)
            } catch (e: Exception) {
                _comicState.value = ComicState.Error("Error fetching comic info: ${e.message}")
            }
        }
    }
}