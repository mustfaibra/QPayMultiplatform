package com.mustfaibra.qpaymultiplatform.decompose.createaccount

import com.mustfaibra.qpaymultiplatform.viewmodels.SignInOptionsViewModel

interface SignInOptionsComponent {
	val signInOptionsViewModel: SignInOptionsViewModel
	
	fun onCreateAccountClicked()
	fun onSignInToAccountClicked()
}
