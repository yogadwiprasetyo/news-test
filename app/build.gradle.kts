plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "technical.test.yprsty"
    compileSdk = 34

    defaultConfig {
        applicationId = "technical.test.yprsty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val key: String by project
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "ApiKey", key)
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
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    val pagingVersion = "3.2.1"
    val viewPagerVersion = "1.0.0"
    val koinAndroidVersion = "3.5.3"
    val lifecycleVersion = "2.6.2"
    val roomVersion = "2.6.1"
    val coroutineAndroidVersion = "1.7.3"

    val retrofitVersion = "2.9.0"
    val okhttp3VersionBom = "4.12.0"
    val glideVersion = "4.16.0"

    // Default
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Paging3
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:$viewPagerVersion")

    // Koin
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")

    // View model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // Live data
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion") // Use Kotlin Symbol Processing
    implementation("androidx.room:room-ktx:$roomVersion") // Support coroutines
    implementation("androidx.room:room-paging:$roomVersion") // Paging 3 integration

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineAndroidVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    // Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    // Okhttp3 with BOM
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttp3VersionBom"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}