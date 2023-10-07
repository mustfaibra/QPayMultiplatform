package com.mustfaibra.qpaymultiplatform.data.entity

import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.ImageResource

data class Pocket(
	val id: String,
	val name: String,
	val createdAt: String,
	val updatedAt: String,
	val goalToReach: Double,
	val currentSaving: Double = 0.0,
	val color: Color,
	val icon: ImageResource,
)
