plugins {
	id("com.android.application")
	kotlin("android")
	id("com.google.devtools.ksp")
	id("com.google.dagger.hilt.android")
}

android {
	namespace = "com.eva.timemanagementapp"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.eva.timemanagementapp"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.0"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}

	androidResources {
		generateLocaleConfig = true
	}
}

ksp {
	arg("room.schemaLocation", "$projectDir/schemas")
	arg("room.incremental", "true")
	arg("room.generateKotlin", "true")
}

dependencies {

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.8.2")
	implementation(platform("androidx.compose:compose-bom:2023.10.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	//navigation
	implementation("androidx.navigation:navigation-compose:2.7.6")
	implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
	//lifecycle runtime compose
	implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
	//hilt
	implementation("com.google.dagger:hilt-android:2.48")
	ksp("com.google.dagger:hilt-android-compiler:2.48")
	//datastore
	implementation("androidx.datastore:datastore-preferences:1.0.0")
	//splash -api
	implementation("androidx.core:core-splashscreen:1.1.0-alpha02")
	//room
	implementation("androidx.room:room-ktx:2.6.1")
	implementation("androidx.room:room-runtime:2.6.1")
	annotationProcessor("androidx.room:room-compiler:2.6.1")
	ksp("androidx.room:room-compiler:2.6.1")
	//downloadable-font
	implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")
	//tests
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	testImplementation(kotlin("test"))
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
	testImplementation("app.cash.turbine:turbine:1.0.0")
}