// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.detekt) apply true
}

detekt {
    toolVersion = "1.23.3"
    config.from(files("config/detekt/detekt.yml"))
    autoCorrect = true
    buildUponDefaultConfig = true
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.1"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.3")
}