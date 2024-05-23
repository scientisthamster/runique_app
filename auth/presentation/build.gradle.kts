plugins {
    alias(libs.plugins.runique.android.feature)
}

android {
    namespace = "com.scientisthamster.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}