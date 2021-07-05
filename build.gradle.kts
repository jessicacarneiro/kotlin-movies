import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
}

group = "io.github.jessicacarneiro"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.litote.kmongo:kmongo:4.2.8")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    testImplementation("io.rest-assured:kotlin-extensions:4.4.0")
    testImplementation("org.amshove.kluent:kluent:1.67")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

sourceSets {
    create("integration") {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/testIntegration/kotlin")
            resources.srcDir("src/testIntegration/resources")
            compileClasspath += sourceSets["main"].output + configurations["testCompileClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
    create("e2e") {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/testE2E/kotlin")
            resources.srcDir("src/testE2E/resources")
            compileClasspath += sourceSets["main"].output + configurations["testCompileClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

task<Test>("e2e") {
    description = "Runs the E2E tests"
    testClassesDirs = sourceSets["e2e"].output.classesDirs
    classpath = sourceSets["e2e"].runtimeClasspath
    mustRunAfter("integration")
}

tasks.register("allTests") {
    dependsOn(tasks["test"])
    dependsOn(tasks["integration"])
    dependsOn(tasks["e2e"])
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
