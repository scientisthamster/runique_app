plugins {
    alias(libs.plugins.runique.android.feature)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.scientisthamster.run.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.run.domain)

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
}