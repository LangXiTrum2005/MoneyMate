# Feature Specification: Add transaction UI
**Created**: 2026-11-03
**Status**: Ready for implementation
**Input**: Approved plan: open the static transaction screen from Home +, matching the supplied reference.

## User Scenarios & Testing
### User Story 1 - Open and leave transaction screen (Priority: P1)
As a user I can open the transaction mockup from Home and return.
**Why this priority**: Makes the mockup accessible within the app.
**Independent Test**: Tap +, inspect screen, return using toolbar and Android Back.
**Acceptance Scenarios**:
1. Given Home, when + is tapped, then show Add transaction with Expense selected and no bottom navigation.
2. Given Add transaction, when either back action occurs, then show unchanged Home.

### User Story 2 - Preview expense and income (Priority: P2)
As a user I can switch the highlighted transaction type while inspecting the same static form.
**Why this priority**: Demonstrates both requested modes without implementing business logic.
**Independent Test**: Select Income then Expense and verify identical sample fields.
**Acceptance Scenarios**:
1. Given Expense selected, when Income is tapped, then only the selected tab changes.
2. Given Income selected, when rotating, then the screen and tab persist.
3. Given the screen was closed, when reopened, then Expense is selected.

### Edge Cases
- Small height, landscape and large fonts: form scrolls; save remains accessible above system navigation.
- Repeated taps on static fields or Save: no dialogs, keyboard, transactions or balance changes.

## Requirements
### Functional Requirements
- **FR-001**: Home + MUST open a full-page screen titled “Thêm giao dịch” with back arrow.
- **FR-002**: Screen MUST show rounded “Chi tiêu” and “Thu nhập” tabs; Expense defaults selected.
- **FR-003**: Both tabs MUST share static fields: Số tiền* = 150.000 đ; Danh mục* = Ăn uống with food icon and chevron; Ngày = Hôm nay, 20/01/2025 with calendar icon; Ghi chú (tùy chọn) = Ăn trưa với bạn.
- **FR-004**: A purple “Lưu giao dịch” button MUST sit at the safe bottom; fields and Save MUST have no data effects.
- **FR-005**: Toolbar and Android Back MUST return Home; reopening MUST reset to Expense.
- **FR-006**: Rotation MUST preserve current screen and selected tab.
- **FR-007**: Layout MUST use the reference's pale background, rounded outlined fields and lavender selection; content MUST remain reachable at large text and short heights.

## Success Criteria
### Measurable Outcomes
- **SC-001**: One tap on + opens the mockup; one back action returns Home.
- **SC-002**: All four sample fields appear identically in both modes.
- **SC-003**: Zero transactions or balance changes result from any mockup interaction.
- **SC-004**: All fields and Save are reachable at 360dp portrait, 720x360dp landscape and font scale 2.

## Assumptions
- Screenshot date is a fixed sample, not the device date.
- No keyboard, picker, validation, storage, backend or navigation library is needed.
- Native system bars are retained; no fake phone frame or status bar.
