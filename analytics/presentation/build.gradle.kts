plugins {
    alias(libs.plugins.runique.android.feature)
}

android {
    namespace = "com.scientisthamster.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}