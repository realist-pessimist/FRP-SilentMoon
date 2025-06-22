plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.example.silentmoon"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.frp"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":feature-reservation"))
    implementation(project(":analytics"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.arrow.core)
    implementation(libs.arrow.functions)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.arrow.optics.kt)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.navigation)
    implementation(libs.firebase.common.ktx)
    implementation(libs.exoplayer)
    ksp(libs.arrow.optics.ksp)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.kotest.framework)
    testImplementation(libs.kotest.framework)
    testImplementation(libs.kotest.core)
    testImplementation(libs.kotest.property)
    testImplementation(kotlin("reflect"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}