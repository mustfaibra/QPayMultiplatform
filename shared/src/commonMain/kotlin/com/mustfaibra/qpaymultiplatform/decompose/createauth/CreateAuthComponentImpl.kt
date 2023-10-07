package com.mustfaibra.qpaymultiplatform.decompose.createauth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.viewmodels.CreateAuthenticateViewModel

class CreateAuthComponentImpl(
	private val componentContext: ComponentContext,
	private val onAuthenticationCreated: (user: User) -> Unit,
) : CreateAuthComponent, ComponentContext by componentContext {
	override val createAuthenticateViewModel: CreateAuthenticateViewModel
		get() = instanceKeeper.getOrCreate { CreateAuthenticateViewModel() }
	
	override fun onAuthPinCreated(user: User) {
		onAuthenticationCreated(user)
	}
}
