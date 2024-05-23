plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.android.room)
}

android {
    namespace = "com.scientisthamster.core.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.org.mongodb.bson)
}