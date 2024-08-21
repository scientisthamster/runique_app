import com.android.build.gradle.LibraryExtension
import com.scientisthamster.convention.ExtensionType
import com.scientisthamster.convention.configureBuildTypes
import com.scientisthamster.convention.configureKotlinAndroid
import com.scientisthamster.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(commonExtension = this, extensionType = ExtensionType.Library)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                add("implementation", project.libs.findBundle("koin").get())
                add("testImplementation", kotlin("test"))
            }
        }
    }
}