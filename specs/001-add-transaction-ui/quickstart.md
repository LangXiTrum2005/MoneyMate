# Quickstart validation
1. Set JAVA_HOME to Android Studio jbr if Java is not on PATH.
2. Run ./gradlew.bat :app:assembleDebug :app:testDebugUnitTest.
3. With an emulator/device connected, run ./gradlew.bat :app:connectedDebugAndroidTest.
4. Launch MoneyMate; tap +. Expect title, Expense selected, four sample fields, purple save and no bottom tabs.
5. Switch Income and Expense: only selection changes. Tap fields/Save: no keyboard, dialog or data effect.
6. Rotate on Income: same screen and selection. Toolbar Back returns Home. Reopen: Expense defaults selected.
7. Android Back returns Home with original balance.
8. Inspect at 360dp phone, 720x360dp landscape and 2x font: scroll fields into view, Save accessible, system bars clear.
