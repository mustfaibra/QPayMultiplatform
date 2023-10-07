package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.Transaction


sealed interface HomeUiState {
	data object Loading : HomeUiState
	data class Loaded(val transactions: List<Transaction>) : HomeUiState
	data object Filtering : HomeUiState
	class Error(val code: String) : HomeUiState
}
