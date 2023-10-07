package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DigitsInput(
	modifier: Modifier = Modifier,
	value: String,
	length: Int = 4,
	digitsHorizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
	onDigitsChange: (value: String, isFilled: Boolean) -> Unit,
) {
	val focusRequester = remember { FocusRequester() }
	val keyboardController = LocalSoftwareKeyboardController.current
	val isInputFocused = remember { mutableStateOf(false) }
	
	BasicTextField(
		modifier = modifier
			.focusRequester(focusRequester)
			.focusable(true)
			.onFocusChanged {
				isInputFocused.value = it.hasFocus
			}
			.fillMaxWidth(),
		value = value,
		onValueChange = {
			if (it.length <= length) {
				if (it.length == length) {
					focusRequester.freeFocus()
					keyboardController?.hide()
				}
				onDigitsChange(it, it.length == length)
			}
		},
		keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
		decorationBox = {
			Row(
				horizontalArrangement = digitsHorizontalArrangement,
				verticalAlignment = Alignment.CenterVertically
			) {
				repeat(length) { index ->
					val currentChar = value.takeIf { it.length > index }?.get(index)
					DigitView(
						modifier = Modifier
							.weight(1f)
							.padding(horizontal = 4.dp),
						char = "${currentChar ?: ""}",
						hasFocus = value.length == index && isInputFocused.value,
					)
				}
			}
		},
	)
}

@Composable
private fun DigitView(
	modifier: Modifier = Modifier,
	char: String,
	hasFocus: Boolean,
) {
	val padding = remember { mutableStateOf(48) }
	val animatedPadding by animateIntAsState(
		targetValue = padding.value,
		label = "otp-padding-animation",
		animationSpec = TweenSpec(
			durationMillis = 300,
			easing = LinearEasing,
		)
	)
	LaunchedEffect(key1 = hasFocus) {
		if (hasFocus) {
			padding.value = 0
		}
		else {
			padding.value = 48
		}
	}
	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center,
	) {
		Box(
			modifier = Modifier
				.size(40.dp)
				.background(
					color = MaterialTheme.colorScheme.surface,
					shape = RoundedCornerShape(15),
				)
				.border(
					width = 1.dp,
					color = if (hasFocus) {
						Color.Transparent
					}
					else MaterialTheme.colorScheme.onSurfaceVariant,
					shape = RoundedCornerShape(15),
				),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = char,
				style = MaterialTheme.typography.bodyMedium,
				textAlign = TextAlign.Center,
			)
		}
		Box(
			modifier = Modifier
				.size((40.plus(animatedPadding)).dp)
				.border(
					width = if (hasFocus) 2.dp else 1.dp,
					color = if (hasFocus) {
						MaterialTheme.colorScheme.primary
					}
					else Color.Transparent,
					shape = RoundedCornerShape(15),
				),
		)
	}
}
