package com.mustfaibra.qpaymultiplatform.decompose.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mustfaibra.qpaymultiplatform.viewmodels.OnboardingViewModel

class OnboardingComponentImpl(
	componentContext: ComponentContext,
	val onOnboardingFinished: () -> Unit,
) : OnboardingComponent, ComponentContext by componentContext {
	override val onboardingViewModel: OnboardingViewModel
		get() = instanceKeeper.getOrCreate { OnboardingViewModel() }
	
	override fun onOnboarded() {
		onOnboardingFinished()
	}
}
