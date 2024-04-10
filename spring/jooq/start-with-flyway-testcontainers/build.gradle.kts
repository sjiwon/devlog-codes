import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

plugins {
    id("org.flywaydb.flyway")
    id("nu.studer.jooq")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":common-resources"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    // Data
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j:${property("mysqlVersion")}")
    implementation("org.flywaydb:flyway-core:${property("flywayVersion")}")
    implementation("org.flywaydb:flyway-mysql:${property("flywayVersion")}")

    // jOOQ
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    jooqGenerator("mysql:mysql-connector-java:${property("mysqlVersion")}")
    jooqGenerator("org.jooq:jooq:${property("jooqVersion")}")
    jooqGenerator("org.jooq:jooq-codegen:${property("jooqVersion")}")
    jooqGenerator("org.jooq:jooq-meta:${property("jooqVersion")}")

    // QueryDsl
    implementation("com.querydsl:querydsl-jpa:${property("queryDslVersion")}:jakarta")
    kapt("com.querydsl:querydsl-apt:${property("queryDslVersion")}:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // Kotlin JDSL
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${property("kotlinJdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${property("kotlinJdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${property("kotlinJdslVersion")}")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:${property("p6spyVersion")}")

    // Test
    testImplementation("org.flywaydb.flyway-test-extensions:flyway-spring-test:${property("flywayTestExtensionVersion")}")

    // TestContainers
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter:${property("testContainersVersion")}")
    testImplementation("org.testcontainers:mysql:${property("testContainersVersion")}")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

// jOOQ
val mysqlContainer = tasks.register("mysqlContainer") {
    val container = MySQLContainer<Nothing>(DockerImageName.parse("mysql:8.0.33")).apply {
        withDatabaseName("dsl")
        withUsername("root")
        withPassword("1234")
        start()
    }

    extra.set("jdbcUrl", container.jdbcUrl)
    extra.set("username", container.username)
    extra.set("password", container.password)
    extra.set("databaseName", container.databaseName)
    extra.set("container", container)
}

val mysqlJdbcUrl = mysqlContainer.get().extra["jdbcUrl"].toString()
val mysqlUsername = mysqlContainer.get().extra["username"].toString()
val mysqlPassword = mysqlContainer.get().extra["password"].toString()
val mysqlDatabaseName = mysqlContainer.get().extra["databaseName"].toString()
val container = mysqlContainer.get().extra["container"] as MySQLContainer<*>

buildscript {
    dependencies {
        classpath("mysql:mysql-connector-java:${property("mysqlVersion")}")
        classpath("org.flywaydb:flyway-mysql:${property("flywayVersion")}")
        classpath("org.testcontainers:mysql:${property("testContainersVersion")}")
    }

    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group.startsWith("org.jooq") && requested.name.startsWith("jooq")) {
            useVersion("${property("jooqVersion")}")
        }
    }
}

flyway {
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    url = mysqlJdbcUrl
    user = mysqlUsername
    password = mysqlPassword
}

jooq {
    version.set("${property("jooqVersion")}")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = mysqlJdbcUrl
                    user = mysqlUsername
                    password = mysqlPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = mysqlDatabaseName
                        forcedTypes.addAll(
                            arrayOf(
                                ForcedType()
                                    .withName("varchar")
                                    .withIncludeExpression(".*")
                                    .withIncludeTypes("JSONB?"),
                                ForcedType()
                                    .withName("varchar")
                                    .withIncludeExpression(".*")
                                    .withIncludeTypes("INET")
                            ).toList()
                        )
                    }
                    generate.apply {
                        isDaos = true
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                        isJavaTimeTypes = true
                    }
                    target.apply {
                        directory = "build/generated/jooq" // jOOQ Model 생성 위치
                        packageName = "com.sjiwon.jooq" // 패키지명
                        encoding = "UTF-8"
                    }
                    strategy.name = "org.jooq.codegen.example.JPrefixGeneratorStrategy" // jOOQ Model Prefix Strategy
                }
            }
        }
    }
}

tasks.named<JooqGenerate>("generateJooq") {
    dependsOn("mysqlContainer")
    dependsOn("flywayMigrate")

    inputs.files(fileTree("src/main/resources/db/migration"))
        .withPropertyName("migration")
        .withPathSensitivity(PathSensitivity.RELATIVE)

    allInputsDeclared.set(true)
    outputs.cacheIf { true }
    doLast { container.stop() }
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
