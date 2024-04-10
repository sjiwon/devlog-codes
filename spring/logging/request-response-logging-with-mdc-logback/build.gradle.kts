import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common-resources"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
