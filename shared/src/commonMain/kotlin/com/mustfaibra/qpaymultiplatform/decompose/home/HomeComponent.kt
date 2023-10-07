package com.mustfaibra.qpaymultiplatform.decompose.home

import com.mustfaibra.qpaymultiplatform.viewmodels.HomeViewModel

interface HomeComponent {
	val homeViewModel: HomeViewModel
	
	fun onNavigateToSendMoney()
}
