package com.mustfaibra.qpaymultiplatform.decompose.nationalid

import com.mustfaibra.qpaymultiplatform.viewmodels.NationalIdViewModel

interface NationalIdComponent {
	val nationalIdViewModel: NationalIdViewModel
	
	fun onCaptured(front: String, back: String)
}
