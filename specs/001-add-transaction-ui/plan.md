# Implementation Plan: Add transaction UI
**Date**: 2026-11-03 | **Spec**: [spec.md](spec.md)

## Summary
Build a static Compose transaction screen and connect Home + through root-level saved state.

## Technical Context
- Language: Kotlin 2.2.10; Java 11 target, Android Gradle Plugin 9.4.1.
- Dependencies: existing Compose BOM 2026.02.01, Material3, Activity Compose 1.13.0.
- Platform: Android API 24+, target 37; native mobile app.
- Storage: none. Screen visibility and selected tab use rememberSaveable.
- Testing: existing JUnit, Android instrumentation and Compose UI test support.
- Constraints: no new dependencies, keyboard, dialogs, persistence or business logic; offline.
- Scope: one new screen, two tabs, four static fields, Home integration.
- Performance: immediate local screen/tab changes; no network work.

## Constitution Check
The installed constitution is an unratified placeholder, so no additional project principles are assumed.
Before and after design: approved scope, existing stack and theme retained; no gate violations.

## Project Structure
- app/src/main/java/com/example/moneymate/MoneyMateApp.kt: root screen state and BackHandler.
- app/src/main/java/com/example/moneymate/ui/transaction/AddTransactionScreen.kt: static form and tab state.
- Existing MainActivity and ui/home/HomeScreen.kt: root entry and + callback.
- app/src/main/res: Vietnamese strings and missing vector icons.
- specs/001-add-transaction-ui: specification, design, tasks and validation report.

## Design
Use Scaffold with safe system insets, a centered max-600dp form and fixed save footer with navigation-bar clearance.
Scrollable body accommodates landscape and 2x fonts. Use the existing light theme, purple/lavender tokens and food icon.
Represent static fields as outlined surfaces with text rather than editable text fields; Save is a no-op button.
Root visibility saved as Boolean; the child tab uses a saved Boolean. Removing the child on Back resets its tab for next entry.
Expose HomeScreen(onAddTransaction: () -> Unit = {}), AddTransactionScreen(onBack: () -> Unit), and MoneyMateApp().
No external APIs, schema changes or migrations.

## Validation
Build debug and run existing unit tests. Run connected tests if a device is available.
Verify open, both tabs, static save, both Back paths, reopening defaults, recreation and scroll reachability.
Inspect phone, short landscape and 2x-font screenshots where a device is available.
