package com.mustfaibra.qpaymultiplatform.decompose.splash

import com.mustfaibra.qpaymultiplatform.viewmodels.SplashViewModel

interface SplashComponent {
	val splashViewModel: SplashViewModel
	
	fun onSplashTimeFinish(isOnboardedBefore: Boolean)
}
