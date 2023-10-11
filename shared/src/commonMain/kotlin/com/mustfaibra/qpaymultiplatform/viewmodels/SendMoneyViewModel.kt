package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.repositories.UserRepository
import com.mustfaibra.qpaymultiplatform.ui.states.SendMoneyUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SendMoneyViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
	val uiState = mutableStateOf<SendMoneyUiState>(SendMoneyUiState.Loading)
	
	init {
		getBeneficiariesNContacts()
	}
	
	private fun getBeneficiariesNContacts() {
		if (uiState.value is SendMoneyUiState.Loading) {
			viewModelScope.launch {
				val beneficiaries = async { UserRepository.getBeneficiaries() }
				val contacts = async { UserRepository.getContacts() }
				uiState.value = SendMoneyUiState.Loaded(
					beneficiaries = beneficiaries.await(),
					contacts = contacts.await(),
				)
			}
		}
	}
}
