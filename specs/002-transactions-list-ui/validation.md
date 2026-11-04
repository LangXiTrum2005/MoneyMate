# Validation: Transactions list UI
Date: 2026-11-03

## Automated results
Final command: `gradlew.bat assembleDebug testDebugUnitTest connectedDebugAndroidTest --offline`.
Result: **BUILD SUCCESSFUL**.
- Existing unit test: 1 passed, 0 failures.
- Connected tests: 4 passed, 0 failures/errors/skips on Android API 37 emulator.
- Existing AddTransactionFlowTest remains passing (tabs, recreation, static save, reset and both Back routes).
- New TransactionsFlowTest covers reference rows and totals in every preview tab, selection semantics, inert search/filter/row interactions, no editable text nodes, toolbar/system Back, Home reset, recreation and both Add return routes.
- No new dependencies, persistence, network calls, external API or schema changes.

Initial attempts encountered a disconnected/booting emulator and a Pixel Launcher ANR dialog obscuring the application. After boot completion and closing that system dialog, all tests passed. Final layout refinement was followed by a successful full build/unit/connected run.

## Visual verification
Inspected actual emulator screenshots after installing the final APK:
- 360x640dp portrait, density 320: all four rows, dates and signed amounts visible, tabs/filter on one row, selected Transactions bottom item; native system bars clear.
- 720x360dp landscape: content scrolls; final shopping row and amount fully reachable above fixed navigation.
- 360x800dp, font scale 2: search text and tabs wrap; month summary stacks; amounts stack below date labels. Final shopping amount is fully visible after scrolling, with no row overlap or bottom-bar collision.
- Content width remains capped at 600dp in landscape; vector icons stay crisp.

Screenshots are ignored local build artifacts:
- `build/transactions-small.png`
- `build/transactions-landscape.png`
- `build/transactions-landscape-scrolled.png`
- `build/transactions-large-text.png`
- `build/transactions-large-text-scrolled.png`

Emulator size, density and font scale restored after inspection. The headless emulator started for this task was stopped afterward.

## Review and limits
- Spec quality checklist: 16/16 satisfied; no extension hooks configured.
- Existing unratified constitution remains unchanged; previous feature documents unchanged.
- Native touch targets and selected tab semantics retained; meaningful icon buttons have Vietnamese labels and category icons are decorative.
- Screen intentionally follows the app's existing light theme. No custom animation introduced.
- Physical device, TalkBack and dark theme testing were not performed.
- Summary totals and sample dates remain independent static reference values. Search, filter and rows intentionally have no data effects.
