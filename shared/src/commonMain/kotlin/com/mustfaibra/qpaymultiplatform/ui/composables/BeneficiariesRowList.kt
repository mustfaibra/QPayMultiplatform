package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BeneficiariesRowList(
	modifier: Modifier = Modifier,
	beneficiaries: List<User>,
) {
	val stringsProvider = LocalStringProvider.current
		?: throw IllegalStateException("String provider should not be null!")
	
	SectionWithHeader(
		modifier = modifier
			.clip(MaterialTheme.shapes.large)
			.background(MaterialTheme.colorScheme.surface)
			.padding(16.dp),
		title = stringsProvider.get(SharedRes.strings.quick_pay),
		optionName = stringsProvider.get(SharedRes.strings.see_all),
		content = {
			LazyRow(
				modifier = Modifier.fillMaxWidth(),
				contentPadding = PaddingValues(end = 16.dp),
				horizontalArrangement = Arrangement.spacedBy(20.dp)
			) {
				item {
					Column(
						horizontalAlignment = Alignment.CenterHorizontally,
					) {
						Box(
							modifier = Modifier
								.size(50.dp)
								.clip(CircleShape)
								.background(
									color = MaterialTheme.colorScheme.inversePrimary.copy(
										alpha = 0.1f
									)
								)
								.clickable {
								
								},
							contentAlignment = Alignment.Center,
						) {
							Icon(
								painter = painterResource(SharedRes.images.ic_add),
								contentDescription = null,
								modifier = Modifier.size(24.dp),
								tint = MaterialTheme.colorScheme.primary,
							)
						}
						Spacer(modifier = Modifier.height(12.dp))
						Text(
							text = "Add",
							style = MaterialTheme.typography.titleSmall
								.copy(fontWeight = FontWeight.Medium),
						)
					}
				}
				items(beneficiaries) { user ->
					ProfileWithName(
						name = user.fName,
						profile = painterResource(user.profile),
					) {
					
					}
				}
			}
		},
		onSeeAll = {
		
		},
	)
}

@Composable
fun ProfileWithName(
	modifier: Modifier = Modifier,
	name: String,
	profile: Painter,
	onProfileClicked: () -> Unit,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		QPayPicture(
			image = profile, size = 50.dp, onImageClicked = onProfileClicked
		)
		Spacer(modifier = Modifier.height(12.dp))
		Text(
			text = name,
			style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
		)
	}
}
