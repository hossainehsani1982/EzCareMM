plugins {
    id("com.android.application")
    kotlin("android")
    id ("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    compileSdk = ProjectConfig.compileSdk
    namespace = "com.hossain_ehs.ezcaremm"
    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }



    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)
    implementation(AndroidX.constraintlayout)

    //Fragment
    implementation(AndroidX.fragment)

    //swipe to delete
    implementation (SwipeToDelete.swipeToDelete)

    //Shawnlin number picker
    implementation(NumberPicker.shawnlinNumberPicker)

    //lottie
    implementation(Lottie.lottie)

    //glide
    implementation(Glide.glide)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    annotationProcessor(Glide.glideCompiler)


//Modules
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.userData))
    implementation(project(Modules.userDomain))
    implementation(project(Modules.userPresentation))
    implementation(project(Modules.medicationData))
    implementation(project(Modules.medicationDomain))
    implementation(project(Modules.medicationPresentation))


    //compose
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.activityCompose)
    implementation(Compose.hiltNavigationCompose)

    //Dagger
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)
    implementation(DaggerHilt.hiltNavigationFragment)
    annotationProcessor(DaggerHilt.hiltCompiler)

    //dataStore
    implementation(AndroidX.dataStore)


    //Room
    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    //Navigation
    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationUi)

    //lifecycle
    implementation(AndroidX.lifecycleProcess)
    implementation(AndroidX.lifecycleViewModelStat)
    implementation(AndroidX.lifecycleService)
    implementation(AndroidX.lifecycleViewModel)
    implementation(AndroidX.lifecycleLiveData)

    //IBM Calendar
    implementation(IbmCalendar.ibmCalendar)

    //Slide to Act
    implementation(SlideToAct.slideToAct)

    //Testing
    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
}