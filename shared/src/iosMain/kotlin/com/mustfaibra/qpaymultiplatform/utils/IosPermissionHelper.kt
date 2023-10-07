package com.mustfaibra.qpaymultiplatform.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

fun openNSUrl(string: String) {
	val settingsUrl: NSURL = NSURL.URLWithString(string)!!
	if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
		UIApplication.sharedApplication.openURL(settingsUrl)
	}
	else throw IllegalStateException("Setting page can't be open: $string")
}

internal fun openAppSettingsPage() {
	openNSUrl(UIApplicationOpenSettingsURLString)
}

internal fun CoroutineScope.observePermission(
	block: suspend () -> Unit,
): Job = launch {
	while (true) {
		block()
		delay(1000)
	}
}
