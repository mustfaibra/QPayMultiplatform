package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.PaymentCard
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun StackOfCardsSection(
	modifier: Modifier = Modifier,
	cards: List<PaymentCard>,
	cardsOffset: Float,
) {
	val currentPage = remember { mutableStateOf(cards.lastIndex) }
	val dragOffsetY = remember { mutableStateOf(0f) }
	val animatedDragOffsetY by animateFloatAsState(
		targetValue = dragOffsetY.value,
		animationSpec = TweenSpec(300)
	)
	
	Box(
		modifier = modifier
			.fillMaxWidth()
			.pointerInput(Unit) {
//                detectVerticalDragGestures { _, dragAmount ->
//                    dragOffsetY.value += dragAmount
//                }
			},
		contentAlignment = Alignment.Center,
	) {
		cards.forEachIndexed { index, card ->
			CardItem(
				card = card,
				index = index,
				currentPage = currentPage.value,
				cardsCount = cards.lastIndex,
				currentCardOffsetY = animatedDragOffsetY,
				defaultCardOffsetY = cardsOffset,
			)
		}
	}
}

@Composable
fun CardItem(
	card: PaymentCard,
	index: Int,
	currentPage: Int,
	cardsCount: Int,
	currentCardOffsetY: Float,
	defaultCardOffsetY: Float,
) {
	val stringsProvider = LocalStringProvider.current
		?: throw IllegalStateException("String provider should not be null!")
	
	val animateCards = remember { mutableStateOf(false) }
	val transition = updateTransition(
		targetState = animateCards.value,
		label = "card-transition",
	)
	val animatedOffset by transition.animateFloat(
		targetValueByState = { target ->
			when (target) {
				true -> (cardsCount - index) * defaultCardOffsetY
				false -> cardsCount * defaultCardOffsetY
			}
		},
		label = "animated Offset",
		transitionSpec = {
			TweenSpec(durationMillis = 500, delay = 500)
		},
	)
	val animatedScale by transition.animateFloat(
		targetValueByState = { target ->
			when (target) {
				true -> 1f - (cardsCount - index) * 0.05f
				false -> 1f
			}
		},
		label = "animated Scale",
		transitionSpec = {
			TweenSpec(durationMillis = 500, delay = 500)
		},
	)
	val animatedAlpha by transition.animateFloat(
		targetValueByState = { target ->
			when (target) {
				true -> 1f
				false -> 0.5f
			}
		},
		label = "animated Alpha",
		transitionSpec = {
			TweenSpec(durationMillis = 500)
		},
	)
	val animatedXRotation by transition.animateFloat(
		targetValueByState = { target ->
			when (target) {
				true -> 0f
				false -> -50f
			}
		},
		label = "animated Rotation",
		transitionSpec = {
			TweenSpec(durationMillis = 700)
		},
	)
	
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.graphicsLayer(
				translationY = animatedOffset + if (index == currentPage)
					currentCardOffsetY
				else 0f,
				shadowElevation = with(LocalDensity.current) { 4.dp.toPx() },
				shape = MaterialTheme.shapes.small,
				alpha = animatedAlpha,
				scaleX = animatedScale,
				rotationX = animatedXRotation,
				rotationZ = 0f,
			),
		shape = MaterialTheme.shapes.small,
		colors = CardDefaults.cardColors(
			containerColor = card.color,
			contentColor = contentColorFor(card.color),
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(24.dp),
			verticalArrangement = Arrangement.spacedBy(30.dp),
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Text(
					text = card.cardName,
					style = MaterialTheme.typography.bodyMedium
				)
				Icon(
					painter = painterResource(SharedRes.images.ic_wireless),
					contentDescription = null,
					modifier = Modifier
						.size(24.dp)
						.rotate(90f)
				)
			}
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.Bottom,
			) {
				Column {
					Text(
						text = stringsProvider.get(SharedRes.strings.name),
						style = MaterialTheme.typography.titleSmall,
					)
					Text(
						text = card.cardHolderName,
						style = MaterialTheme.typography.titleLarge,
						maxLines = 1,
					)
				}
				Spacer(modifier = Modifier.weight(1f))
				Text(
					text = card.cardNumber,
					style = MaterialTheme.typography.titleLarge,
				)
			}
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.background(MaterialTheme.colorScheme.surface)
					.padding(horizontal = 12.dp, vertical = 4.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
			) {
				Text(
					text = stringsProvider.get(
						SharedRes.strings.exp,
						listOf("${card.expirationMonth}/${card.expirationYear}")
					),
					style = MaterialTheme.typography.titleMedium
						.copy(fontWeight = FontWeight.Normal),
					color = MaterialTheme.colorScheme.onSurface,
				)
				Icon(
					painter = painterResource(card.icon),
					contentDescription = null,
					modifier = Modifier
						.size(24.dp),
					tint = MaterialTheme.colorScheme.onSurface,
				)
			}
		}
	}
	LaunchedEffect(key1 = animateCards) {
		animateCards.value = true
	}
}
