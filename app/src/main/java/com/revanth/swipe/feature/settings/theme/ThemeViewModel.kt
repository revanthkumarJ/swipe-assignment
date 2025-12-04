package com.revanth.swipe.feature.settings.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.data.repos.UserRepository
import com.revanth.swipe.core.models.ThemeConfig
import com.revanth.swipe.feature.onboarding.OnboardingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ChangeThemeViewModel(
    private val repository: UserRepository,
) : ViewModel(){

    private val _state = MutableStateFlow(ThemeState())
    val state: StateFlow<ThemeState> = _state.asStateFlow()

    init {
        repository.getUser()
            .onEach { it ->
                val theme= ThemeConfig.fromName(it?.theme)
                when(theme){
                    ThemeConfig.DARK -> handleThemeSelection(ThemeConfig.DARK)
                    ThemeConfig.LIGHT -> handleThemeSelection(ThemeConfig.LIGHT)
                    ThemeConfig.SYSTEM -> handleThemeSelection(ThemeConfig.SYSTEM)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleAction(action: ThemeAction) {
        when(action){
            is ThemeAction.Internal.LoadTheme -> handleLoadTheme(action)
            ThemeAction.SetTheme -> handleSetTheme(_state.value.currentTheme)
            is ThemeAction.ThemeSelection -> handleThemeSelection(action.theme)
        }
    }

    private fun handleThemeSelection(theme: ThemeConfig) {
        _state.update {
            it.copy(
                currentTheme = theme
            )
        }
    }

    private fun handleSetTheme(theme: ThemeConfig) {
        viewModelScope.launch {
            repository.updateTheme(theme.themeName)
            _state.update {
                it.copy(currentTheme = theme)
            }
        }
    }

    private fun handleLoadTheme(action: ThemeAction.Internal.LoadTheme) {
        _state.update {
            it.copy(currentTheme = action.theme)
        }
    }
}

internal data class ThemeState(
    val currentTheme: ThemeConfig = ThemeConfig.SYSTEM,
) {
    val themeOptions
        get() = listOf(
            ThemeConfig.DARK to "Dark Theme",
            ThemeConfig.LIGHT to "Light Theme",
            ThemeConfig.SYSTEM to "System Default",
        )
}

internal sealed interface ThemeEvent {
    data object OnNavigateBack : ThemeEvent
}

internal sealed interface ThemeAction {
    data object SetTheme : ThemeAction

    data class ThemeSelection(val theme: ThemeConfig) : ThemeAction

    sealed interface Internal : ThemeAction {

        data class LoadTheme(val theme: ThemeConfig) : Internal
    }
}

