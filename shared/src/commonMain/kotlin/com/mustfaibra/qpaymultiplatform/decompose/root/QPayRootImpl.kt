package com.mustfaibra.qpaymultiplatform.decompose.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mustfaibra.qpaymultiplatform.decompose.bottomnavholder.BottomNavComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.contactsinfo.ContactInfoComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.createaccount.SignInOptionsComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.createauth.CreateAuthComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.identityverification.IdentityVerificationComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.login.LoginComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.nationalid.NationalIdComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.onboarding.OnboardingComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.phoneverification.PhoneVerificationComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.splash.SplashComponentImpl
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider
import com.mustfaibra.qpaymultiplatform.viewmodels.RootViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication

class QPayRootImpl(
	val componentContext: ComponentContext,
	override val stringProvider: StringsProvider,
	override val koinApplication: KoinApplication,
) : QPayRoot, ComponentContext by componentContext {
	private val mainDispatcher = CoroutineScope(Dispatchers.Main)
	override val rootViewModel: RootViewModel
		get() = instanceKeeper.getOrCreate { RootViewModel() }
	
	private val navigation = StackNavigation<MainNavigationConfig>()
	private val _backstack = this.childStack(
		source = navigation,
		initialConfiguration = MainNavigationConfig.Splash,
		handleBackButton = true,
	) { config, context ->
		createChildFactory(
			config = config, componentContext = context
		)
	}
	override val backstack = _backstack
	
	private fun createChildFactory(
		config: MainNavigationConfig,
		componentContext: ComponentContext,
	): QPayRoot.MainDestinationChild {
		return when (config) {
			is MainNavigationConfig.Splash -> QPayRoot.MainDestinationChild.Splash(
				component = buildSplashComponent(context = componentContext)
			)
			
			is MainNavigationConfig.Onboarding -> QPayRoot.MainDestinationChild.Onboarding(
				component = buildOnboardingComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.SignInOptions -> QPayRoot.MainDestinationChild.SignInOptions(
				component = buildSignInOptionsComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.Login -> QPayRoot.MainDestinationChild.Login(
				component = buildLoginComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.ContactPage -> QPayRoot.MainDestinationChild.ContactInfo(
				component = buildContactInfoComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.PhoneVerification -> QPayRoot.MainDestinationChild.PhoneVerification(
				component = buildPhoneVerificationComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.NationalIDCapture -> QPayRoot.MainDestinationChild.NationalIdCapture(
				component = buildNationalIDComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.IdentityVerification -> QPayRoot.MainDestinationChild.IdentifyVerification(
				component = buildIdentityVerificationComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.CreateAuthentication -> QPayRoot.MainDestinationChild.CreateAuthenticationPin(
				component = buildCreateAuthComponent(
					context = componentContext,
				)
			)
			
			is MainNavigationConfig.BottomNavHolder -> QPayRoot.MainDestinationChild.BottomNavHolder(
				component = buildBottomNavComponent(
					context = componentContext,
				)
			)
			
			else -> TODO()
		}
	}
	
	private fun buildSplashComponent(
		context: ComponentContext
	) = SplashComponentImpl(componentContext = context,
		onSplashTimeFinished = { isOnboardedBefore ->
			mainDispatcher.launch {
				if (isOnboardedBefore) {
					navigation.replaceCurrent(configuration = MainNavigationConfig.SignInOptions)
				}
				else {
					navigation.replaceCurrent(configuration = MainNavigationConfig.Onboarding)
				}
			}
		})
	
	private fun buildOnboardingComponent(
		context: ComponentContext,
	) = OnboardingComponentImpl(componentContext = context, onOnboardingFinished = {
		mainDispatcher.launch {
			navigation.replaceCurrent(MainNavigationConfig.SignInOptions)
		}
	})
	
	private fun buildSignInOptionsComponent(
		context: ComponentContext,
	) = SignInOptionsComponentImpl(
		componentContext = context,
		onCreateAccount = {
			mainDispatcher.launch {
				navigation.push(MainNavigationConfig.ContactPage)
			}
		},
		onSignInToAccount = {
			mainDispatcher.launch {
				navigation.push(MainNavigationConfig.Login)
			}
		},
	)
	
	private fun buildLoginComponent(
		context: ComponentContext,
	) = LoginComponentImpl(
		componentContext = context,
		onAuthenticated = { user, rememberMe ->
			mainDispatcher.launch {
				rootViewModel.updateLoggedUser(user = user)
				navigation.replaceAll(MainNavigationConfig.BottomNavHolder)
			}
		},
	)
	
	private fun buildContactInfoComponent(
		context: ComponentContext,
	) = ContactInfoComponentImpl(componentContext = context, onOtpSentToUser = {
		mainDispatcher.launch {
			navigation.push(MainNavigationConfig.PhoneVerification)
		}
	})
	
	private fun buildPhoneVerificationComponent(
		context: ComponentContext,
	) = PhoneVerificationComponentImpl(componentContext = context,
		onVerifiedSuccessfully = {
			mainDispatcher.launch {
				navigation.push(MainNavigationConfig.NationalIDCapture)
			}
		})
	
	private fun buildNationalIDComponent(
		context: ComponentContext,
	) = NationalIdComponentImpl(componentContext = context,
		onNationalIdCaptured = { front, back ->
			mainDispatcher.launch {
				navigation.push(MainNavigationConfig.IdentityVerification)
			}
		})
	
	private fun buildIdentityVerificationComponent(
		context: ComponentContext,
	) = IdentityVerificationComponentImpl(componentContext = context,
		onStatusBarColorChange = {},
		onIdentityVerifiedSuccessfully = {
			mainDispatcher.launch {
				navigation.push(MainNavigationConfig.CreateAuthentication)
			}
		})
	
	private fun buildCreateAuthComponent(
		context: ComponentContext,
	) = CreateAuthComponentImpl(componentContext = context,
		onAuthenticationCreated = { user ->
			mainDispatcher.launch {
				rootViewModel.updateLoggedUser(user = user)
				navigation.replaceAll(MainNavigationConfig.BottomNavHolder)
			}
		})
	
	private fun buildBottomNavComponent(
		context: ComponentContext,
	) = BottomNavComponentImpl(
		componentContext = context,
		onNavigateToMainChildRequested = { config ->
			mainDispatcher.launch {
				navigation.push(config)
			}
		},
	)
	
	sealed class MainNavigationConfig : Parcelable {
		@Parcelize
		data object Splash : MainNavigationConfig()
		
		@Parcelize
		data object Onboarding : MainNavigationConfig()
		
		@Parcelize
		data object SignInOptions : MainNavigationConfig()
		
		@Parcelize
		data object Login : MainNavigationConfig()
		
		@Parcelize
		data object ContactPage : MainNavigationConfig()
		
		@Parcelize
		data object PhoneVerification : MainNavigationConfig()
		
		@Parcelize
		data object NationalIDCapture : MainNavigationConfig()
		
		@Parcelize
		data object IdentityVerification : MainNavigationConfig()
		
		@Parcelize
		data object CreateAuthentication : MainNavigationConfig()
		
		@Parcelize
		data object BottomNavHolder : MainNavigationConfig()
		
		@Parcelize
		data object SendMoney : MainNavigationConfig()
	}
}
