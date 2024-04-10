import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
