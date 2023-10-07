package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.User

sealed interface SendMoneyUiState {
	data object Loading : SendMoneyUiState
	data class Loaded(
		val beneficiaries: List<User>,
		val contacts: List<User>
	) : SendMoneyUiState
	
	class Error(val code: String) : SendMoneyUiState
}
