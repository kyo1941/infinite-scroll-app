# Repository Guidelines

## Project Structure & Module Organization

This is a single-module Android app built with Gradle Kotlin DSL. The app module lives in `app/`.

- `app/src/main/java/com/example/infinite_scroll_app/`: Kotlin source code.
- `app/src/main/java/com/example/infinite_scroll_app/ui/main/`: main Compose screen, view model, and use case for the infinite-scroll behavior.
- `app/src/main/java/com/example/infinite_scroll_app/ui/theme/`: Compose theme, color, and type definitions.
- `app/src/main/res/`: Android resources, launcher assets, XML config, strings, and themes.
- `app/src/test/`: local JVM unit tests.
- `app/src/androidTest/`: Android instrumentation and UI tests.
- `gradle/libs.versions.toml`: centralized dependency and plugin versions.

## Build, Test, and Development Commands

Use the checked-in Gradle wrapper.

- `./gradlew assembleDebug`: builds a debug APK.
- `./gradlew testDebugUnitTest`: runs local JVM tests in `app/src/test`.
- `./gradlew connectedDebugAndroidTest`: runs instrumentation tests on a connected emulator or device.
- `./gradlew lintDebug`: runs Android lint for the debug variant.

Open the project in Android Studio for emulator management, Compose preview, and interactive debugging.

## Coding Style & Naming Conventions

Use Kotlin with Android Studio defaults: 4-space indentation, trailing commas where they improve multiline diffs, and clear import ordering from the IDE formatter. Keep package names under `com.example.infinite_scroll_app`.

Name Compose UI functions in `PascalCase` and annotate them with `@Composable`, for example `MainScreen`. Name view models with a `ViewModel` suffix and use cases with a `UseCase` suffix. Prefer immutable public state exposed as `StateFlow` and keep mutable state private.

## Testing Guidelines

Use JUnit for local tests and AndroidX test libraries for instrumentation tests. Place fast logic tests in `app/src/test/java/...` and device-dependent tests in `app/src/androidTest/java/...`.

Name test classes after the unit under test, such as `MainUseCaseTest`, and write test methods that describe behavior. Run `./gradlew testDebugUnitTest` before opening a PR; run `./gradlew connectedDebugAndroidTest` when changing UI behavior or Android framework interactions.

## Commit & Pull Request Guidelines

Recent history uses Conventional Commit prefixes such as `feat:` and `fix:`. Keep commit subjects short and imperative, for example `fix: prevent duplicate page loads`.

Pull requests should include a brief summary, the test commands run, and screenshots or a short screen recording for visible UI changes. Link related issues when available and call out behavior changes in the infinite-scroll flow.

## Security & Configuration Tips

Do not commit local machine paths or secrets. Keep `local.properties` local, and put dependency updates in `gradle/libs.versions.toml` instead of scattering versions across build files.
