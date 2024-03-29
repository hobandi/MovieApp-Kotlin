// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0-beta01")
        classpath("com.neenbedankt.gradle.plugins:android-apt:1.8")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:1.3.50}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
