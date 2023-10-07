package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	
	private val countryKey = "+971 "
	val phoneInputError = mutableStateOf("")
	val phone = mutableStateOf(countryKey)
	val isLoading = mutableStateOf(false)
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
	fun updatePhoneNumber(phoneNum: String) {
		// First we should make sure country key not deleted
		if (phoneNum.length in countryKey.length .. countryKey.length.plus(9)
			&& phoneNum.startsWith(countryKey)
		) {
			if (phoneInputError.value.isNotBlank()) {
				phoneInputError.value = ""
			}
			phone.value = phoneNum
		}
	}
	
	fun sendOtp(phone: String, onOtpSent: () -> Unit) {
		if (phone.isNotBlank() && phone.length >= 9) {
			isLoading.value = true
			viewModelScope.launch {
				delay(3000)
				isLoading.value = false
				onOtpSent()
			}
		}
		else {
			phoneInputError.value = "Make sure you entered a valid phone number!"
		}
	}
	
}
