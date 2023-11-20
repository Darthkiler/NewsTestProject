package com.darthkiler.newstestproject.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darthkiler.newstestproject.domain.usecase.GetContentByIdUseCase
import com.darthkiler.newstestproject.domain.utils.Status
import com.darthkiler.newstestproject.navigation.NavigationArgs
import com.darthkiler.newstestproject.navigation.getArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getContentByIdUseCase: GetContentByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var contentId: String
    private val _uiState = MutableStateFlow(
        DetailsScreenUIState(
            isLoading = true
        ) {
            handleEvent(it)
        }
    )

    val uiState = _uiState.asStateFlow()

    init {
        handleArgument(savedStateHandle.getArgument())
    }

    private fun handleArgument(detailsScreenArgs: NavigationArgs.DetailsScreenArgs) {
        contentId = detailsScreenArgs.getId()
        getContent(contentId)
    }

    private fun getContent(id: String) {
        viewModelScope.launch {
            getContentByIdUseCase(id).let { contentResult ->
                println(contentResult)
                when (contentResult) {
                    is Status.Error -> {
                        handleError(contentResult.error)
                    }
                    is Status.Success -> {
                        _uiState.update {
                            it.copy(
                                contentModel = contentResult.data,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleEvent(event: DetailsListScreenEvent) {
        when (event) {
            DetailsListScreenEvent.OnRetryClick -> {
                onRetryClick()
            }

            DetailsListScreenEvent.OnLinkClick -> {
                // TODO open link
            }
        }
    }

    private fun handleError(e: Exception) {
        // TODO обработать разные ошибки
        _uiState.update {
            it.copy(
                isError = true
            )
        }
    }

    private fun onRetryClick() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        getContent(contentId)
    }

    sealed interface DetailsListScreenEvent {
        data object OnRetryClick : DetailsListScreenEvent
        data object OnLinkClick : DetailsListScreenEvent
    }
}
