package com.mustfaibra.qpaymultiplatform.data.repositories

import androidx.compose.ui.graphics.Color
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.PaymentCard
import com.mustfaibra.qpaymultiplatform.data.entity.User
import kotlinx.coroutines.delay
import kotlin.random.Random

object UserRepository {
	private val names = listOf(
		"Mustafa",
		"Ali",
		"Ahmed",
		"Omer",
		"Raj",
		"Selena",
		"Rani",
		"Samir",
		"Elon",
		"Arthur"
	)
	private val me = User(
		id = "user_272627227",
		username = "mustfaibra",
		fName = "Mustafa",
		lName = "Ibrahim",
		token = "fjdfj22820wxw922772ksk",
		balance = 350_000.0,
		profile = User.getRandomProfile(),
		cards = mutableListOf(
			PaymentCard(
				cardName = "ADIB",
				cardNumber = "4532 5687 6543 9876",
				cardHolderName = "Mustafa Ibrahim",
				expirationMonth = Random.nextInt(1, 12),
				expirationYear = Random.nextInt(23, 30),
				cvv = "${Random.nextInt(100, 999)}",
				color = Color(0xFF2196F3),
				icon = SharedRes.images.ic_mastercard,
			),
			PaymentCard(
				cardName = "Mashreq",
				cardNumber = "9876 2345 6789 6011",
				cardHolderName = "Mustafa Ibrahim",
				expirationMonth = Random.nextInt(1, 12),
				expirationYear = Random.nextInt(23, 30),
				cvv = "${Random.nextInt(100, 999)}",
				color = Color(0xFFFF9800),
				icon = SharedRes.images.ic_visa,
			),
			PaymentCard(
				cardName = "QPay",
				cardNumber = "4916 1234 5678 4321",
				cardHolderName = "Mustafa Ibrahim",
				expirationMonth = Random.nextInt(1, 12),
				expirationYear = Random.nextInt(23, 30),
				cvv = "${Random.nextInt(100, 999)}",
				color = Color(0xFF2b66d8),
				icon = SharedRes.images.ic_paypal,
			),
		)
	)
	private val users = mutableListOf<User>().apply {
		repeat(30) {
			val id = Random.nextInt(1000000, Int.MAX_VALUE)
			this.add(
				User(
					id = "$id",
					username = "qpay_user_$id",
					fName = names.random(),
					lName = names.random(),
					phone = "971${Random.nextInt(100000000, 999999999)}",
					profile = User.getRandomProfile(),
				)
			)
		}
	}
	private val contacts = users
	private val beneficiaries = users.slice(0..13)
	
	fun getContacts(): List<User> {
		return contacts
	}
	
	fun getBeneficiaries(): List<User> {
		return beneficiaries
	}
	
	suspend fun getAuthenticatedUser(): User {
		delay(2000)
		return me
	}
	
	suspend fun checkUserAuthentication(
		encryptedPhone: String,
		encryptedPin: String,
	): User {
		delay(2000)
		return me
	}
}
