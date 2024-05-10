plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.starupcowokapp"
    compileSdk = 34
buildFeatures{
    viewBinding=true
}
    defaultConfig {
        applicationId = "com.example.starupcowokapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(group = "com.airbnb.android", name = "lottie", version = "6.0.0")
    implementation ("nl.joery.animatedbottombar:library:1.1.0")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.github.marlonlom:timeago:+")
    implementation ("com.google.android.material:material:1.2.0-alpha02")
    implementation ("com.google.android.material:material:1.1.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
    implementation ("com.google.android.gms:play-services-vision:20.1.3")
    implementation ("com.google.zxing:core:3.2.0")
    implementation ("com.google.firebase:firebase-messaging-ktx:21.1.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation ("com.google.firebase:firebase-messaging-directboot:20.2.0")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
}
