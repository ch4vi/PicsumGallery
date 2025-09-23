import io.gitlab.arturbosch.detekt.Detekt

extra.apply {
    set("versionName", "1.0.0")
    set("versionCode", 1)
    set("compileVersion", 36)
    set("targetVersion", 34)
    set("minVersion", 30)
}

plugins {
    alias(libs.plugins.plugin.application) apply false
    alias(libs.plugins.plugin.kotlin) apply false
    alias(libs.plugins.plugin.detekt) apply false
    alias(libs.plugins.plugin.ktlint) apply false
    alias(libs.plugins.plugin.ksp) apply false
    alias(libs.plugins.plugin.hilt) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

subprojects {
    tasks.withType<Detekt>().configureEach {
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
    delete(rootProject.layout.buildDirectory)
}
