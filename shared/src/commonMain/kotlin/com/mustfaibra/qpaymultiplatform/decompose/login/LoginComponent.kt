package com.mustfaibra.qpaymultiplatform.decompose.login

import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.viewmodels.LoginViewModel

interface LoginComponent {
	val loginViewModel: LoginViewModel
	
	fun onAuthenticationSuccess(user: User, rememberMe: Boolean)
}
