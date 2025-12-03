package com.revanth.swipe.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.data.repos.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val Total_Pages = 2

class OnboardingViewModel(
    private val repo: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    private val _eventState = MutableSharedFlow<OnboardingEvent>()
    val eventState: SharedFlow<OnboardingEvent> = _eventState.asSharedFlow()

    fun handleAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.NextPage -> handleNextPage()
            is OnboardingAction.CompleteOnboarding -> handleCompleteOnboarding()
            is OnboardingAction.Internal.UpdateCurrentPage -> handleUpdateCurrentPage(action.page)
        }
    }

    private fun handleNextPage() {
        val nextPage = _state.value.currentPage + 1

        if (nextPage <= state.value.totalPages) {
            _state.update { it.copy(currentPage = nextPage) }
        } else {
            handleCompleteOnboarding()
        }
    }

    private fun handleCompleteOnboarding() {
        viewModelScope.launch {
            repo.setFirstTimeUser(false)
            _eventState.emit(OnboardingEvent.OnboardingCompleted)
        }
    }

    private fun handleUpdateCurrentPage(page: Int) {
        _state.update { it.copy(currentPage = page) }
    }
}

data class OnboardingState(
    val currentPage: Int = 1,
    val totalPages: Int = Total_Pages,
)

sealed interface OnboardingEvent {
    data object OnboardingCompleted : OnboardingEvent
}

sealed interface OnboardingAction {
    data object NextPage : OnboardingAction
    data object CompleteOnboarding : OnboardingAction

    sealed interface Internal : OnboardingAction {
        data class UpdateCurrentPage(val page: Int) : Internal
    }
}