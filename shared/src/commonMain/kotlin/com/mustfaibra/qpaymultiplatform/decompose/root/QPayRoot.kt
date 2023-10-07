package com.mustfaibra.qpaymultiplatform.decompose.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mustfaibra.qpaymultiplatform.decompose.contactsinfo.ContactInfoComponent
import com.mustfaibra.qpaymultiplatform.decompose.createaccount.SignInOptionsComponent
import com.mustfaibra.qpaymultiplatform.decompose.createauth.CreateAuthComponent
import com.mustfaibra.qpaymultiplatform.decompose.home.HomeComponent
import com.mustfaibra.qpaymultiplatform.decompose.identityverification.IdentityVerificationComponent
import com.mustfaibra.qpaymultiplatform.decompose.nationalid.NationalIdComponent
import com.mustfaibra.qpaymultiplatform.decompose.onboarding.OnboardingComponent
import com.mustfaibra.qpaymultiplatform.decompose.phoneverification.PhoneVerificationComponent
import com.mustfaibra.qpaymultiplatform.decompose.splash.SplashComponent
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider
import com.mustfaibra.qpaymultiplatform.viewmodels.RootViewModel
import org.koin.core.KoinApplication

interface QPayRoot {
	val stringProvider: StringsProvider
	val backstack: Value<ChildStack<*, DestinationChild>>
	val koinApplication: KoinApplication
	val rootViewModel: RootViewModel
	
	sealed class DestinationChild {
		class Splash(
			val component: SplashComponent,
		) : DestinationChild()
		
		class Onboarding(
			val component: OnboardingComponent,
		) : DestinationChild()
		
		class SignInOptions(
			val component: SignInOptionsComponent
		) : DestinationChild()
		
		class ContactInfo(
			val component: ContactInfoComponent,
		) : DestinationChild()
		
		class PhoneVerification(
			val component: PhoneVerificationComponent,
		) : DestinationChild()
		
		class NationalIdCapture(
			val component: NationalIdComponent,
		) : DestinationChild()
		
		class IdentifyVerification(
			val component: IdentityVerificationComponent,
		) : DestinationChild()
		
		class CreateAuthenticationPin(
			val component: CreateAuthComponent,
		) : DestinationChild()
		
		class Home(
			val component: HomeComponent,
		) : DestinationChild()
		
	}
}
