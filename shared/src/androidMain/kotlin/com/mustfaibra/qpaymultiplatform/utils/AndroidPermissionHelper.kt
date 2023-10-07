package com.mustfaibra.qpaymultiplatform.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.mustfaibra.qpaymultiplatform.utils.permissions.Permission
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionState

internal fun Context.openPage(
	action: String,
	newData: Uri? = null,
	onError: (Exception) -> Unit,
) {
	try {
		val intent = Intent(action).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK
			newData?.let { data = it }
		}
		startActivity(intent)
	} catch (e: Exception) {
		onError(e)
	}
}

internal fun checkPermissions(
	context: Context,
	activity: Lazy<Activity>,
	permissions: List<String>,
): PermissionState {
	permissions.ifEmpty { return PermissionState.GRANTED } // no permissions needed
	val status: List<Int> = permissions.map {
		context.checkSelfPermission(it)
	}
	val isAllGranted: Boolean = status.all { it == PackageManager.PERMISSION_GRANTED }
	if (isAllGranted) return PermissionState.GRANTED
	
	val isAllRequestRationale: Boolean = try {
		permissions.all {
			!activity.value.shouldShowRequestPermissionRationale(it)
		}
	} catch (t: Throwable) {
		t.printStackTrace()
		true
	}
	return if (isAllRequestRationale) PermissionState.UNKNOWN
	else PermissionState.DENIED
}

internal fun Activity.askForPermissions(
	permissions: List<String>,
	onFailure: (Throwable) -> Unit,
) {
	try {
		this.requestPermissions(
			permissions.toTypedArray(),
			45678
		)
	} catch (t: Throwable) {
		onFailure(t)
	}
}

internal fun Context.openAppSettingsPage(permission: Permission) {
	openPage(
		action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
		newData = Uri.parse("package:$packageName"),
		onError = { throw IllegalStateException("Setting page can't be open for permission ${permission.name}") }
	)
}
