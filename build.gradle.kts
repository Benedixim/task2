plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("org.liquibase:liquibase-core:4.23.0")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation ("org.postgresql:postgresql:42.6.0")
    // https://mvnrepository.com/artifact/org.testcontainers/testcontainers
    testImplementation ("org.testcontainers:testcontainers:1.19.1")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}