# UI contract

- MoneyMateBottomBar(selectedDestination, onHome, onTransactions, onAddTransaction): Home, Transactions, +, Statistics, Settings; last two retain no-op behavior.
- HomeScreen(onAddTransaction = {}, onTransactions = {}) preserves call compatibility.
- TransactionsScreen(selectedTab, onSelectTab, onBack, onHome, onAddTransaction) displays fixed reference content; tab callback changes selection only.
- Toolbar and system Back close Add to its origin, or leave Transactions for Home and reset tab.
- Search is a display surface, not editable. Filter and rows have no data effects. Native system bars retained.
- No external API, storage contract or migration.
