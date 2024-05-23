plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.scientisthamster.core.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}