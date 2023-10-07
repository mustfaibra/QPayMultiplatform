package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.data.repositories.UserRepository
import com.mustfaibra.qpaymultiplatform.ui.states.CreateAuthUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CreateAuthenticateViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	val uiState = mutableStateOf<CreateAuthUIState>(CreateAuthUIState.Idle)
	val pin = mutableStateOf("")
	
	fun updatePin(newPin: String) {
		pin.value = newPin
	}
	
	fun saveUserPin(
		pin: String,
		onCredentialsCreated: (user: User) -> Unit,
	) {
		uiState.value = CreateAuthUIState.Creating(pin)
		viewModelScope.launch {
			// Now we should have a fake user generated for our journey
			UserRepository.getAuthenticatedUser().let(onCredentialsCreated)
		}
	}
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
