package com.mustfaibra.qpaymultiplatform.data.repositories

import com.mustfaibra.qpaymultiplatform.data.entity.ActivityFilter
import com.mustfaibra.qpaymultiplatform.data.entity.Transaction
import com.mustfaibra.qpaymultiplatform.data.entity.User
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlin.random.Random

object TransactionRepository {
	private val transactions = mutableListOf<Transaction>().apply {
		repeat(10) {
			val instant = Clock.System.now()
				.minus(
					Random.nextInt(1, 100),
					DateTimeUnit.DAY,
					TimeZone.currentSystemDefault()
				)
				.minus(
					Random.nextInt(10, 250),
					DateTimeUnit.MINUTE,
					TimeZone.currentSystemDefault()
				)
			this.add(
				Transaction(
					id = "tranx_${instant.toEpochMilliseconds()}",
					createdAt = instant
						.toString(),
					isTransferred = Random.nextBoolean(),
					value = Random.nextInt(50, 100000).toDouble(),
				)
			)
		}
	}
	
	suspend fun getTransaction(me: User, filter: ActivityFilter): List<Transaction> {
		// First we get the date of today
		return transactions.onEach {
			val randomDude = UserRepository.getBeneficiaries().random()
			it.sender = if (it.isTransferred) me
			else randomDude
			it.receiver = if (it.isTransferred) randomDude
			else me
		}
	}
}
