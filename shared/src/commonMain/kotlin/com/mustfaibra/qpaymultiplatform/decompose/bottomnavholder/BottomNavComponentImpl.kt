package com.mustfaibra.qpaymultiplatform.decompose.bottomnavholder

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.decompose.home.HomeComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.pocket.PocketComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.profile.ProfileComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.qrpay.QrPayComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.root.QPayRootImpl
import com.mustfaibra.qpaymultiplatform.decompose.wallet.WalletComponentImpl
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalDecomposeApi::class)
class BottomNavComponentImpl(
	componentContext: ComponentContext,
	private val onNavigateToMainChildRequested: (child: QPayRootImpl.MainNavigationConfig) -> Unit,
) : BottomNavComponent, ComponentContext by componentContext {
	override val configs = listOf(
		BottomNavConfig.Home,
		BottomNavConfig.Wallet,
		BottomNavConfig.QrCodePay,
		BottomNavConfig.Pocket,
		BottomNavConfig.Profile,
	)
	private val mainDispatcher = CoroutineScope(Dispatchers.Main)
	private val navigation = PagesNavigation<BottomNavConfig>()
	private val _pages = this.childPages(
		source = navigation,
		initialPages = {
			Pages(
				items = configs,
				selectedIndex = 0,
			)
		},
		childFactory = ::createChildPageFactor
	)
	override val pages: Value<ChildPages<*, BottomNavComponent.BottomNavChild>>
		get() = _pages
	
	override fun onNewPageSelected(index: Int) {
		mainDispatcher.launch {
			navigation.select(index)
		}
	}
	
	override fun onNavigateToMainChild(child: QPayRootImpl.MainNavigationConfig) {
		onNavigateToMainChildRequested(child)
	}
	
	private fun createChildPageFactor(
		config: BottomNavConfig,
		componentContext: ComponentContext,
	): BottomNavComponent.BottomNavChild {
		return when (config) {
			BottomNavConfig.Home -> BottomNavComponent.BottomNavChild.Home(
				component = buildHomeComponent(
					context = componentContext,
				)
			)
			
			BottomNavConfig.Wallet -> BottomNavComponent.BottomNavChild.Wallet(
				component = buildWalletComponent(
					context = componentContext,
				)
			)
			
			BottomNavConfig.QrCodePay -> BottomNavComponent.BottomNavChild.QrCodePay(
				component = buildQrPayComponent(
					context = componentContext,
				)
			)
			
			BottomNavConfig.Pocket -> BottomNavComponent.BottomNavChild.Pocket(
				component = buildPocketComponent(
					context = componentContext,
				)
			)
			
			BottomNavConfig.Profile -> BottomNavComponent.BottomNavChild.Profile(
				component = buildProfileComponent(
					context = componentContext,
				)
			)
		}
	}
	
	private fun buildHomeComponent(
		context: ComponentContext,
	) = HomeComponentImpl(
		componentContext = context,
		onOpenSendMoney = {
			onNavigateToMainChild(QPayRootImpl.MainNavigationConfig.SendMoney)
		}
	)
	
	private fun buildPocketComponent(
		context: ComponentContext,
	) = PocketComponentImpl(
		componentContext = context,
	)
	
	private fun buildQrPayComponent(
		context: ComponentContext,
	) = QrPayComponentImpl(
		componentContext = context,
	)
	
	private fun buildWalletComponent(
		context: ComponentContext,
	) = WalletComponentImpl(
		componentContext = context,
	)
	
	private fun buildProfileComponent(
		context: ComponentContext,
	) = ProfileComponentImpl(
		componentContext = context,
	)
	
	
	sealed class BottomNavConfig(
		val title: StringResource,
		val icon: ImageResource,
	) : Parcelable {
		@Parcelize
		data object Home : BottomNavConfig(
			title = SharedRes.strings.home,
			icon = SharedRes.images.ic_home,
		)
		
		@Parcelize
		data object Wallet : BottomNavConfig(
			title = SharedRes.strings.wallet,
			icon = SharedRes.images.ic_wallet,
		)
		
		@Parcelize
		data object QrCodePay : BottomNavConfig(
			title = SharedRes.strings.qr_code,
			icon = SharedRes.images.ic_qr_code,
		)
		
		@Parcelize
		data object Pocket : BottomNavConfig(
			title = SharedRes.strings.pocket,
			icon = SharedRes.images.ic_pocket,
		)
		
		@Parcelize
		data object Profile : BottomNavConfig(
			title = SharedRes.strings.profile,
			icon = SharedRes.images.ic_profile,
		)
	}
}
