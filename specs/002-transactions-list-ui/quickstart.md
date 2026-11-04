# Quickstart validation

Prerequisites: Android Studio JBR, installed SDK and emulator/device for connected tests. Set JAVA_HOME to JBR if needed.

```powershell
.\gradlew.bat assembleDebug testDebugUnitTest
.\gradlew.bat connectedDebugAndroidTest
```

1. Home -> Giao dịch: inspect four rows, summary and selected bottom item.
2. Select tabs: all sample data remains unchanged. Search/filter/rows open no keyboard or dialog.
3. Recreate: destination and selected tab persist.
4. Open + and return using both Back paths: origin/tab remain. Repeat from Home.
5. Leave list for Home and reopen: All selected.
6. At 360dp portrait, 720x360dp landscape and font scale 2: scroll to last row, inspect overlap/clipping/insets.

Actual results and limitations are recorded in validation.md.
