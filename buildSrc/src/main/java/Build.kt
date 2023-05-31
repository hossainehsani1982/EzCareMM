object Build {
    //"7.0.4"
    private const val androidBuildToolsVersion = "7.4.2"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    //2.38.1
    private const val hiltAndroidGradlePluginVersion = "2.38.1"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val navSafeArgsVersion = "2.5.3"
    const val  navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navSafeArgsVersion"
}