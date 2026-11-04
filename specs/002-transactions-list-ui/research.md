# Research: Transactions list UI

## State and navigation
Decision: root rememberSaveable primitives for destination, add visibility and tab.
Rationale: existing app uses saved local navigation; tab must survive child removal during Add.
Alternatives: navigation dependency or ViewModel adds unnecessary scope for two static destinations.

## Layout
Decision: LazyColumn with stable keys, native safe insets and large-font stacking.
Rationale: local UI/UX Compose guidance supports lazy lists and stable keys; fixed heights would clip large fonts/landscape.

## Data
Decision: four immutable samples and independent fixed summary totals.
Rationale: user chose static UI; screenshot total differs from visible expenses.
Alternatives: calculated totals or persistence violate approved scope.

No unresolved research questions remain; repository inspection and approved plan supply all decisions.
