package com.mustfaibra.qpaymultiplatform.ui

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.moriatsushi.insetsx.WindowInsetsUIViewController
import com.mustfaibra.qpaymultiplatform.decompose.root.QPayRootImpl
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider
import org.koin.core.KoinApplication
import platform.UIKit.UIViewController

fun MainViewController(
	stringProvider: StringsProvider,
	koinApplication: KoinApplication,
) : UIViewController {
	val root = QPayRootImpl(
		componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry()),
		stringProvider = stringProvider,
		koinApplication = koinApplication,
	)
	return WindowInsetsUIViewController {
		QPayApp(
			root = root,
		)
	}
}
