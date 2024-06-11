plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.scanbotexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scanbotexample"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    implementation("io.scanbot:sdk-package-4:5.1.1")
    implementation("io.scanbot:sdk-package-ui:5.1.1")
    implementation("io.scanbot:rtu-ui-v2-bundle:5.1.1")
    implementation("io.scanbot:sdk-generictext-assets:5.1.1")
    implementation("io.scanbot:bundle-sdk-barcode-assets:5.1.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}