import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

androidExtensions {
    configure<AndroidExtensionsExtension> {
        isExperimental = true
    }
}

android {

    defaultConfig {
        applicationId = "pl.kfert.movie"
        minSdkVersion(17)
        compileSdkVersion(29)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Movie Dev")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
        create("mock") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".mock"
            resValue("string", "app_name", "Movie Mock")
            buildConfigField("boolean", "MOCK_DATA", "true")
        }
        create("production") {
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Movie")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
    }

    applicationVariants.all {
        buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/\"")
        buildConfigField("String", "API_KEY", "\"e3a1095d6b893aa1e6c9c6571a0c512e\"")
        buildConfigField("String", "SMALL_IMAGE_URL", "\"https://image.tmdb.org/t/p/w200\"")
        buildConfigField("String", "LARGE_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "ORIGINAL_IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")

        when (name) {
            "dev" -> {
            }
            "production" -> {
            }
        }
    }

    compileOptions {
        setTargetCompatibility(1.8)
        setSourceCompatibility(1.8)
    }

    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dataBinding {
        isEnabled = true
    }

}

dependencies {
    // common
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta2")
    implementation("androidx.recyclerview:recyclerview:1.1.0-beta05")
    implementation("com.google.android.material:material:1.1.0-beta01")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.core:core-ktx:1.2.0-beta01")
    implementation("androidx.fragment:fragment-ktx:1.2.0-beta01")
    implementation("androidx.paging:paging-runtime:2.1.0")
    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-beta01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-beta01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-beta01")

    // room
    implementation("androidx.room:room-runtime:2.2.0")
    kapt("androidx.room:room-compiler:2.2.0")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.2.0")

    // navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.2.0-beta01")
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.0-beta01")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.0-beta01")

    // gson
    implementation("com.google.code.gson:gson:2.8.5")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.9.0")
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    implementation("org.koin:koin-androidx-viewmodel:2.0.1")


    // unit test
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.27.0")
    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.0")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinCompilerVersion.VERSION}")
}