package com.mustfaibra.qpaymultiplatform.decompose.phoneverification

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.ContactsViewModel
import com.mustfaibra.qpaymultiplatform.viewmodels.VerifyPhoneViewModel

class PhoneVerificationComponentImpl(
	componentContext: ComponentContext,
	val onVerifiedSuccessfully: () -> Unit,
) : PhoneVerificationComponent, ComponentContext by componentContext {
	override val verificationViewModel: VerifyPhoneViewModel
		get() = instanceKeeper.getOrCreate { VerifyPhoneViewModel() }
	
	override fun onVerificationCompleted() {
		onVerifiedSuccessfully()
	}
}
