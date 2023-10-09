package com.mustfaibra.qpaymultiplatform.decompose.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.viewmodels.LoginViewModel

class LoginComponentImpl(
	componentContext: ComponentContext,
	private val onAuthenticated: (user: User, rememberMe: Boolean) -> Unit,
) : LoginComponent, ComponentContext by componentContext {
	override val loginViewModel: LoginViewModel
		get() = instanceKeeper.getOrCreate { LoginViewModel() }
	
	override fun onAuthenticationSuccess(user: User, rememberMe: Boolean) {
		onAuthenticated(user, rememberMe)
	}
}
