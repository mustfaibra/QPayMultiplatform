package com.mustfaibra.qpaymultiplatform.utils.permissions

import kotlinx.coroutines.flow.Flow

interface PermissionHandler {
	fun getPermissionState(permission: Permission): PermissionState
	fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
	fun openSettingForPermission(permission: Permission)
	suspend fun askForPermission(
		permission: Permission,
		onGranted: () -> Unit,
		onFailure: () -> Unit,
	)
}
