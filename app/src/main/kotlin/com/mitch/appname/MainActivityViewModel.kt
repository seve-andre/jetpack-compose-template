package com.mitch.appname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferences
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userPreferencesRepo: UserPreferencesRepo
) : ViewModel() {
    val uiState = userPreferencesRepo.userPreferencesData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed class MainActivityUiState {
    object Loading : MainActivityUiState()
    data class Success(val userPreferences: UserPreferences) : MainActivityUiState()
}
