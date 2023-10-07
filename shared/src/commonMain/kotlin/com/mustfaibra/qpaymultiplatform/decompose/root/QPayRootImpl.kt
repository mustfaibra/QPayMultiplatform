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
import com.mustfaibra.qpaymultiplatform.decompose.contactsinfo.ContactInfoComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.createaccount.SignInOptionsComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.createauth.CreateAuthComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.home.HomeComponentImpl
import com.mustfaibra.qpaymultiplatform.decompose.identityverification.IdentityVerificationComponentImpl
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
	
	private val navigation = StackNavigation<Config>()
	private val _backstack = this.childStack(
		source = navigation,
		initialConfiguration = Config.Splash,
		handleBackButton = true,
	) { config, context ->
		createChildFactory(
			config = config,
			componentContext = context
		)
	}
	override val backstack = _backstack
	
	private fun createChildFactory(
		config: Config,
		componentContext: ComponentContext,
	): QPayRoot.DestinationChild {
		return when (config) {
			is Config.Splash -> QPayRoot.DestinationChild.Splash(
				component = buildSplashComponent(context = componentContext)
			)
			
			is Config.Onboarding -> QPayRoot.DestinationChild.Onboarding(
				component = buildOnboardingComponent(
					context = componentContext,
				)
			)
			
			is Config.SignInOptions -> QPayRoot.DestinationChild.SignInOptions(
				component = buildSignInOptionsComponent(
					context = componentContext,
				)
			)
			
			is Config.ContactPage -> QPayRoot.DestinationChild.ContactInfo(
				component = buildContactInfoComponent(
					context = componentContext,
				)
			)
			
			is Config.PhoneVerification -> QPayRoot.DestinationChild.PhoneVerification(
				component = buildPhoneVerificationComponent(
					context = componentContext,
				)
			)
			
			is Config.NationalIDCapture -> QPayRoot.DestinationChild.NationalIdCapture(
				component = buildNationalIDComponent(
					context = componentContext,
				)
			)
			
			is Config.IdentityVerification -> QPayRoot.DestinationChild.IdentifyVerification(
				component = buildIdentityVerificationComponent(
					context = componentContext,
				)
			)
			
			is Config.CreateAuthentication -> QPayRoot.DestinationChild.CreateAuthenticationPin(
				component = buildCreateAuthComponent(
					context = componentContext,
				)
			)
			
			is Config.Home -> QPayRoot.DestinationChild.Home(
				component = buildHomeComponent(
					context = componentContext,
				)
			)
			
			else -> TODO()
		}
	}
	
	private fun buildSplashComponent(
		context: ComponentContext
	) = SplashComponentImpl(
		componentContext = context,
		onSplashTimeFinished = { isOnboardedBefore ->
			mainDispatcher.launch {
				if (isOnboardedBefore) {
					navigation.replaceCurrent(configuration = Config.SignInOptions)
				}
				else {
					navigation.replaceCurrent(configuration = Config.Onboarding)
				}
			}
		}
	)
	
	private fun buildOnboardingComponent(
		context: ComponentContext,
	) = OnboardingComponentImpl(
		componentContext = context,
		onOnboardingFinished = {
			mainDispatcher.launch {
				navigation.replaceCurrent(Config.SignInOptions)
			}
		}
	)
	
	private fun buildSignInOptionsComponent(
		context: ComponentContext,
	) = SignInOptionsComponentImpl(
		componentContext = context,
		onCreateAccount = {
			mainDispatcher.launch {
				navigation.push(Config.ContactPage)
			}
		},
		onSignInToAccount = {},
	)
	
	private fun buildContactInfoComponent(
		context: ComponentContext,
	) = ContactInfoComponentImpl(
		componentContext = context,
		onOtpSentToUser = {
			mainDispatcher.launch {
				navigation.push(Config.PhoneVerification)
			}
		}
	)
	
	private fun buildPhoneVerificationComponent(
		context: ComponentContext,
	) = PhoneVerificationComponentImpl(
		componentContext = context,
		onVerifiedSuccessfully = {
			mainDispatcher.launch {
				navigation.push(Config.NationalIDCapture)
			}
		}
	)
	
	private fun buildNationalIDComponent(
		context: ComponentContext,
	) = NationalIdComponentImpl(
		componentContext = context,
		onNationalIdCaptured = { front, back ->
			mainDispatcher.launch {
				navigation.push(Config.IdentityVerification)
			}
		}
	)
	
	private fun buildIdentityVerificationComponent(
		context: ComponentContext,
	) = IdentityVerificationComponentImpl(
		componentContext = context,
		onStatusBarColorChange = {},
		onIdentityVerifiedSuccessfully = {
			mainDispatcher.launch {
				navigation.push(Config.CreateAuthentication)
			}
		}
	)
	
	private fun buildCreateAuthComponent(
		context: ComponentContext,
	) = CreateAuthComponentImpl(
		componentContext = context,
		onAuthenticationCreated = { user ->
			mainDispatcher.launch {
				rootViewModel.updateLoggedUser(user = user)
				navigation.replaceAll(Config.Home)
			}
		}
	)
	
	private fun buildHomeComponent(
		context: ComponentContext,
	) = HomeComponentImpl(
		componentContext = context,
		onOpenSendMoney = {
			mainDispatcher.launch {
				navigation.push(Config.SendMoney)
			}
		}
	)
	
	sealed class Config : Parcelable {
		@Parcelize
		data object Splash : Config()
		
		@Parcelize
		data object Onboarding : Config()
		
		@Parcelize
		data object SignInOptions : Config()
		
		@Parcelize
		data object ContactPage : Config()
		
		@Parcelize
		data object PhoneVerification : Config()
		
		@Parcelize
		data object NationalIDCapture : Config()
		
		@Parcelize
		data object IdentityVerification : Config()
		
		@Parcelize
		data object CreateAuthentication : Config()
		
		@Parcelize
		data object Home : Config()
		
		@Parcelize
		data object Wallet : Config()
		
		@Parcelize
		data object QrCodePay : Config()
		
		@Parcelize
		data object Pocket : Config()
		
		@Parcelize
		data object Profile : Config()
		
		@Parcelize
		data object SendMoney : Config()
	}
}
