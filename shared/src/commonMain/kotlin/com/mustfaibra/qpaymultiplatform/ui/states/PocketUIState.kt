package com.mustfaibra.qpaymultiplatform.ui.states

import com.mustfaibra.qpaymultiplatform.data.entity.Pocket

sealed interface PocketUIState {
	data object Loading : PocketUIState
	data class Loaded(val pockets: List<Pocket>) : PocketUIState
	class Error(val code: String) : PocketUIState
}
