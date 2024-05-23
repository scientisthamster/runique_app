plugins {
    alias(libs.plugins.runique.android.feature)
}

android {
    namespace = "com.scientisthamster.run.presentation"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}