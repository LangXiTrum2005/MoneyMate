# Data model
No persistent entities.
- Root: showAddTransaction Boolean, initially false; + sets true; either Back sets false.
- Child: incomeSelected Boolean, initially false; tabs set it; saved across recreation while screen exists.
- Static samples: amount 150.000 đ; category Ăn uống; date Hôm nay, 20/01/2025; note Ăn trưa với bạn.
- No validation rules because values cannot be edited. Save does not mutate state or data.
