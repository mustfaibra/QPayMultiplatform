package com.mustfaibra.qpaymultiplatform.utils.permissions

interface PermissionDelegate {
	fun getPermissionState(): PermissionState
	fun openSettingForPermission()
	suspend fun askForPermission(
		onGranted: () -> Unit,
		onFailure: () -> Unit,
	)
}
