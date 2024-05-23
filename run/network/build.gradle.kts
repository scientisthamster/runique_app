plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.scientisthamster.run.network"
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}