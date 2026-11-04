# Tasks: Transactions list UI
**Input**: spec.md, plan.md, research.md, data-model.md, contracts/ui.md.

## Phase 1: Setup
- [x] T001 Resolve Spec-kit templates and create approved design artifacts in specs/002-transactions-list-ui/.

## Phase 2: Foundation
- [x] T002 Extract shared navigation in app/src/main/java/com/example/moneymate/ui/components/MoneyMateBottomBar.kt and update ui/home/HomeScreen.kt.

## Phase 3: US1 - Browse samples (P1)
Goal: open the reference list and return Home. Independent test: Home -> Transactions -> both Back paths.
- [x] T003 [US1] Add navigation/static content tests in app/src/androidTest/java/com/example/moneymate/TransactionsFlowTest.kt.
- [x] T004 [P] [US1] Add labels and category/search/filter vectors in app/src/main/res/.
- [x] T005 [US1] Build reference list in app/src/main/java/com/example/moneymate/ui/transaction/TransactionsScreen.kt.
- [x] T006 [US1] Connect primary destinations and Back in app/src/main/java/com/example/moneymate/MoneyMateApp.kt.

## Phase 4: US2 - Preview selection and add origin (P2)
Goal: preserve selection and originating destination. Independent test: select, Add, recreate, return, leave and reopen.
- [x] T007 [US2] Add restoration and origin tests in app/src/androidTest/java/com/example/moneymate/TransactionsFlowTest.kt.
- [x] T008 [US2] Save tab/add/origin state and reset on Home in app/src/main/java/com/example/moneymate/MoneyMateApp.kt.

## Phase 5: Polish and validation
- [x] T009 Verify responsive layouts and run debug/unit/connected checks; record evidence in specs/002-transactions-list-ui/validation.md.

## Dependencies and execution
T001 -> T002 -> US1 -> US2 -> T009. Write tests before corresponding behavior. T004 resources can be prepared independently of T003 tests. US2 tests can be authored alongside US1 visual layout once the shared contract is fixed. Execute incrementally: establish shared bar, deliver list MVP, preserve add origin/state, then validate all flows. No delegation required.
