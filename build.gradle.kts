// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        dependencies {
            classpath("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
            //noinspection GradlePluginVersion
            classpath("com.android.tools.build:gradle:3.0.0-beta6")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        }
//        allprojects {
//            repositories {
//                google()
//                mavenCentral()
////                maven{url 'https://jitpack.io'}
//            }
//        }
    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
//    id("com.google.dagger.android") version "2.48.1" apply false

}

//        task clean(type: Delete) {
//        delete rootProject.buildDir
//    }