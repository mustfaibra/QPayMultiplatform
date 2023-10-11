package com.mustfaibra.qpaymultiplatform.decompose.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mustfaibra.qpaymultiplatform.decompose.bottomnavholder.BottomNavComponent
import com.mustfaibra.qpaymultiplatform.decompose.contactsinfo.ContactInfoComponent
import com.mustfaibra.qpaymultiplatform.decompose.createaccount.SignInOptionsComponent
import com.mustfaibra.qpaymultiplatform.decompose.createauth.CreateAuthComponent
import com.mustfaibra.qpaymultiplatform.decompose.identityverification.IdentityVerificationComponent
import com.mustfaibra.qpaymultiplatform.decompose.login.LoginComponent
import com.mustfaibra.qpaymultiplatform.decompose.nationalid.NationalIdComponent
import com.mustfaibra.qpaymultiplatform.decompose.onboarding.OnboardingComponent
import com.mustfaibra.qpaymultiplatform.decompose.phoneverification.PhoneVerificationComponent
import com.mustfaibra.qpaymultiplatform.decompose.splash.SplashComponent
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider
import com.mustfaibra.qpaymultiplatform.viewmodels.RootViewModel
import org.koin.core.KoinApplication

interface QPayRoot {
	val stringProvider: StringsProvider
	val backstack: Value<ChildStack<*, MainDestinationChild>>
	val koinApplication: KoinApplication
	val rootViewModel: RootViewModel
	
	sealed class MainDestinationChild {
		class Splash(
			val component: SplashComponent,
		) : MainDestinationChild()
		
		class Onboarding(
			val component: OnboardingComponent,
		) : MainDestinationChild()
		
		class SignInOptions(
			val component: SignInOptionsComponent
		) : MainDestinationChild()
		
		class Login(
			val component: LoginComponent
		) : MainDestinationChild()
		
		class ContactInfo(
			val component: ContactInfoComponent,
		) : MainDestinationChild()
		
		class PhoneVerification(
			val component: PhoneVerificationComponent,
		) : MainDestinationChild()
		
		class NationalIdCapture(
			val component: NationalIdComponent,
		) : MainDestinationChild()
		
		class IdentifyVerification(
			val component: IdentityVerificationComponent,
		) : MainDestinationChild()
		
		class CreateAuthenticationPin(
			val component: CreateAuthComponent,
		) : MainDestinationChild()
		
		class BottomNavHolder(
			val component: BottomNavComponent,
		) : MainDestinationChild()
	}
}
