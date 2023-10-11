package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mustfaibra.qpaymultiplatform.decompose.bottomnavholder.BottomNavComponent
import com.mustfaibra.qpaymultiplatform.decompose.root.QPayRootImpl
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayBottomBar

@OptIn(ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun BottomNavPage(
	bottomNavComponent: BottomNavComponent,
	onNavigateToMainChild: (child: QPayRootImpl.MainNavigationConfig) -> Unit,
) {
	val configs = bottomNavComponent.configs
	val pages by bottomNavComponent.pages.subscribeAsState()
	val currentPageIndex = pages.selectedIndex
	val pagerState = rememberPagerState(0, pageCount = { configs.size })
	
	LaunchedEffect(currentPageIndex){
		pagerState.scrollToPage(currentPageIndex)
	}
	
	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		HorizontalPager(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
				.background(MaterialTheme.colorScheme.background),
			state = pagerState,
			userScrollEnabled = false,
		) {
			when (val page = pages.items[it].instance) {
				is BottomNavComponent.BottomNavChild.Home -> {
					HomePage(
						viewModel = page.component.homeViewModel,
						onNavigateToSendMoney = {
							onNavigateToMainChild(QPayRootImpl.MainNavigationConfig.SendMoney)
						}
					)
				}
				
				is BottomNavComponent.BottomNavChild.Pocket -> {
					PocketPage(
						viewModel = page.component.pocketViewModel,
					)
				}
				
				is BottomNavComponent.BottomNavChild.Profile -> {
					ProfilePage(
						viewModel = page.component.profileViewModel,
					)
				}
				
				is BottomNavComponent.BottomNavChild.QrCodePay -> {
					QrPayPage(
						viewModel = page.component.qrPayViewModel,
						onCancelScanningQr = {
							bottomNavComponent.onNewPageSelected(
								index = 0,
							)
						}
					)
				}
				
				is BottomNavComponent.BottomNavChild.Wallet -> {
					WalletPage(
						viewModel = page.component.walletViewModel,
					)
				}
				
				else -> {}
			}
		}
//		Pages(
//			modifier = Modifier
//				.fillMaxWidth()
//				.weight(1f),
//			pages = pages,
//			onPageSelected = {
//				bottomNavComponent.onNewPageSelected(index = it)
//			},
//		) { _, page ->
//			when (page) {
//				is BottomNavComponent.BottomNavChild.Home -> {
//					HomePage(
//						viewModel = page.component.homeViewModel,
//						onNavigateToSendMoney = {
//							onNavigateToMainChild(QPayRootImpl.MainNavigationConfig.SendMoney)
//						}
//					)
//				}
//
//				is BottomNavComponent.BottomNavChild.Pocket -> {
//					PocketPage(
//						viewModel = page.component.pocketViewModel,
//					)
//				}
//
//				is BottomNavComponent.BottomNavChild.Profile -> {
//					ProfilePage(
//						viewModel = page.component.profileViewModel,
//					)
//				}
//
//				is BottomNavComponent.BottomNavChild.QrCodePay -> {
//					QrPayPage(
//						viewModel = page.component.qrPayViewModel,
//						onCancelScanningQr = {
//							bottomNavComponent.onNewPageSelected(
//								index = 0,
//							)
//						}
//					)
//				}
//
//				is BottomNavComponent.BottomNavChild.Wallet -> {
//					WalletPage(
//						viewModel = page.component.walletViewModel,
//					)
//				}
//			}
//		}
		QPayBottomBar(
			pages = configs,
			current = configs[currentPageIndex],
		) {
			bottomNavComponent.onNewPageSelected(index = it)
		}
	}
}
