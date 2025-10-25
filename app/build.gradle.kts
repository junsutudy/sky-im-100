plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "app.junsu.imback"
    compileSdk = 35

    defaultConfig {
        applicationId = "app.junsu.imback"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            buildConfigField(
                type = "String",
                name = "BASE_URL",
                /* value = gradleLocalProperties(rootDir).getProperty("BASE_URL_PROD") */
                value = "\"https://picsum.photos\"",
            )
        }

        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                /* value = gradleLocalProperties(rootDir).getProperty("BASE_URL_DEBUG") */
                value = "\"https://picsum.photos\"",
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
    }
}

dependencies {


    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-compiler:2.57.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:5.2.1")
    implementation("com.squareup.okhttp3:logging-interceptor:5.2.1")

    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("io.coil-kt:coil-compose:2.5.0")


    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    implementation("androidx.paging:paging-compose:3.2.1")

    implementation("androidx.room:room-runtime:2.8.2")
    implementation("androidx.room:room-ktx:2.8.2")
    ksp("androidx.room:room-compiler:2.8.2")

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