[![Release](https://jitpack.io/v/adam-dpg/snyk-gradle-plugin.svg)](https://jitpack.io/#adam-dpg/snyk-gradle-plugin)

# Prototype Snyk Plugin for Gradle

[This project](https://github.com/adam-dpg/snyk-gradle-plugin/) contains a proposed Snyk Gradle
plugin that re-implements the existing plugin in Kotlin.


Benefits
* compatible with the Gradle build and configuration caches 
* more options for configuration
* validates the Snyk CLI checksum

## Getting started

To use it, add [Jitpack as a plugin repository](https://jitpack.io/#adam-dpg/gradle-plugin), for
example, with centralized repository definitions;

```kotlin
//settings.gradle.kts
dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }

  pluginManagement {
    repositories {
      gradlePluginPortal()
      mavenCentral()
      maven("https://jitpack.io") // add Jitpack
    }
  }
}
```

```kotlin
//build.gradle.kts
plugins {
  id("io.snyk.gradle.plugin.snyk-kt") version "master-SNAPSHOT"
}
```

### Usage

```kotlin
//build.gradle.kts
snyk {
  // The auth token is automatically detected from either
  // - SNYK_TOKEN environment variable
  // - a SNYK_TOKEN variable in $GRADLE_USER_HOME/gradle.properties

  // You can also manually set it
  snykToken.set("1234-1234-1234")
  // or use a custom environment variable
  snykToken.set(providers.environmentVariable("CUSTOM_SNYK_TOKEN_ENV_VAR"))

  // Set arguments that will be added to the Snyk task
  defaultArguments.add("--print-deps")

  // helper setter for --severity-threshold
  defaultSeverity.set(SnykExtension.Severity.CRITICAL)

  // not yet implemented
  cliAutoUpdateEnabled.set(false)

  // where the Snyk CLI should be downloaded
  cliDownloadDir.set(layout.projectDirectory.dir(".gradle/snyk"))

  cliVersion.set("v1.919.0")
  cliFilename.set("")

  // by default the CLI is downloaded from GitHub, and the download URL is 
  // computed based on the CLI version and filename.
  // Only if necessary, set a manual download URL - this will override the cliVersion!
  cliSourceUri.set("https://my-own-download-url.com/snyk-cli")
}
```

---

![Snyk logo](https://snyk.io/style/asset/logo/snyk-print.svg)

# Snyk plugin for Gradle

[![Application CI](https://github.com/snyk/gradle-plugin/workflows/Application%20CI/badge.svg?branch=master)](https://github.com/snyk/gradle-plugin/actions?query=workflow%3A%22Application+CI%22)

Snyk helps you find, fix and monitor for known vulnerabilities in your dependencies, both on an
ad-hoc basis and as part of your CI (Build) system.

The Snyk Gradle plugin tests and monitors your Gradle dependencies.

| :information_source: This product is not an official Snyk supported product. It is an open-source community driven project that is initialised and partially maintained by Snyk engineers |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Using the Snyk Plugin for Gradle

The latest version of the plugin is released at
the [Gradle Plugins Portal](https://plugins.gradle.org/plugin/io.snyk.gradle.plugin.snykplugin).
Import the plugin using the plugin DSL

Groovy:

```groovy
plugins {
  id "io.snyk.gradle.plugin.snykplugin" version "0.4"
}
```

Kotlin

```kotlin
plugins {
  id("io.snyk.gradle.plugin.snykplugin") version "0.4"
}
```

### Setting:

Groovy:

```groovy
snyk {
  arguments = '--all-sub-projects'
  severity = 'low'
  api = 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx'
  autoDownload = true
  autoUpdate = true
}
```

all fields are optional

- **arguments** - add extra arguments to the Snyk CLI. See Snyk CLI help for more information. In
  this example it scans all subprojects for gradle
- **severity** - what is the severity threshold. Leave empty to only show the vulnerabilities but
  not break
- **api** - api key that can be found on the settings page of your (free) Snyk account.
  Alternatively you can set an environment variable `SNYK_TOKEN` and omit it here
- **autoDownload** - automatically download the CLI is none is installed (default = true)
- **autoUpdate** - update the CLI if there is a newer version (only if downloaded by gradle
  plugin) (default = false)

### Running:

Snyk Test:

```bash
$ gradle snyk-test
```

Snyk Test together with a clean build:

```bash
$ gradle clean build snyk-test
```

Snyk Monitor:

```bash
$ gradle snyk-monitor
```

Snyk Monitor together with a clean build:

```bash
$ gradle clean build snyk-monitor
```

