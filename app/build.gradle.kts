plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    //database Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.educationappmaximsvidrak"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.educationappmaximsvidrak"
        minSdk = 28
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

implementation(libs.firebase.database)

    val retrofitVersion = "2.9.0"
    val roomVersion = "2.6.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit und Moshi
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    //Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Coil
    implementation("io.coil-kt:coil:2.5.0")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //viewPager
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //chatGPT
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //database Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    //custom Calender
    implementation ("com.applandeo:material-calendar-view:1.9.2")

    //EpoxyTouchHelper (delete with swipe)
    implementation ("com.airbnb.android:epoxy:5.1.4")
    annotationProcessor ("com.airbnb.android:epoxy-processor:5.1.4")

    implementation ("it.xabaras.android:recyclerview-swipedecorator:1.4")

    //lottie animation
    implementation ("com.airbnb.android:lottie:6.5.1")

    //firebase storage
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

}