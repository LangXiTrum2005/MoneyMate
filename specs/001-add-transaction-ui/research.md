# Research
## Navigation
Decision: root rememberSaveable Boolean plus BackHandler.
Rationale: two screens require no navigation dependency; child removal resets tab on reopen.
Alternative: Navigation Compose adds unnecessary dependency and route setup.

## Form and state
Decision: shared non-editable surfaces and local rememberSaveable tab selection.
Rationale: matches static mockup, avoids keyboard/pickers and supports recreation.
Alternative: editable TextFields would imply unsupported input behavior.

## Layout
Decision: Scaffold footer, inset-aware scrollable body, 600dp maximum width, existing theme tokens.
Rationale: same visual language as Home; controls remain reachable in landscape and large text.
Alternative: fixed reference-image dimensions would clip on real devices.
No unresolved technical unknowns; decisions grounded in repository dependencies and existing Home code.
