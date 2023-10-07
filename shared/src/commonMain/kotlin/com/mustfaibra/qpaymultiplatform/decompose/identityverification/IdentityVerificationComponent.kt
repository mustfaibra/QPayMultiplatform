package com.mustfaibra.qpaymultiplatform.decompose.identityverification

import androidx.compose.ui.graphics.Color
import com.mustfaibra.qpaymultiplatform.viewmodels.IdentityVerificationViewModel

interface IdentityVerificationComponent {
	val identityVerificationViewModel: IdentityVerificationViewModel
	
	fun onIdentityVerified()
	
	fun onChangeStatusBarColor(color: Color)
}
