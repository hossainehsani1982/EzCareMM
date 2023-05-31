plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply {
    from("$rootDir/base-module.gradle")
}
android {
    namespace = "com.hossain_ehs.medication_data"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.medicationDomain))
    "implementation"(AndroidX.lifecycleService)
    //"implementation"(AndroidX.lifecycleLiveData)

    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}