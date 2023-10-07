package com.mustfaibra.qpaymultiplatform.ui.states

sealed interface NationalIdSide {
	data object Front: NationalIdSide
	data object Back: NationalIdSide
}
