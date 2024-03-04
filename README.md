# Flappy Bird

This is Flappy Bird game written by Korge game engine.

## Setups

1. Java 17, Kotlin 1.9.22
2. Run `build.gradle.kts` to configure projects and load dependencies
3. For testing on Jvm, run: `./gradlew runJvmAutoreload`

## Targets

- Js: `./gradlew jsBrowserDistribution`
- Desktop:
  - For creating a FatJAR, use the gradle task: `./gradlew packageJvmFatJar`
  - For creating a FatJAR using proguard for thinner sizes, use the gradle task: `./gradlew packageJvmFatJarProguard`

# Demo

Demo: https://github.com/hsthe29/hsthe29.github.io/tree/main/flappybird

[//]: # (Build JS: `$ ./gradlew jsBrowserDistribution # Outputs to /build/distributions`)

## Give a ⭐ if you like this project!
