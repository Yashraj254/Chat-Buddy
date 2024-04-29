@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("chatbuddy.android.library")
    id("chatbuddy.android.library.compose")
    id("chatbuddy.android.hilt")
}

android {
    namespace = "com.yashraj.chat_presentation"
}

dependencies {
    implementation(project(":chat:chat_domain"))
    implementation(project(":core:utils"))
    implementation(project(":core:ui"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}