plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.login_page"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.login_page"
        minSdk = 28
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

    buildFeatures{
        viewBinding=true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //firebase
    implementation("com.google.firebase:firebase-database:${rootProject.extra.get("firebaseDatabaseVersion") as String}")
    implementation("com.google.firebase:firebase-auth:${rootProject.extra.get("firebaseAuthVersion") as String}")
    implementation("com.google.firebase:firebase-storage:${rootProject.extra.get("firebaseDatabaseVersion") as String}")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.android.car.ui:car-ui-lib:2.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Material
    implementation ("androidx.recyclerview:recyclerview:${rootProject.extra.get("recyclerViewVersion") as String}")
    implementation ("androidx.cardview:cardview:1.0.0")

    //Images
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("com.squareup.picasso:picasso:2.5.2")

    //RazorPay
    implementation ("com.razorpay:checkout:1.6.33")
}