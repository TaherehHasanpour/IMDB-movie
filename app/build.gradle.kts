plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

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
    implementation("androidx.activity:activity-compose:1.3.1")

//
//    //retrofit
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
//
//
//    //hilt di
//    implementation ("com.google.dagger:hilt-android:2.44")
//    kapt ("com.google.dagger:hilt-compiler:2.44")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
//
//
//    //compose navigation
//    implementation ("androidx.navigation:navigation-compose:2.7.7")
//
//    //animation
//    implementation ("com.airbnb.android:lottie-compose:5.2.0")
//
//    //coil - load image from url
//    implementation ("io.coil-kt:coil-compose:2.2.2")
//
//    //swipe refresh
//    implementation( "com.google.accompanist:accompanist-swiperefresh:0.30.0")
//
//    //system ui controller
//    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
//
//    //datastore
//    implementation ("androidx.datastore:datastore-preferences:1.1.0")
//
//    //pager
//    implementation ("com.google.accompanist:accompanist-pager:0.29.0-alpha")
//    implementation ("com.google.accompanist:accompanist-pager-indicators:0.29.0-alpha")
//
//    //room
//    implementation ("androidx.room:room-runtime:2.6.1")
//    kapt ("androidx.room:room-compiler:2.6.1")
//    implementation ("androidx.room:room-ktx:2.6.1")
//
//    // paging
//    implementation ("androidx.paging:paging-runtime-ktx:3.3.0-beta01")
//    implementation ("androidx.paging:paging-compose:3.3.0-beta01")
//
//    // youtube player
//    implementation("io.github.ilyapavlovskii:youtubeplayer-compose:2023.11.16")
////    implementation("com.chrynan.uri:uri-core:0.3.3")
////    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
//
//    // exo player
////    implementation ("androidx.media3:media3-exoplayer:1.2.0")
////    implementation ("androidx.media3:media3-exoplayer-dash:1.2.0")
////    implementation ("androidx.media3:media3-ui:1.2.0")
//
//    // zoomable image
////    implementation("me.saket.telephoto:zoomable:0.7.1")
//    implementation("me.saket.telephoto:zoomable-image-coil:0.7.1")
//
//
    //paging3
    implementation ("androidx.paging:paging-compose:3.3.0-beta01")



}