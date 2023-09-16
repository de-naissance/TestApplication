package com.example.testapplication.ui.screens.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.AppRepository
import com.example.testapplication.network.Detail
import com.example.testapplication.network.NewsRequest
import com.example.testapplication.network.Story
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AppUiState {
    data class Success(val characterRequest: List<Story>) : AppUiState
    object Error : AppUiState
    object Loading : AppUiState
}
class HomeViewModel(
    private val appRepository: AppRepository,
): ViewModel() {

    /**
     * The changeable state in which the status of the most recent request is stored
     */
    var appUiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    init {
        getNews()
    }

    /**
     * A function that sends a request to display a list of characters, indicating the page.
     * The search parameters are taken from the [SearchFilter] variable
     */
    fun getNews() {
        viewModelScope.launch {
            appUiState = AppUiState.Loading
            appUiState = try {
                val newsRequest = appRepository.getNews()
                val characters = newsRequest.detail.stories
                AppUiState.Success(characters)
            } catch (e: IOException) {
                AppUiState.Error
            } catch (e: HttpException) {
                AppUiState.Error
            }
        }
    }
}