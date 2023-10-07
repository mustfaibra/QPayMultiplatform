package com.mustfaibra.qpaymultiplatform.decompose.createauth

import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.viewmodels.CreateAuthenticateViewModel

interface CreateAuthComponent {
	val createAuthenticateViewModel: CreateAuthenticateViewModel
	
	fun onAuthPinCreated(user: User)
}
