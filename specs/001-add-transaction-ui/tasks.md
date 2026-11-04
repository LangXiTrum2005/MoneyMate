# Tasks: Add transaction UI
**Input**: spec.md, plan.md, research.md, data-model.md, contracts/ui.md, quickstart.md.
## Phase 1: Setup
- [x] T001 Initialize Spec Kit and prepare approved feature artifacts in specs/001-add-transaction-ui/.
## Phase 2: Foundation
- [x] T002 Add Vietnamese labels and sample values to app/src/main/res/values/strings.xml and vector icons in app/src/main/res/drawable/.
## Phase 3: US1 - Open and return (P1)
Goal: access static form and return to unchanged Home.
Independent test: + opens full screen; toolbar and Android Back return Home.
- [x] T003 [US1] Build inset-aware scrollable form and static save footer in app/src/main/java/com/example/moneymate/ui/transaction/AddTransactionScreen.kt.
- [x] T004 [US1] Connect Home callback and saved root navigation in app/src/main/java/com/example/moneymate/ui/home/HomeScreen.kt, MoneyMateApp.kt and MainActivity.kt.
## Phase 4: US2 - Tab preview (P2)
Goal: switch type without changing static fields.
Independent test: switch tabs, recreate, return and reopen to check reset.
- [x] T005 [US2] Add shared form tab selection with saved state and previews to app/src/main/java/com/example/moneymate/ui/transaction/AddTransactionScreen.kt.
## Phase 5: Verification
- [x] T006 Build debug, run existing tests and record results in specs/001-add-transaction-ui/validation.md.
- [x] T007 Verify interaction/recreation and phone, landscape, large-text layouts; record evidence or environment limits in specs/001-add-transaction-ui/validation.md.
## Dependencies and Strategy
T001 -> T002 -> T003 -> T004 -> T005 -> T006 -> T007.
US1 is the MVP; US2 extends it. Implement sequentially because the UI tasks share files.
Parallel opportunities: resource icon additions can be independent of string additions in T002; no agent delegation needed.


