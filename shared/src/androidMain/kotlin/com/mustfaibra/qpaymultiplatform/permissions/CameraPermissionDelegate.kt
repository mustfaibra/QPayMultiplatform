package com.mustfaibra.qpaymultiplatform.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import com.mustfaibra.qpaymultiplatform.utils.askForPermissions
import com.mustfaibra.qpaymultiplatform.utils.checkPermissions
import com.mustfaibra.qpaymultiplatform.utils.openAppSettingsPage
import com.mustfaibra.qpaymultiplatform.utils.permissions.Permission
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionDelegate
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionState

class CameraPermissionDelegate(
	private val context: Context,
	private val activity: Lazy<Activity>,
) : PermissionDelegate {
	override fun getPermissionState(): PermissionState {
		return checkPermissions(
			context = context,
			activity = activity,
			permissions = listOf(Manifest.permission.CAMERA)
		)
	}
	
	override fun openSettingForPermission() {
		context.openAppSettingsPage(permission = Permission.Camera)
	}
	
	override suspend fun askForPermission(
		onGranted: () -> Unit,
		onFailure: () -> Unit,
	) {
		activity.value.askForPermissions(
			permissions = listOf(Manifest.permission.CAMERA),
			onFailure = { onFailure() },
		)
	}
}
