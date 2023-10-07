package com.mustfaibra.qpaymultiplatform.decompose.identityverification

import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.IdentityVerificationViewModel

class IdentityVerificationComponentImpl(
	private val componentContext: ComponentContext,
	private val onIdentityVerifiedSuccessfully: () -> Unit,
	private val onStatusBarColorChange: (color: Color) -> Unit,
) : IdentityVerificationComponent, ComponentContext by componentContext {
	override val identityVerificationViewModel: IdentityVerificationViewModel
		get() = instanceKeeper.getOrCreate { IdentityVerificationViewModel() }
	
	override fun onIdentityVerified() {
		onIdentityVerifiedSuccessfully()
	}
	
	override fun onChangeStatusBarColor(color: Color) {
		onStatusBarColorChange(color)
	}
}
