# IDV One Time Passcode

[![Build](https://github.com/michaelruocco/idv-one-time-passcode/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/idv-one-time-passcode/actions)
[![codecov](https://codecov.io/gh/michaelruocco/idv-one-time-passcode/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/idv-one-time-passcode)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/idv-one-time-passcode/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/idv-one-time-passcode&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv-one-time-passcode?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-one-time-passcode&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-one-time-passcode)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-one-time-passcode&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-one-time-passcode)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-one-time-passcode&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-one-time-passcode)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_idv-one-time-passcode&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_idv-one-time-passcode)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco.idv/idv-one-time-passcode-spring-app.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco.idv%22%20AND%20a:%22idv-one-time-passcode-spring-app%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Todo

*   Add masking sensitive values in logs
*   Secure sensitive data flag is true from context
    
*   Don't allow resending passcode if passcode has been verified / verification complete
*   Add plain app integration test for verifying otp getting complete otp
*   Add karate test for verifying otp and getting complete otp
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