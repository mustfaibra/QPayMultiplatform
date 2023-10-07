package com.mustfaibra.qpaymultiplatform.data.entity

import com.mustfaibra.qpaymultiplatform.SharedRes
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

sealed class ActivityFilter(
	val title: StringResource,
	val from: String? = null,
	val to: String = Clock.System.now().toString(),
) {
	data object LastFiveTrans : ActivityFilter(title = SharedRes.strings.last_5)
	data object Last3Days : ActivityFilter(
		title = SharedRes.strings.last_3_days,
		from = Clock.System.now()
			.minus(3, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object LastWeek : ActivityFilter(
		title = SharedRes.strings.last_week,
		from = Clock.System.now()
			.minus(1, DateTimeUnit.WEEK, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object LastMonth : ActivityFilter(
		title = SharedRes.strings.last_month,
		from = Clock.System.now()
			.minus(1, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object Last3Month : ActivityFilter(
		title = SharedRes.strings.last_3_months,
		from = Clock.System.now()
			.minus(3, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object Last6Month : ActivityFilter(
		title = SharedRes.strings.last_6_months,
		from = Clock.System.now()
			.minus(6, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object Last9Month : ActivityFilter(
		title = SharedRes.strings.last_9_months,
		from = Clock.System.now()
			.minus(9, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
			.toString(),
	)
	
	data object LastYear : ActivityFilter(
		title = SharedRes.strings.last_year,
		from = Clock.System.now()
			.minus(1, DateTimeUnit.YEAR, TimeZone.currentSystemDefault())
			.toString(),
	)
}
