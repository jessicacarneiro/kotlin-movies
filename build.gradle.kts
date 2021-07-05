import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"

    idea
    jacoco
}

group = "io.github.jessicacarneiro"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

jacoco { toolVersion = "0.8.7" }

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.litote.kmongo:kmongo:4.2.8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:kotlin-extensions:4.4.0")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.amshove.kluent:kluent:1.67")
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

task<Test>("integration") {
    description = "Runs the integration tests"
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    mustRunAfter("test")
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

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
