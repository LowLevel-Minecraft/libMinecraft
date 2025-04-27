plugins {
    id("java")
}

group = "com.lowlevel.minecraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    implementation("io.netty:netty-buffer:4.2.0.Final")
    implementation("io.netty:netty-handler:4.2.0.Final")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("com.mojang:datafixerupper:8.0.16")
    compileOnly("org.projectlombok:lombok:1.18.34")
    implementation("it.unimi.dsi:fastutil:8.5.11")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}