plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "app.junsu.imback"
    compileSdk = 36

    defaultConfig {
        applicationId = "app.junsu.imback"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {

        // BASE_URL_PROD="https://picsum.photos"
        // BASE_URL_DEBUG="https://picsum.photos"
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = localProperty("BASE_URL_PROD")
            )
        }

        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = localProperty("BASE_URL_DEBUG")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {


    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.material.icons.extended)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)


    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
fun localProperty(key: String): String =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
        rootDir,
        providers,
    ).getProperty(key)