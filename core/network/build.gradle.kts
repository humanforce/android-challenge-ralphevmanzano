import org.jetbrains.kotlin.config.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.humanforce.humanforceandroidengineeringchallenge.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val envFile = rootProject.file(".env")
        val properties = Properties()
        if (envFile.exists()) {
            properties.load(envFile.inputStream())
        }

        buildConfigField("String", "OPEN_WEATHER_MAP_API_KEY", properties.getProperty("OPEN_WEATHER_MAP_API_KEY", "\"\""))
    }

    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_17.toString()
    }
}

dependencies {
    implementation(project(":core:domain"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Retrofit + Okhttp + Sandwich
    implementation(platform(libs.retrofit.bom))
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.sandwich.retrofit)
    implementation(libs.sandwich)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}