# UI contract
HomeScreen(onAddTransaction: () -> Unit = {}) invokes the callback on +.
AddTransactionScreen(onBack: () -> Unit) invokes onBack on toolbar arrow.
MoneyMateApp() owns saved screen visibility and handles Android Back only on Add transaction.
Tabs expose selected semantics; back icon has Vietnamese description; decorative icons have no description.
Static fields expose readable text without editable semantics. Save is a no-op.
No external API.
