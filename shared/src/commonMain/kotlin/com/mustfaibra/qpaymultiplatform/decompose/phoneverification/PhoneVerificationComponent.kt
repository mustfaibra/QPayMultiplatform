package com.mustfaibra.qpaymultiplatform.decompose.phoneverification

import com.mustfaibra.qpaymultiplatform.viewmodels.VerifyPhoneViewModel

interface PhoneVerificationComponent {
	val verificationViewModel: VerifyPhoneViewModel
	
	fun onVerificationCompleted()
}
