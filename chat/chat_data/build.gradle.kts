@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("chatbuddy.android.library")
    id("chatbuddy.android.hilt")
}

android {
    namespace = "com.yashraj.chat_data"

    buildTypes {
        android.buildFeatures.buildConfig = true
        val key: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
            rootDir
        ).getProperty("EMP_COMMENTS_API_KEY")

        debug{
            buildConfigField("String","EMP_COMMENTS_API_KEY", key)
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":chat:chat_domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation (libs.firebase.ui.auth)

    implementation(libs.play.services.auth)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}