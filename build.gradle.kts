plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:28.2-jre")
    // Test
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "org.alexandre.App"
}