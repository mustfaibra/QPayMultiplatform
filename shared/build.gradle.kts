plugins {
	kotlin("multiplatform")
	id("com.android.library")
	id("org.jetbrains.compose")
	kotlin("plugin.serialization")
	id("kotlin-parcelize")
	id("dev.icerock.mobile.multiplatform-resources")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
	android {
		compilations.all {
			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
	}
	
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	).forEach {
		it.binaries.framework {
			baseName = "shared"
			isStatic = true
			export("dev.icerock.moko:graphics:0.9.0")
			export("dev.icerock.moko:resources-compose:0.23.0")
		}
	}
	
	sourceSets {
		val decomposeVersion = "2.0.0"
		val commonMain by getting {
			dependencies {
				//put your multiplatform dependencies here
				api(compose.runtime)
				api(compose.foundation)
				api(compose.material3)
				@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
				api(compose.components.resources)
				
				// Required for Decompose
				api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
				api("com.arkivanov.decompose:decompose:$decomposeVersion-compose-experimental")
				api("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion-compose-experimental")
				
				// Moko resources generator
				api("dev.icerock.moko:resources-compose:0.23.0")
				
				// Koin
				implementation("io.insert-koin:koin-core:3.4.0")
				
				// Kotlinx date & time
				implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
				
				// Multiplatform settings
				implementation("com.russhwolf:multiplatform-settings:1.0.0")
				
				// InsetX for controlling status bar and navigation bar
				implementation("com.moriatsushi.insetsx:insetsx:0.1.0-alpha10")
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
			}
		}
		val androidMain by getting {
			dependsOn(commonMain)
		}
		val iosX64Main by getting
		val iosArm64Main by getting
		val iosSimulatorArm64Main by getting
		val iosMain by creating {
			dependsOn(commonMain)
			iosX64Main.dependsOn(this)
			iosArm64Main.dependsOn(this)
			iosSimulatorArm64Main.dependsOn(this)
		}
		val iosTest by creating {
			dependsOn(commonTest)
		}
	}
}

android {
	defaultConfig {
		configurations.all {
			resolutionStrategy {
				force("androidx.emoji2:emoji2-views-helper:1.3.0")
				force("androidx.emoji2:emoji2:1.3.0")
			}
		}
	}
	namespace = "com.mustfaibra.qpaymultiplatform"
	compileSdk = 33
	defaultConfig {
		minSdk = 24
	}
}

multiplatformResources {
	multiplatformResourcesPackage = "com.mustfaibra.qpaymultiplatform"
	multiplatformResourcesClassName = "SharedRes"
	disableStaticFrameworkWarning = true
}
