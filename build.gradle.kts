plugins {
    id("java")
    id("me.champeau.jmh") version "0.6.6"
}

group = "dev.zmigrodzki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2020.0.20"))

    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor:reactor-tools")

    testImplementation(platform(("org.junit:junit-bom:5.8.2")))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.reactivestreams:reactive-streams-tck:1.0.4")
    testImplementation("org.testng:testng:7.6.0")
    testImplementation("org.assertj:assertj-core:3.23.1")

    jmh("org.openjdk.jmh:jmh-core:1.35")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.35")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    useTestNG()
}