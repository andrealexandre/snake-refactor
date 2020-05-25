plugins {
    java
    application
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:28.2-jre")
    // Test
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "org.snake.App"
}