package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.User

sealed interface LoginUIState {
	data object Idle : LoginUIState
	data object Loading : LoginUIState
	data class Authenticated(val user: User) : LoginUIState
	sealed class Error(val message: String) : LoginUIState {
		data class Phone(val why: String) : Error(message = why)
		data class Pin(val why: String) : Error(message = why)
		data class Auth(val why: String) : Error(message = why)
	}
}
