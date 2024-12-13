import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    kotlin("jvm") version "2.0.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

kotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
}


tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}

tasks.withType(JavaCompile::class){
    options.release = 21
}