plugins {
    id("java")
}

group = "dev.zmigrodzki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2020.0.20"))

    implementation("io.projectreactor:reactor-core")

    testImplementation(platform(("org.junit:junit-bom:5.8.2")))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.reactivestreams:reactive-streams-tck:1.0.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}