@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("chatbuddy.android.application")
    id("chatbuddy.android.application.compose")
    id("chatbuddy.android.hilt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yashraj.chatbuddy"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":chat:chat_domain"))
    implementation(project(":chat:chat_data"))
    implementation(project(":chat:chat_presentation"))
    implementation(project(":login"))
    implementation(project(":core:utils"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.system.ui.controller)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}