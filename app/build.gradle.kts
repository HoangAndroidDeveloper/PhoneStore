plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.phonestore"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.example.phonestore"
        minSdk = 21
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
    packagingOptions {
        resources {
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(fileTree(mapOf(
        "dir" to "E:\\ProjectAndroid\\tmp",
        "include" to listOf("*.aar", "*.jar")
    )))
    //noinspection UseTomlInstead
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.circleindicator)
    implementation (libs.glide)
        //noinspection UseTomlInstead
        implementation ("com.github.ybq:Android-SpinKit:1.4.0")
    //noinspection UseTomlInstead
    implementation ("com.timehop.stickyheadersrecyclerview:library:0.4.3@aar")
    //noinspection UseTomlInstead
    implementation ("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
    //noinspection UseTomlInstead
    implementation ("io.github.ParkSangGwon:tedimagepicker:1.5.0")
    //noinspection UseTomlInstead
   // send email
    //noinspection UseTomlInstead
    implementation ("com.sun.mail:android-mail:1.6.6")
    //noinspection UseTomlInstead
    implementation ("com.sun.mail:android-activation:1.6.7")
}