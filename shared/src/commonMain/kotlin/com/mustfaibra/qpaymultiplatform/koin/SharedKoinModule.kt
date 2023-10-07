package com.mustfaibra.qpaymultiplatform.koin

import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionHandlerImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun getPlatformModule(): Module

val permissionModule = module {
	includes(getPlatformModule())
	
	single<PermissionHandlerImpl> {
		PermissionHandlerImpl()
	}
}
