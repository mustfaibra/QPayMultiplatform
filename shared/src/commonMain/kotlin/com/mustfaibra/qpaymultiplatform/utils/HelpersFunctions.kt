package com.mustfaibra.qpaymultiplatform.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

@Composable
fun StringResource.get(vararg args: Any): String {
	return LocalStringProvider.current?.get(this, args.asList()) ?: "Default Text"
}

@Composable
fun ImageResource.get(): Painter {
	return painterResource(this)
}

fun Instant.minus(value: Int, unit: DateTimeUnit) = apply {
	minus(
		value = value,
		unit = unit,
		timeZone = TimeZone.currentSystemDefault()
	)
}

fun LocalDateTime.formatToReadableDate(): String {
	return "${this.date} ${
		this.time.toString().replaceAfter(".", "").replace(".", "")
	}"
}

fun validatePhone(phone: String): Boolean {
	return phone.isNotBlank() && phone.length == 12 && phone.startsWith("971")
}

fun validatePin(pin: String): Boolean {
	return pin.isNotBlank() && pin.length == 4
}
