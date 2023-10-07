package com.mustfaibra.qpaymultiplatform.data.repositories

import androidx.compose.ui.graphics.Color
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.Pocket
import kotlinx.coroutines.delay

object PocketRepository {
	private val pockets = mutableListOf<Pocket>().apply {
		this.add(
			Pocket(
				id = "672gshjd",
				createdAt = "",
				updatedAt = "",
				goalToReach = 1000_000.0,
				currentSaving = 120_000.0,
				name = "House",
				color = Color(0xFF4CAF50),
				icon = SharedRes.images.qpay,
			)
		)
		this.add(
			Pocket(
				id = "xsadjw2",
				createdAt = "",
				updatedAt = "",
				goalToReach = 200_000.0,
				currentSaving = 90_000.0,
				name = "Tesla Car",
				color = Color.Red,
				icon = SharedRes.images.qpay,
			)
		)
		this.add(
			Pocket(
				id = "2js92s",
				createdAt = "",
				updatedAt = "",
				goalToReach = 5_000.0,
				currentSaving = 1_000.0,
				name = "Mac Pro M3",
				color = Color(0xFF000000),
				icon = SharedRes.images.qpay,
			)
		)
		this.add(
			Pocket(
				id = "32adw3",
				createdAt = "",
				updatedAt = "",
				goalToReach = 4_999.0,
				currentSaving = 2_500.0,
				name = "iPhone 15 Pro Max",
				color = Color(0xFF2F1857),
				icon = SharedRes.images.qpay,
			)
		)
		this.add(
			Pocket(
				id = "df2ksjs",
				createdAt = "",
				updatedAt = "",
				goalToReach = 12_500.0,
				currentSaving = 4_700.0,
				name = "Annual Vacation",
				color = Color(0xFF2196F3),
				icon = SharedRes.images.qpay,
			)
		)
	}
	
	suspend fun getPockets(): List<Pocket> {
		delay(500)
		return pockets
	}
}
