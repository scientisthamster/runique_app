plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.scientisthamster.core.data"
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.domain)

    implementation(libs.timber)
}