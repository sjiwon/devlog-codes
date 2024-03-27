rootProject.name = "spring-transaction"

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val kotlinVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
            }
        }
    }
}

include(
    "transaction-abstraction",
    "transaction-sync",
    "transaction-propagation",
)
