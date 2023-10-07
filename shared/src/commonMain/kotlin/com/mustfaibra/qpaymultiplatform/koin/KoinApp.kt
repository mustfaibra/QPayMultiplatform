package com.mustfaibra.qpaymultiplatform.koin

import androidx.compose.runtime.compositionLocalOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun doInitKoinApplication(
	platformModules: List<Module> = listOf(),
): KoinApplication {
	val koinApp = koinApplication {
		modules(
			listOf(
				module { includes(platformModules) },
				permissionModule,
			)
		)
		createEagerInstances()
	}
	return startKoin(koinApp)
}

val LocalKoinApplication = compositionLocalOf {
	doInitKoinApplication()
}
