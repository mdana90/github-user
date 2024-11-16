// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

extra.apply {
    set("javaVersion", JavaVersion.VERSION_17)
    set("kotlinJvmTarget", "17")
    set("minSdk", 26)
    set("targetSdk", 35)
    set("compileSdk", 35)
}