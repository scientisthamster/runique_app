plugins {
    alias(libs.plugins.runique.android.library.compose)
}

android {
    namespace = "com.scientisthamster.core.presentation.ui"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}