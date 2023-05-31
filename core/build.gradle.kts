plugins {
    id("com.android.library")
}
apply {
    from("$rootDir/base-module.gradle")
}
android {
    namespace = "com.hossain_ehs.core"
}

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}