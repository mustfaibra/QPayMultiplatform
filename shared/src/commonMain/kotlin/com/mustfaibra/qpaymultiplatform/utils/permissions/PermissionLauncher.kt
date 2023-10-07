package com.mustfaibra.qpaymultiplatform.utils.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.mustfaibra.qpaymultiplatform.koin.LocalKoinApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PermissionLauncher {
	private lateinit var permissionHandler: PermissionHandlerImpl
	private lateinit var requestedPermission: Permission
	private lateinit var scope: CoroutineScope
	
	@Composable
	fun createLaunchRequest(
		permission: Permission,
	): PermissionLauncher {
		scope = rememberCoroutineScope()
		val koinApplication = LocalKoinApplication.current
		permissionHandler = koinApplication.koin.get()
		requestedPermission = permission
		return this
	}
	
	fun launch(
		onGranted: () -> Unit,
		onDenied: () -> Unit,
	) {
		scope.launch {
			permissionHandler.askForPermission(
				permission = requestedPermission,
				onGranted = onGranted,
				onFailure = onDenied,
			)
		}
	}
}
