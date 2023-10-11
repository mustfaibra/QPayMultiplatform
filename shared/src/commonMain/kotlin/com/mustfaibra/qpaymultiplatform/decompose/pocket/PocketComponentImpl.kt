package com.mustfaibra.qpaymultiplatform.decompose.pocket

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.PocketViewModel

class PocketComponentImpl(
	componentContext: ComponentContext
) : PocketComponent, ComponentContext by componentContext {
	override val pocketViewModel: PocketViewModel
		get() = instanceKeeper.getOrCreate { PocketViewModel() }
}
