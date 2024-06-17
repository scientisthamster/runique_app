plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.jvm.ktor)
}

android {
    namespace = "com.scientisthamster.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}