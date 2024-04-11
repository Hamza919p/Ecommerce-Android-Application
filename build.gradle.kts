buildscript {

    val firebaseDatabaseVersion by extra("20.3.0")
    val firebaseAuthVersion by extra("22.3.0")
    val recyclerViewVersion by extra("1.2.0")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}