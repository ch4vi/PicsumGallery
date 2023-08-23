plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val appName = "Picsum Gallery"

android {
    namespace = "com.ch4vi.picsumgallery"
    compileSdk = rootProject.extra["compileVersion"] as Int?

    defaultConfig {
        applicationId = "com.ch4vi.picsumgallery"
        minSdk = rootProject.extra["minVersion"] as Int
        targetSdk = rootProject.extra["targetVersion"] as Int
        versionCode = 1
        versionName = rootProject.extra["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            enableAndroidTestCoverage = true
            enableAndroidTestCoverage = true
            isDebuggable = true

            resValue("string", "app_label", "[D] $appName")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            resValue("string", "app_label", appName)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        lintConfig = file("$rootDir/config/lint.xml")
        checkAllWarnings = true
        warningsAsErrors = true
        htmlReport = true
    }
    testCoverage {
        jacocoVersion = "0.8.10"
    }
}

dependencies {

    implementation(libs.bundles.common)
    implementation(libs.bundles.ui)
    debugImplementation(libs.bundles.debug)

    kapt(libs.hilt.kapt)
    kapt(libs.moshi.kapt)
    kapt(libs.room.kapt)
    implementation(libs.bundles.data)

    testImplementation(libs.bundles.test)

    kaptAndroidTest(libs.hilt.kapt)
    androidTestImplementation(libs.bundles.uitest)
}
