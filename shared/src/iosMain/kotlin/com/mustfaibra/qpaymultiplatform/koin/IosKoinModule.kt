package com.mustfaibra.qpaymultiplatform.koin

import com.mustfaibra.qpaymultiplatform.permissions.CameraPermissionDelegate
import com.mustfaibra.qpaymultiplatform.utils.permissions.Permission
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionDelegate
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun getPlatformModule() = module {
	single<PermissionDelegate>(named(Permission.Camera.name)) {
		CameraPermissionDelegate()
	}
}
