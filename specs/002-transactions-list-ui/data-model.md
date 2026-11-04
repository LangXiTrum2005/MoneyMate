# Data model

- Primary destination: Home or Transactions, saved across recreation.
- Add visibility: saved Boolean; underlying destination remains unchanged.
- Preview tab: saved integer 0..2 (All, Expense, Income), reset on Home navigation.
- Sample row: stable category resource ID, date label resource ID, signed amount resource ID, vector ID, badge background/tint. Immutable display data.

Transitions: Home -> Transactions; either primary -> Add -> origin; Transactions -> Home resets preview tab. Tab selection changes no rows/totals. Existing Add local type resets on reopening.
No database schema or data validation needed.
