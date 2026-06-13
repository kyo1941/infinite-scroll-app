# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Project structure, coding style, testing, and commit conventions are documented in `AGENTS.md`, imported below. This file focuses on the build commands and the architecture that span multiple files.

@AGENTS.md

## Commands

Use the checked-in Gradle wrapper (`./gradlew`).

- `./gradlew assembleDebug` — build a debug APK.
- `./gradlew testDebugUnitTest` — run local JVM unit tests (`app/src/test`).
- `./gradlew testDebugUnitTest --tests "com.example.infinite_scroll_app.MainUseCaseTest"` — run a single test class.
- `./gradlew connectedDebugAndroidTest` — run instrumentation/UI tests on a connected device or emulator (`app/src/androidTest`).
- `./gradlew lintDebug` — run Android lint for the debug variant.

Dependency and plugin versions are centralized in `gradle/libs.versions.toml`; add updates there rather than inline in build files.

## Architecture

Single-module Android app (Kotlin, Jetpack Compose, Material3). The point of the project is a hand-rolled infinite scroll — there is no networking or persistence; "data" is just a growing `List<Long>` of sequential numbers generated on demand.

The infinite-scroll flow is split across three files in `app/src/main/java/com/example/infinite_scroll_app/ui/main/`, following a Screen → ViewModel → UseCase layering:

- **`MainScreen.kt`** — the Compose UI and the *trigger* for paging. It renders a `LazyColumn` and watches scroll position via `rememberLazyListState()`. A `derivedStateOf` (`isRequiredMoreLoadAfter`) fires when the last visible item comes within a `buffer` (20 items) of the end of the list; a `LaunchedEffect` keyed on that boolean then calls `viewModel.loadAfter(30)` to append the next page. The Screen owns the scroll-detection threshold logic; the ViewModel/UseCase own data generation.
- **`MainViewModel.kt`** — holds the list as a private `MutableStateFlow<List<Long>>` exposed as a read-only `StateFlow` (`uiList`). `loadAfter(limit)` delegates generation and concatenation to the UseCase, then `update`s the flow. State exposure follows the immutable-public / mutable-private `StateFlow` convention.
- **`MainUseCase.kt`** — pure list logic, no Android dependencies (hence easy to unit-test). `makeItems` builds a sequential range continuing from the list's last value; `addItems` concatenates pages.

`MainActivity.kt` is a thin entry point: edge-to-edge `Scaffold` wrapping `MainScreen`, themed by `ui/theme/`.

When changing paging behavior, keep the responsibility split intact: scroll-position thresholds live in the Screen, while what data gets produced lives in the UseCase so it stays testable without an emulator. The current branch (`feature/scroll-up-paging`) is extending this to support paging upward as well as downward — expect symmetric trigger/generation logic for the leading edge.
