package com.mustfaibra.qpaymultiplatform.data.repositories

import androidx.compose.ui.graphics.Color
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.Pocket
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlin.random.Random

object PocketRepository {
	private val pockets = mutableListOf<Pocket>().apply {
		this.add(
			Pocket(
				id = "672gshjd",
				createdAt = "",
				updatedAt = Clock.System.now()
					.minus(
						Random.nextInt(1, 100),
						DateTimeUnit.DAY,
						TimeZone.currentSystemDefault()
					).toString(),
				goalToReach = 1000_000.0,
				currentSaving = 120_000.0,
				name = "House",
				color = Color(0xFF4CAF50),
				icon = SharedRes.images.ic_house,
			)
		)
		this.add(
			Pocket(
				id = "xsadjw2",
				createdAt = "",
				updatedAt = Clock.System.now()
					.minus(
						Random.nextInt(1, 100),
						DateTimeUnit.DAY,
						TimeZone.currentSystemDefault()
					).toString(),
				goalToReach = 200_000.0,
				currentSaving = 90_000.0,
				name = "Tesla Car",
				color = Color.Red,
				icon = SharedRes.images.ic_tesla,
			)
		)
		this.add(
			Pocket(
				id = "2js92s",
				createdAt = "",
				updatedAt = Clock.System.now()
					.minus(
						Random.nextInt(1, 100),
						DateTimeUnit.DAY,
						TimeZone.currentSystemDefault()
					).toString(),
				goalToReach = 5_000.0,
				currentSaving = 1_000.0,
				name = "Mac Pro M3",
				color = Color(0xFF000000),
				icon = SharedRes.images.ic_mac,
			)
		)
		this.add(
			Pocket(
				id = "32adw3",
				createdAt = "",
				updatedAt = Clock.System.now()
					.minus(
						Random.nextInt(1, 100),
						DateTimeUnit.DAY,
						TimeZone.currentSystemDefault()
					).toString(),
				goalToReach = 4_999.0,
				currentSaving = 2_500.0,
				name = "iPhone 15 Pro Max",
				color = Color(0xFF2F1857),
				icon = SharedRes.images.ic_iphone,
			)
		)
		this.add(
			Pocket(
				id = "df2ksjs",
				createdAt = "",
				updatedAt = Clock.System.now()
					.minus(
						Random.nextInt(1, 100),
						DateTimeUnit.DAY,
						TimeZone.currentSystemDefault()
					).toString(),
				goalToReach = 12_500.0,
				currentSaving = 4_700.0,
				name = "Annual Vacation",
				color = Color(0xFF2196F3),
				icon = SharedRes.images.ic_beach,
			)
		)
	}
	
	suspend fun getPockets(): List<Pocket> {
		delay(500)
		return pockets
	}
}
