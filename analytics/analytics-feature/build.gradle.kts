plugins {
    alias(libs.plugins.runique.android.dynamic.feature)
}
android {
    namespace = "com.scientisthamster.analytics_feature"
}

dependencies {
    implementation(project(":app"))

    implementation(projects.core.database)
    implementation(projects.analytics.data)
    implementation(projects.analytics.domain)
    api(projects.analytics.presentation)
}