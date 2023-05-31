plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply{
    from("$rootDir/base-ui-module.gradle")
}
android {
    namespace = "com.hossain_ehs.medication_presentation"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.medicationDomain))
}