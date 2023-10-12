
# Quick Pay


Quick Pay - QPay for short - is a simple use case for a Fintech app for Onboarding new customers and providing Services, built entirely with Compose Multiplatform. This cross-platform project is considered a use case for unifying & providing financial services on both Android and iOS platforms while leveraging the power of Native.


## Targeted Platforms For Now

- Android Platform
- iOS Platform


## Screenshots
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/onboarding.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/signinoptions.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/allowidcapture.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/confirmphoto.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/login.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/home.png" width="200">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/wallet.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/qrpage.png" width="200px">
<img src="https://github.com/mustfaibra/QPayMultiplatform/blob/master/screenshots/pocket.png" width="200px">

## Tech & Libs Used Until Now

The project used the following:

- Kotlin Programming Language
- [Compose Mutliplatform](https://github.com/JetBrains/compose-multiplatform) Framework
- [Decompose](https://arkivanov.github.io/Decompose/) for State's Management and Navigation, thanks [Arkadii](https://github.com/arkivanov)
    - Stack navigation for the main navigation system.
    - Pages Navigation for the Bottom Nav.
- [Moko Resources](https://github.com/icerockdev/moko-resources) for sharing resources & handling painters on both platforms.
- [Koin](https://insert-koin.io) for handling dependency Injection on Mutliplatform.
- [Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime) for Mutliplatform
- [InsetX](https://github.com/mori-atsushi/insetsx) for handling windows insets - status bar and navigation bar - on both platforms.


## Resources & Projects Until Now

Here are some projects that I found helpful when building this project:

- Moko Sharing resources [Demo](https://github.com/philipplackner/KMM-SharingResources/tree/master) by the great Phillip Lackner.
- [Music App](https://github.com/SEAbdulbasit/MusicApp-KMP/tree/master) by Abdulbasit, thank you so much!


## UI Credits

The great app UI design is from [QPay](https://www.behance.net/gallery/174390161/QPay) on Behance, by the talented Designer [Alaa](https://www.behance.net/alaaabdalrhman).


## What's Next?
The remaining screens appears on the app UI design on the shared Behance link on the credit section is built and ready, only waiting for some clean up.

The following features is coming, feel free to contribute!

 - The Profile and other remaining screens.
 - Camera For Capturing ID and Identity Verification : Currently the app is asking for Camera permission successfully on both platforms, but the camera feature itself is not implemented yet.
 - QR Code Scanner to support Scan To Send Money for both Android & iOS.
 - Building an Encryption Handler that work on both platforms, as there is no efficient one till now(AFAIK).
 - Implementing Lottie Animations Component for both platforms.

## Final words!

I really hope you find this repository helpful, if so, don't forget to give a star ⭐️!

You can also follow me on 
- [LinkedIn](https://www.linkedin.com/in/mustfaibra/)
- [Twitter](https://twitter.com/mustfaibra)
