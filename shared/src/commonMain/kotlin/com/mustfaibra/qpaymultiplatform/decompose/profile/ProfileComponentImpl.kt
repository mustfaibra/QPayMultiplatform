package com.mustfaibra.qpaymultiplatform.decompose.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.ProfileViewModel

class ProfileComponentImpl(
	componentContext: ComponentContext,
) : ProfileComponent, ComponentContext by componentContext {
	override val profileViewModel: ProfileViewModel
		get() = instanceKeeper.getOrCreate { ProfileViewModel() }
}
