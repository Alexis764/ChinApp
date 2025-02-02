plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //KAPT
    kotlin("kapt")
    //Dagger Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.chinappsecond"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chinappsecond"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Dagger hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //Navigation
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.6.2")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //Responsive
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")

    //Implementations
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //UnitTesting
    testImplementation(libs.junit)
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.12.3")

    //AndroidTesting
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}