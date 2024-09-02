import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}



android {
    namespace = "com.example.sunny_warehouse"
    compileSdk = 34


    packaging {
        resources {
            excludes += listOf("META-INF/NOTICE.md", "META-INF/LICENSE.md")
        }
    }


    defaultConfig {
        applicationId = "com.example.sunny_warehouse"
        minSdk = 26
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



}

dependencies {
    //implementation("com.sun.mail:javax.mail:1.6.2")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(fileTree("src/main/libs") { include("*.jar") })
    implementation(files("libs/mail.jar"))
    implementation(files("libs/additionnal.jar"))
    implementation(files("libs/activation.jar"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("androidx.activity:activity:1.9.0")
    //implementation("org.ow2.jonas.osgi:springframework:5.1.7")

    //implementation("org.bitbucket.risu8:springframework:1.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    //implementation("javax.mail:javax.mail-api:1.6.2")

//    implementation("com.sun.mail:android-mail:1.6.6")
//    implementation("com.sun.mail:android-activation :1.6.6")

}