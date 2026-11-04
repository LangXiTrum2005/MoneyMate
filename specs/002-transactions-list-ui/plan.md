# Implementation Plan: Transactions list UI
**Date**: 2026-11-03 | **Spec**: [spec.md](spec.md)

## Summary
Add the static reference list, extract shared bottom navigation, and preserve primary destination and tab through add-screen round trips and recreation.

## Technical Context
- Existing Kotlin / Jetpack Compose / Material3, Android API 24+, Java 11 target.
- No network, persistence, backend, ViewModel or new dependencies.
- Root rememberSaveable primitives: primary destination, add visibility and preview tab.
- LazyColumn with stable keys, shared fixed bottom bar, safe insets and max 600dp width.
- Debug build, unit tests, Compose instrumentation and responsive screenshots.

## Constitution Check
Installed constitution is an unratified placeholder; no extra principles are inferred. Before and after design: approved static scope and existing dependencies retained; no violations.

## Project Structure
- MoneyMateApp manages destination, add visibility and preview tab state.
- ui/components/MoneyMateBottomBar.kt exposes selection and callbacks; Home receives a Transactions callback.
- ui/transaction/TransactionsScreen.kt renders static summary, tabs, search, filter and rows.
- Resources contain Vietnamese labels and vector icons; androidTest verifies navigation and recreation.

## Design
Home and Transactions share bottom navigation. Returning Home resets the preview tab. Add retains its underlying primary destination and tab. Both Back routes close add first, then navigate Transactions to Home. Existing add local tab behavior remains unchanged.
Header/search/tabs/summary scroll together for short-screen reachability. Large fonts wrap tabs and stack summary/row amounts. Native system bars remain real. No external API or migration.

## Validation
Run assembleDebug, testDebugUnitTest and connectedDebugAndroidTest when a device exists. Verify both add origins, both Back routes, recreation, reset on reopen, static interactions and portrait/landscape/2x-font layouts. Record actual results in validation.md.
