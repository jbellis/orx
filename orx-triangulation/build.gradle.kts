plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val kotlinxSerializationVersion: String by rootProject.extra
val kotestVersion: String by rootProject.extra
val jvmTarget: String by rootProject.extra
val kotlinApiVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra
val kotlinLoggingVersion: String by rootProject.extra
val kluentVersion: String by rootProject.extra
val openrndrVersion: String by rootProject.extra
val openrndrOS: String by rootProject.extra
val spekVersion: String by rootProject.extra
val delaunatorVersion = "1.0.3.3"

kotlin {
    jvm {
        compilations {
            val demo by creating {
                defaultSourceSet {
                    kotlin.srcDir("src/demo")
                    dependencies {
                        implementation("org.openrndr:openrndr-application:$openrndrVersion")
                        implementation("org.openrndr:openrndr-extensions:$openrndrVersion")
                        runtimeOnly("org.openrndr:openrndr-gl3:$openrndrVersion")
                        runtimeOnly("org.openrndr:openrndr-gl3-natives-$openrndrOS:$openrndrVersion")
                        implementation(compilations["main"]!!.output.allOutputs)
                    }
                }
            }
        }
        compilations.all {
            kotlinOptions.jvmTarget = jvmTarget
            kotlinOptions.apiVersion = kotlinApiVersion
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation("com.github.jbellis:delaunator:$delaunatorVersion")
                implementation("org.openrndr:openrndr-shape:$openrndrVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}