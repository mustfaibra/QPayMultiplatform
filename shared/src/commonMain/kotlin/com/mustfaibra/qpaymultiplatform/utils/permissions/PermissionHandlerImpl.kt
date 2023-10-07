package com.mustfaibra.qpaymultiplatform.utils.permissions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class PermissionHandlerImpl : PermissionHandler, KoinComponent {
	override fun getPermissionState(permission: Permission): PermissionState {
		return try {
			val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
			permissionDelegate.getPermissionState()
		} catch (t: Throwable) {
			print(t.stackTraceToString())
			PermissionState.UNKNOWN
		}
	}
	
	override fun checkPermissionFlow(permission: Permission): Flow<PermissionState> {
		return flow {
			while (true) {
				val permissionState = getPermissionState(permission)
				emit(permissionState)
				delay(1000)
			}
		}
	}
	
	override suspend fun askForPermission(
		permission: Permission,
		onGranted: () -> Unit,
		onFailure: () -> Unit,
	) {
		try {
			val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
			getPermissionState(permission = permission).let {
				when (it) {
					PermissionState.GRANTED -> onGranted()
					
					else -> {
						permissionDelegate.askForPermission(
							onGranted = onGranted,
							onFailure = onFailure,
						)
					}
				}
			}
		} catch (t: Throwable) {
			print(t.stackTraceToString())
		}
	}
	
	override fun openSettingForPermission(permission: Permission) {
		try {
			val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
			permissionDelegate.openSettingForPermission()
		} catch (t: Throwable) {
			print(t.stackTraceToString())
		}
	}
}
