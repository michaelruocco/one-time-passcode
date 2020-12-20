# One Time Passcode

[![Build](https://github.com/michaelruocco/library-template/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/library-template/actions)
[![codecov](https://codecov.io/gh/michaelruocco/library-template/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/library-template)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/library-template/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/library-template&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/library-template?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_library-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_library-template)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_library-template&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_library-template)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_library-template&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_library-template)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_library-template&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_library-template)
[![Download](https://api.bintray.com/packages/michaelruocco/maven/library-template/images/download.svg)](https://bintray.com/michaelruocco/maven/library-template/_latestVersion)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/library-template.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22library-template%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Todo

*   Set up repo

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates lintGradle spotlessApply build
```

```gradle
// generate dependency graph images at build/reports/dependency-graph
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphOneTimePasscode
```