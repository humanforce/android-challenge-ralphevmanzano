import org.jetbrains.kotlin.config.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.humanforce.humanforceandroidengineeringchallenge.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_17.toString()
    }
}

dependencies {
    // modules
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.coroutines.core)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    implementation(libs.sandwich.retrofit)
    implementation(libs.sandwich)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}