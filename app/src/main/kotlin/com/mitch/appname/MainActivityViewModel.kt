package com.mitch.appname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.appname.domain.models.AppTheme
import com.mitch.appname.data.settings.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    /**
     * Initial [MainActivity] ui state is set to [MainActivityUiState.Loading] and mapped to
     * [MainActivityUiState.Success] once the [AppTheme] data is retrieved
     */
    val uiState = userSettingsRepository.getTheme().map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainActivityUiState.Loading
    )
}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data class Success(val theme: AppTheme) : MainActivityUiState()
}
