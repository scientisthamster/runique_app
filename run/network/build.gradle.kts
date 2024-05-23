plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.scientisthamster.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}