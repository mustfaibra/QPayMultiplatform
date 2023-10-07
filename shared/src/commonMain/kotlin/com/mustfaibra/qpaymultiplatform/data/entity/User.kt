package com.mustfaibra.qpaymultiplatform.data.entity

import androidx.compose.runtime.compositionLocalOf
import com.mustfaibra.qpaymultiplatform.SharedRes
import dev.icerock.moko.resources.ImageResource

data class User(
	val id: String,
	val username: String,
	val fName: String,
	val lName: String,
	val phone: String? = null,
	val profile: ImageResource,
	val token: String? = null,
	val balance: Double = 0.0,
	val cards: List<PaymentCard> = listOf(),
) {
	companion object {
		fun getRandomProfile(): ImageResource {
			return listOf(
				SharedRes.images.dreadlocked_man,
				SharedRes.images.fashion_designer,
				SharedRes.images.long_hair_woman,
				SharedRes.images.teacher,
			).random()
		}
	}
}

val LocalUser = compositionLocalOf<User?> { null }
