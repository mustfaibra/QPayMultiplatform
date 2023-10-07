package com.mustfaibra.qpaymultiplatform.android

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.mustfaibra.qpaymultiplatform.decompose.root.QPayRootImpl
import com.mustfaibra.qpaymultiplatform.koin.doInitKoinApplication
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider
import com.mustfaibra.qpaymultiplatform.ui.QPayApp
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		val koinApp = doInitKoinApplication(
			listOf(
				module {
					single<Context> { applicationContext }
					single<Activity> { this@MainActivity }
				}
			)
		)
		val root = QPayRootImpl(
			componentContext = defaultComponentContext(),
			stringProvider = StringsProvider(this),
			koinApplication = koinApp,
		)
		setContent {
			QPayApp(root = root)
		}
	}
}
