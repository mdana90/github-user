plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.android.junit5) version libs.versions.androidJUnit5.get()

}

android {
    namespace = "com.dana.githubuser.common_test"
    compileSdk = rootProject.extra.get("compileSdk") as Int

    defaultConfig {
        minSdk = rootProject.extra.get("minSdk") as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = rootProject.extra.get("javaVersion") as JavaVersion
        targetCompatibility = rootProject.extra.get("javaVersion") as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra.get("kotlinJvmTarget") as String
    }
}

dependencies {

    implementation(libs.kotlin.test.junit)
    implementation(libs.mockk)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit.jupiter.api)
    runtimeOnly(libs.junit.jupiter.engine)
}