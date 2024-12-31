package com.humanforce.humanforceandroidengineeringchallenge.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery.debounce(1000).collectLatest { query ->
                if (query.isNotBlank() && query.length >= 3) {
                    searchCitiesUseCase(
                        query = query,
                        onStart = { _uiState.update { it.copy(isLoading = true) } },
                        onError = { error ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = error
                                )
                            }
                        }
                    ).collect { cities ->
                        _uiState.update { it.copy(isLoading = false, cities = cities) }
                    }
                }
            }
        }
    }

    fun onSearchChanged(q: String) {
        if (q.isBlank()) {
            _uiState.update { it.copy(isLoading = false, cities = emptyList()) }
        } else {
            _searchQuery.value = q
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val error: String? = null
)