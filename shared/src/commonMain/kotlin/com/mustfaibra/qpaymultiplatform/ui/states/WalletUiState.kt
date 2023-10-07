package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.Transaction

sealed interface WalletUiState {
	data object Loading : WalletUiState
	data class Loaded(val transactions: List<Transaction>) : WalletUiState
	class Error(val code: String) : WalletUiState
}
