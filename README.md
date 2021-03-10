# IDV One Time Passcode

[![Build](https://github.com/michaelruocco/one-time-passcode/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/one-time-passcode/actions)
[![codecov](https://codecov.io/gh/michaelruocco/one-time-passcode/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/one-time-passcode)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/one-time-passcode/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/one-time-passcode&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/one-time-passcode?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_one-time-passcode&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_one-time-passcode)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_one-time-passcode&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_one-time-passcode)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_one-time-passcode&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_one-time-passcode)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_one-time-passcode&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_one-time-passcode)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/one-time-passcode.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22one-time-passcode%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Todo

*   Secure sensitive data flag is true from context
*   Add json error handling

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates spotlessApply build
```

```gradle
// generate dependency graph images at build/reports/dependency-graph
./gradlew generateDependencyGraph
./gradlew generateDependencyGraphIdvOneTimePasscode
```