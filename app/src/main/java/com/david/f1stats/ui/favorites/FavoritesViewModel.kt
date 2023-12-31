package com.david.f1stats.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteRacesUseCase: GetAllFavoriteRacesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
): ViewModel() {

    private val _favoriteRaces = MutableLiveData<List<FavoriteRace>?>()
    val favoriteRaces: LiveData<List<FavoriteRace>?> = _favoriteRaces

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchAllFavoriteRaces()
    }

    fun fetchAllFavoriteRaces() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getAllFavoriteRacesUseCase()
                _favoriteRaces.value = result
            }  catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
                _isDeleted.value = false
            }
        }
    }

    fun deleteFavorite(idRace: Int) {
        viewModelScope.launch {
            try {
                deleteFavoriteUseCase(idRace)
                _isDeleted.value = true
                fetchAllFavoriteRaces()

            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
