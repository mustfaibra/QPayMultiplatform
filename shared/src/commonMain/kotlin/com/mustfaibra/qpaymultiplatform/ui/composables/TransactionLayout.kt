package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.utils.formatToReadableDate
import com.mustfaibra.qpaymultiplatform.utils.get
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TransactionLayout(
	createdAt: String,
	isTransferred: Boolean,
	value: Double,
	user: User,
	onClicked: () -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable {
				onClicked()
			}
			.padding(horizontal = 24.dp, vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		QPayPicture(image = painterResource(user.profile), size = 50.dp) {
		
		}
		Column(
			modifier = Modifier
				.weight(1f),
		) {
			Text(
				text = user.fName,
				style = MaterialTheme.typography.titleLarge
					.copy(fontWeight = FontWeight.Medium),
			)
			Spacer(modifier = Modifier.height(6.dp))
			Text(
				text = Instant.parse(createdAt)
					.toLocalDateTime(TimeZone.currentSystemDefault())
					.formatToReadableDate(),
				style = MaterialTheme.typography.titleSmall
					.copy(fontWeight = FontWeight.Normal),
			)
		}
		Column(
			horizontalAlignment = Alignment.End,
		) {
			Text(
				text = SharedRes.strings.sar_x.get(value),
				style = MaterialTheme.typography.titleMedium
					.copy(fontWeight = FontWeight.Medium),
			)
			Spacer(Modifier.height(4.dp))
			if (isTransferred) {
				Icon(
					painter = painterResource(SharedRes.images.ic_transerred_arrow),
					contentDescription = null,
					modifier = Modifier.size(24.dp),
					tint = MaterialTheme.colorScheme.secondary,
				)
			}
			else {
				Icon(
					painter = painterResource(SharedRes.images.ic_received_arrow),
					contentDescription = null,
					modifier = Modifier.size(24.dp),
					tint = MaterialTheme.colorScheme.primary,
				)
			}
		}
	}
}
