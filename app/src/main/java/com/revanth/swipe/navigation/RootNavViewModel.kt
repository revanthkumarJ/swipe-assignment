package com.revanth.swipe.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.data.repos.UserRepository
import com.revanth.swipe.core.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RootNavViewModel(
    private val userDataRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<RootNavState>(RootNavState.Splash)
    val state: StateFlow<RootNavState> = _state.asStateFlow()

    init {
        observeUserData()
    }

    private fun observeUserData() {
        viewModelScope.launch {
            userDataRepository.getUser().collectLatest { userData ->
                Log.d("RootNavViewModel", "observeUserData: $userData")
                _state.value = if (userData?.firstTimeUser?:true) {
                    RootNavState.ShowOnboarding
                } else {
                    RootNavState.Home
                }
            }
        }
    }
}


sealed class RootNavState {

    data object ShowOnboarding : RootNavState()

    data object Splash : RootNavState()

    data object Home : RootNavState()
}