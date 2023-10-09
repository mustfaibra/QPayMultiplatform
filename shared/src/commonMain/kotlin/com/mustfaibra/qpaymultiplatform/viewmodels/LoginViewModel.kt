package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.repositories.UserRepository
import com.mustfaibra.qpaymultiplatform.ui.states.LoginUIState
import com.mustfaibra.qpaymultiplatform.utils.encrypt
import com.mustfaibra.qpaymultiplatform.utils.validatePhone
import com.mustfaibra.qpaymultiplatform.utils.validatePin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
	val uiState: StateFlow<LoginUIState> = _uiState
	private val countryKey = "971"
	private val _phone = mutableStateOf(countryKey)
	val phone: State<String> = _phone
	private val _pin = mutableStateOf("")
	val pin: State<String> = _pin
	
	fun authenticateUser(phone: String, pin: String) {
		// Validate the data first
		if (validatePhone(phone = phone)) {
			if (validatePin(pin = pin)) {
				val encryptedPhone = phone.encrypt()
				val encryptedPin = pin.encrypt()
				_uiState.update { LoginUIState.Loading }
				viewModelScope.launch {
					UserRepository.checkUserAuthentication(
						encryptedPhone = encryptedPhone,
						encryptedPin = encryptedPin,
					).let {
						it?.let { user ->
							_uiState.update { LoginUIState.Authenticated(user) }
						} ?: run {
							_uiState.update { LoginUIState.Error.Auth("There is no account with this info!") }
						}
					}
				}
			}
			else {
				_uiState.update { LoginUIState.Error.Pin("Pin is not valid!") }
			}
		}
		else {
			_uiState.update { LoginUIState.Error.Phone("Phone number is not valid!") }
		}
	}
	
	fun updatePhone(phone: String) {
		if (phone.length in countryKey.length..countryKey.length.plus(9)
			&& phone.startsWith(countryKey)
		) {
			if (_uiState.value is LoginUIState.Error.Phone) {
				_uiState.value = LoginUIState.Idle
			}
			_phone.value = phone
		}
	}
	
	fun updatePin(pin: String) {
		_pin.value = pin
	}
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
