package buildsrc.convention

plugins {
  `maven-publish`
}

plugins.withType(JavaPlugin::class.java) {
  publishing {
    publications {
      create<MavenPublication>("mavenJava") {
        from(components["java"])
      }
    }
    repositories {
      maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/adamko-dev/kotka-streams")
        credentials(PasswordCredentials::class)
      }
    }
  }
}

tasks
  .matching { it.name in listOf("publish", "publishToMavenLocal") }
  .configureEach {

    val projectGroup = project.group
    val projectName = project.name
    val projectVersion = project.version

    doLast {
      logger.lifecycle("[${this.name}] ${projectGroup}:${projectName}:${projectVersion}")
    }
  }
