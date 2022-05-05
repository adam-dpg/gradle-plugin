plugins {
  `java-gradle-plugin`
  `java-library`
  id("com.gradle.plugin-publish") version "0.21.0"
  kotlin("jvm")
  `kotlin-dsl`
  id("me.qoomon.git-versioning") version "5.2.0"
  buildsrc.convention.`maven-publish`
}

group = "io.snyk.gradle.plugin"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
  refs {
    branch(".+") { version = "\${ref}-SNAPSHOT" }
    tag("v(?<version>.*)") { version = "\${ref.version}" }
  }

  // optional fallback configuration in case of no matching ref configuration
  rev { version = "\${commit}" }
}

pluginBundle {
  website = "https://github.com/snyk/gradle-plugin"
  vcsUrl = "https://github.com/snyk/gradle-plugin"
  tags = listOf("security", "scanning", "dependencies")
}

gradlePlugin {
  plugins {
    create("snykPlugin") {
      id = "io.snyk.gradle.plugin.snyk-kt"
      displayName = "Snyk Security Scanner for Gradle"
      description =
        "Find and fix vulnerabilities in you third-party dependencies with this Snyk for Gradle plugin"
      implementationClass = "io.snyk.gradle.plugin.kt.SnykPlugin"
    }
  }
}

tasks.wrapper {
  gradleVersion = "7.4.2"
  distributionType = Wrapper.DistributionType.ALL
}
