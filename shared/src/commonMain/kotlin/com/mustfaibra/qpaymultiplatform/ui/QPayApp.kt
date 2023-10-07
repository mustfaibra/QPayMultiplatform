package com.mustfaibra.qpaymultiplatform.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.moriatsushi.insetsx.SystemBarsBehavior
import com.moriatsushi.insetsx.rememberWindowInsetsController
import com.mustfaibra.qpaymultiplatform.data.entity.LocalUser
import com.mustfaibra.qpaymultiplatform.decompose.root.QPayRoot
import com.mustfaibra.qpaymultiplatform.koin.LocalKoinApplication
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import com.mustfaibra.qpaymultiplatform.ui.screens.ContactPage
import com.mustfaibra.qpaymultiplatform.ui.screens.CreateAuthenticationPage
import com.mustfaibra.qpaymultiplatform.ui.screens.HomePage
import com.mustfaibra.qpaymultiplatform.ui.screens.IdentityVerificationPage
import com.mustfaibra.qpaymultiplatform.ui.screens.NationalIDCapturePage
import com.mustfaibra.qpaymultiplatform.ui.screens.OnboardingPage
import com.mustfaibra.qpaymultiplatform.ui.screens.PhoneVerificationPage
import com.mustfaibra.qpaymultiplatform.ui.screens.SignInOptionsPage
import com.mustfaibra.qpaymultiplatform.ui.screens.SplashPage
import com.mustfaibra.qpaymultiplatform.ui.theme.CommonQPayTheme

@Composable
fun QPayApp(
	root: QPayRoot,
) {
	val windowsInsets = rememberWindowInsetsController()
	val user by root.rootViewModel.user.collectAsState()
	
	LaunchedEffect(Unit){
		windowsInsets?.setIsNavigationBarsVisible(false)
		windowsInsets?.setIsStatusBarsVisible(false)
		windowsInsets?.setSystemBarsBehavior(SystemBarsBehavior.Immersive)
	}
	
	CommonQPayTheme {
		CompositionLocalProvider(
			LocalStringProvider provides root.stringProvider,
			LocalKoinApplication provides root.koinApplication,
			LocalUser provides user,
		) {
			val stack by root.backstack.subscribeAsState()
			val current = stack.active.instance
			
			Column(
				modifier = Modifier.fillMaxSize()
					.background(MaterialTheme.colorScheme.surface),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Children(
					stack = stack,
					modifier = Modifier.weight(1f),
				) { childCreated ->
					when (val child = childCreated.instance) {
						is QPayRoot.DestinationChild.Splash -> {
							SplashPage(
								viewModel = child.component.splashViewModel,
								onSplashFinished = { onboardedBefore ->
									child.component.onSplashTimeFinish(
										isOnboardedBefore = onboardedBefore,
									)
								}
							)
						}
						
						is QPayRoot.DestinationChild.Onboarding -> {
							OnboardingPage(
								onGetStarted = {
									child.component.onOnboarded()
								}
							)
						}
						
						is QPayRoot.DestinationChild.SignInOptions -> {
							SignInOptionsPage(
								onCreateAccount = {
									child.component.onCreateAccountClicked()
								},
								onSignToQpayAccount = {
									child.component.onSignInToAccountClicked()
								}
							)
						}
						
						is QPayRoot.DestinationChild.ContactInfo -> {
							ContactPage(
								viewModel = child.component.contactsViewModel,
							) {
								child.component.onOtpSent()
							}
						}
						
						is QPayRoot.DestinationChild.PhoneVerification -> {
							PhoneVerificationPage(
								viewModel = child.component.verificationViewModel,
							) {
								child.component.onVerificationCompleted()
							}
						}
						
						is QPayRoot.DestinationChild.NationalIdCapture -> {
							NationalIDCapturePage(
								viewModel = child.component.nationalIdViewModel,
							) { front, back ->
								child.component.onCaptured(front, back)
							}
						}
						
						is QPayRoot.DestinationChild.IdentifyVerification -> {
							IdentityVerificationPage(
								viewModel = child.component.identityVerificationViewModel,
								onStatusBarColorChangeRequest = {
									child.component.onChangeStatusBarColor(it)
								},
								onIdentityVerified = {
									child.component.onIdentityVerified()
								}
							)
						}
						
						is QPayRoot.DestinationChild.CreateAuthenticationPin -> {
							CreateAuthenticationPage(
								viewModel = child.component.createAuthenticateViewModel,
								onAuthenticationCreated = {
									child.component.onAuthPinCreated(it)
								},
							)
						}
						
						is QPayRoot.DestinationChild.Home -> {
							HomePage(
								viewModel = child.component.homeViewModel,
								onNavigateToSendMoney = {
								
								}
							)
						}
					}
				}
			}
		}
	}
}
