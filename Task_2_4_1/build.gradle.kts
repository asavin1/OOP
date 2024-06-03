plugins {
    id("java")
    id ("jacoco")
    id("groovy")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.gradle:gradle-tooling-api:7.3-20210825160000+0000")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.10")
    implementation("org.apache.groovy:groovy-all:5.0.0-alpha-1")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("org.freemarker:freemarker:2.3.30")
    implementation("commons-io:commons-io:2.16.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    "jacocoTestReport"(JacocoReport::class) {
        reports {
            xml.required.set(true)
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("$buildDir/jacoco/jacocoHtml"))
        }
    }
}
