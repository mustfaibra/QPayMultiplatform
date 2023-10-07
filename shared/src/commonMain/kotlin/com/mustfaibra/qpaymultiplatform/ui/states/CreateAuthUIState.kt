package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.User

sealed interface CreateAuthUIState {
	data object Idle : CreateAuthUIState
	data class Creating(val pin: String) : CreateAuthUIState
	data class Created(val user: User) : CreateAuthUIState
	data class Error(val message: String) : CreateAuthUIState
}
