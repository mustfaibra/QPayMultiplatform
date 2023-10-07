package com.mustfaibra.qpaymultiplatform.data.entity

import kotlinx.datetime.Instant

data class Transaction(
	val id: String,
	val createdAt: String,
	val isTransferred: Boolean,
	val value: Double,
	var sender: User? = null,
	var receiver: User? = null,
) {
	val otherDude: User?
		get() = if(isTransferred) receiver else sender
	val date
		get() = Instant.parse(createdAt)
}
