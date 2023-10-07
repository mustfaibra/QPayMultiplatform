package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.entity.ActivityFilter
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.data.repositories.TransactionRepository
import com.mustfaibra.qpaymultiplatform.data.repositories.UserRepository
import com.mustfaibra.qpaymultiplatform.ui.states.HomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	val uiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
	val historyFilter = mutableStateOf<ActivityFilter>(ActivityFilter.LastFiveTrans)
	val beneficiaries = mutableStateListOf<User>()
	lateinit var me: User
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
	fun init(me: User) {
		this.me = me
		if (uiState.value !is HomeUiState.Loaded) {
			getBeneficiaries()
			getHistory()
		}
	}
	
	private fun getBeneficiaries() {
		if (beneficiaries.isEmpty()) {
			viewModelScope.launch {
				UserRepository.getBeneficiaries().let {
					beneficiaries.addAll(it)
				}
			}
		}
	}
	
	private fun getHistory() {
		viewModelScope.launch {
			TransactionRepository.getTransaction(me, historyFilter.value).let {
				uiState.value = HomeUiState.Loaded(transactions = it)
			}
		}
	}
	
	fun getFilteredHistory(filter: ActivityFilter) {
		historyFilter.value = filter
//		uiState.value = HomeUiState.Filtering
//
//		viewModelScope.launch {
//			TransactionRepository.getTransaction(me, filter).let {
//				uiState.value = HomeUiState.Loaded(transactions = it)
//			}
//		}
	}
}
