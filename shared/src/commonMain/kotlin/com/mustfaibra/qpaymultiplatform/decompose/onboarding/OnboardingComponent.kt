package com.mustfaibra.qpaymultiplatform.decompose.onboarding

import com.mustfaibra.qpaymultiplatform.viewmodels.OnboardingViewModel

interface OnboardingComponent {
	val onboardingViewModel: OnboardingViewModel
	
	fun onOnboarded ()
}
