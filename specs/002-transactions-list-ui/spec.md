# Feature Specification: Transactions list UI

**Created**: 2026-11-03
**Status**: Implemented and validated
**Input**: Approved Vietnamese plan and screenshot: open a static transaction list from bottom navigation.

## User Scenarios & Testing

### User Story 1 - Browse sample transactions (Priority: P1)
As a user I can tap Giao dịch to inspect the reference transaction list.
**Why this priority**: Provides the requested destination and visual content.
**Independent Test**: Tap Giao dịch, inspect all four rows and use either Back action.
**Acceptance Scenarios**:
1. Given Home, when Giao dịch is tapped, then show the list with Giao dịch selected in the bottom bar.
2. Given the list, when Trang chủ, toolbar Back or Android Back is used, then return Home.
3. Given the list, when search, filter or a row is tapped, then no keyboard, dialog or data change occurs.

### User Story 2 - Preview tabs and add flow (Priority: P2)
As a user I can preview tab selection and open the existing add mockup without losing my place.
**Why this priority**: Makes navigation consistent with Home and preserves visual state.
**Independent Test**: Select Thu nhập, open +, recreate, return, then leave and reopen the list.
**Acceptance Scenarios**:
1. Given any tab, when another is selected, then only the selected styling changes; all four rows remain.
2. Given a selected tab, when the device rotates, then the destination and selected tab remain.
3. Given the list, when + is tapped, then the existing add screen opens without bottom navigation; either Back action returns to the list with the tab preserved.
4. Given the list was left for Home, when reopened, then Tất cả is selected.
5. Given Home, when + and Back are used, then Home returns as before.

### Edge Cases
- Small portrait, landscape and 2x fonts: all content remains reachable and does not overlap the fixed bottom bar.
- Repeated taps on tabs or navigation do not stack duplicate screens.
- System bars remain native and controls remain clear of system insets.

## Requirements

### Functional Requirements
- **FR-001**: Show Giao dịch with a back arrow, static Tìm kiếm giao dịch... search surface, Tất cả / Chi tiêu / Thu nhập tabs, and filter icon.
- **FR-002**: Show Tháng 1, 2025, Thu: +15.000.000 đ and Chi: -2.550.000 đ.
- **FR-003**: Show Ăn uống / Hôm nay, 12:10 / -80.000 đ; Di chuyển / Hôm qua, 18:30 / -50.000 đ; Lương / 18/01/2025 / +15.000.000 đ; Mua sắm / 16/01/2025 / -300.000 đ, in that order.
- **FR-004**: Use the reference pale background, purple selection, category badges with vector icons, green income and red expenses with explicit signs.
- **FR-005**: Default to Tất cả; selecting tabs MUST change only selection and MUST survive rotation and the add round trip. Returning Home and reopening MUST reset selection.
- **FR-006**: Bottom navigation MUST be shared with Home; Home and Transactions navigate, + opens the existing add mockup, Statistics and Settings retain current behavior.
- **FR-007**: Both Back actions MUST return list to Home and add to its originating destination. The add screen MUST keep its existing static behavior.
- **FR-008**: Search, filter and rows MUST have no effects; no storage, network, keyboard or transaction creation is introduced.
- **FR-009**: Content MUST scroll above bottom navigation and remain usable at 360dp portrait, 720x360dp landscape and font scale 2.

### Key Entities
- Sample transaction: category, date/time label, signed display amount and category icon/color.
- Navigation state: active primary destination, add-screen visibility, selected preview tab.

## Success Criteria
### Measurable Outcomes
- **SC-001**: One tap opens the list; one Back action returns to Home.
- **SC-002**: All four sample rows and both summary amounts match the reference text in every tab.
- **SC-003**: Zero data or balance changes occur across all mockup interactions.
- **SC-004**: Rotation preserves destination and tab; both add Back paths return to their origin.
- **SC-005**: All list content can be reached in the three required layout configurations.

## Assumptions
- The user selected a static mockup and no row action.
- Dates and totals are fixed samples, independent of the device date and displayed row sums.
- Existing light theme, native system bars and offline operation are retained; no new dependencies or detail screen.
