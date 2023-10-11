package com.mustfaibra.qpaymultiplatform.decompose.qrpay

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.QrPayViewModel

class QrPayComponentImpl(
	componentContext: ComponentContext,
) : QrPayComponent, ComponentContext by componentContext {
	override val qrPayViewModel: QrPayViewModel
		get() = instanceKeeper.getOrCreate { QrPayViewModel() }
}
