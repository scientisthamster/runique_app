package com.scientisthamster.runique

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.core.domain.SessionStorage
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(MainActivityState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(isLoggedIn = sessionStorage.get() != null)
            state = state.copy(isCheckingAuth = false)
        }
    }
}