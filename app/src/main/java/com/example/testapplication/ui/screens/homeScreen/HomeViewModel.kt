package com.example.testapplication.ui.screens.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.AppRepository
import com.example.testapplication.data.local.AppRepositoryLocal
import com.example.testapplication.data.local.favorite.Favorite
import com.example.testapplication.network.Story
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AppUiState {
    object Success : AppUiState
    object Error : AppUiState
    object Loading : AppUiState
}
class HomeViewModel(
    private val appRepository: AppRepository,
    private val appRepositoryLocal: AppRepositoryLocal
): ViewModel() {

    /**
     * The changeable state in which the status of the most recent request is stored
     */
    var appUiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    private val firstRequest = mutableStateOf<List<Story>>(listOf())
    private val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)

    val isSearching = _isSearching.asStateFlow()
    private val _newsList = MutableStateFlow<List<Story>>(firstRequest.value)

    val newsList = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_newsList) { text, news ->
            if (text.isBlank()) {
                if (_newsList !== firstRequest) _newsList.update { firstRequest.value }
                news
            } else {
                delay(2000L)
                news.filter {
                    doesMatchSearchQuery(
                        query = text,
                        label = it.news_name,
                    )
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _newsList.value
        )
    /**
     * A function that sends a request to display a list of characters, indicating the page.
     * The search parameters are taken from the [SearchFilter] variable
     */
    fun getNews() {
        viewModelScope.launch {
            appUiState = AppUiState.Loading
            appUiState = try {
                val newsRequest = appRepository.getNews()
                firstRequest.value = newsRequest.detail.stories
                AppUiState.Success
            } catch (e: IOException) {
                AppUiState.Error
            } catch (e: HttpException) {
                AppUiState.Error
            }
        }
    }

    /**
    Фильтрация поиского запроса на схожесть
     */
    private fun doesMatchSearchQuery(
        query: String,
        label: String
    ): Boolean {
        return label.contains(query, ignoreCase = true)
    }
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    suspend fun insertFavorite(uniqueName: String) {
        appRepositoryLocal.insertFavorite(
            Favorite(uniqueName = uniqueName)
        )
    }

    suspend fun deleteFavorite(uniqueName: String) {
        appRepositoryLocal.deleteFavorite(uniqueName)
    }

    fun getFavoriteNews(uniqueName: String): LiveData<Boolean> {
        appRepositoryLocal.getFavoriteNews(uniqueName)
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = uniqueName == appRepositoryLocal.getFavoriteNews(uniqueName)
                .map { it?.uniqueName ?: "" }
                .stateIn(
                    scope = viewModelScope
                ).value
        }/*
        val getNews = appRepositoryLocal.getFavoriteNews(uniqueName)
            .map { it?.uniqueName ?: "" }
            .stateIn(
                scope = viewModelScope
            )*/
        return result
    }
    init {
        getNews()
    }
}