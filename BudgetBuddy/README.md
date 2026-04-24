# Smart Expense Tracker (Python CLI)
A Python project to record daily expenses, categorize spending, and generate monthly insights for organized and structural spendings track to improve personal financial budgeting.

## Why this project?
Many people spend money daily but do not know where most of it goes. This tool helps by:
- logging every expense quickly,
- grouping spend by category,
- showing monthly totals,
- highlighting the highest spending category,
- visualizing category distribution with a pie chart.

## Features
- CLI-based input system
- Expense fields: `date`, `category`, `amount`, `description`
- CSV data storage (`expenses.csv`)
- Monthly summary by `MM-YYYY`
- Category-wise percentage breakdown
- Highest spending category detection
- Pie chart using `matplotlib`
- Basic insight: warns when one category is over 40% of monthly spend

## Project structure
- `expense_tracker.py` -> main application
- `expenses.csv` -> auto-created data file when first entry is saved

## Tech used
- Python 3
- Standard library: `csv`, `datetime`, `collections`, `pathlib`
- External library: `matplotlib`

## How to run
1. Install dependency:
   ```bash
   pip install matplotlib
   ```
2. Run:
   ```bash
   python expense_tracker.py
   ```

## Menu flow
1. **Add Expense**
   - Enter date (or press Enter for today)
   - Enter category (`Food`, `Travel`, `Bills`, `Shopping`, `Health`, `Other`)
   - Enter amount and description
2. **Monthly Summary**
   - Enter month like `04-2026`
   - See total spend, category shares, and top category
3. **Category Pie Chart**
   - Enter month
   - View visual split by category

## Explanation (short)
I built a CLI expense tracker in Python using CSV storage to keep it lightweight and easy to understand. The app supports adding expenses with metadata, then filters data by month to compute totals and category-level percentages. I also added a pie chart for visual analytics and a simple rule-based insight to flag overspending categories. The design is modular with small functions for input, filtering, aggregation, and plotting.

## Validation rules included
- Date must follow `DD-MM-YYYY`
- Amount must be positive
- Unknown categories are mapped to `Other`


