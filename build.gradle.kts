@file:Suppress("DSL_SCOPE_VIOLATION")

import io.gitlab.arturbosch.detekt.Detekt

extra.apply {
    set("versionName", "1.0.0")
    set("versionCode", 1)
    set("compileVersion", 34)
    set("targetVersion", 33)
    set("minVersion", 30)
}

plugins {
    alias(libs.plugins.plugin.application) apply false
    alias(libs.plugins.plugin.kotlin) apply false
    alias(libs.plugins.plugin.detekt) apply false
    alias(libs.plugins.plugin.ktlint) apply false
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

subprojects {
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        description = "Runs a custom detekt build."
        config.setFrom(files("$rootDir/config/detekt.yml"))
        autoCorrect = true
        disableDefaultRuleSets = false

        reports {
            html.required.set(true)
            html.outputLocation.set(file("build/reports/detekt.html"))

            xml.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
        include("**/*.kt", "**/*.kts")
        exclude("resources/", "build/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
