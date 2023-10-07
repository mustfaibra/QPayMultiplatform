package com.mustfaibra.qpaymultiplatform.data.entity

import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.ImageResource

data class PaymentCard(
	val cardName: String,
	val cardNumber: String,
	val cardHolderName: String,
	val expirationMonth: Int,
	val expirationYear: Int,
	val cvv: String,
	val color: Color = Color.Transparent,
	val icon: ImageResource,
)
