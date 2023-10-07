package com.mustfaibra.qpaymultiplatform.decompose.nationalid

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.NationalIdViewModel

class NationalIdComponentImpl(
	private val componentContext: ComponentContext,
	private val onNationalIdCaptured: (front: String, back: String) -> Unit,
) : NationalIdComponent, ComponentContext by componentContext {
	override val nationalIdViewModel: NationalIdViewModel
		get() = instanceKeeper.getOrCreate { NationalIdViewModel() }
	
	override fun onCaptured(front: String, back: String) {
		onNationalIdCaptured(front, back)
	}
}
