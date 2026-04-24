import csv
import datetime as dt
from collections import defaultdict
from pathlib import Path
import matplotlib.pyplot as plt

FILE = Path("expenses.csv")
CATEGORIES = ["Food", "Travel", "Bills", "Shopping", "Health", "Other"]
FIELDS = ["date", "category", "amount", "description"]


def parse_date(date_str):
    for fmt in ("%d-%m-%Y", "%Y-%m-%d"):
        try:
            return dt.datetime.strptime(date_str, fmt).date()
        except ValueError:
            continue
    return None


def init_file():
    if not FILE.exists():
        with FILE.open("w", newline="", encoding="utf-8") as f:
            csv.DictWriter(f, fieldnames=FIELDS).writeheader()


def read_expenses():
    init_file()
    with FILE.open("r", newline="", encoding="utf-8") as f:
        data = list(csv.DictReader(f))
    for r in data:
        r["amount"] = float(r["amount"])
    return data


def add_expense():
    date = input("Date (DD-MM-YYYY, Enter for today): ").strip() or dt.date.today().strftime("%d-%m-%Y")
    if not parse_date(date):
        print("Invalid date format.")
        return
    print("Categories:", ", ".join(CATEGORIES))
    category = input("Category: ").strip().title()
    if category not in CATEGORIES:
        category = "Other"
    try:
        amount = float(input("Amount: ").strip())
        if amount <= 0:
            raise ValueError
    except ValueError:
        print("Amount must be a positive number.")
        return
    description = input("Description: ").strip() or "-"
    init_file()
    with FILE.open("a", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=FIELDS)
        writer.writerow({"date": date, "category": category, "amount": f"{amount:.2f}", "description": description})
    print("Expense saved.")


def month_filter(rows, ym):
    try:
        target = dt.datetime.strptime(ym, "%m-%Y")
    except ValueError:
        return []
    out = []
    for r in rows:
        date_obj = parse_date(r["date"])
        if date_obj and date_obj.month == target.month and date_obj.year == target.year:
            out.append(r)
    return out


def summary(rows):
    total = sum(r["amount"] for r in rows)
    by_cat = defaultdict(float)
    for r in rows:
        by_cat[r["category"]] += r["amount"]
    return total, dict(by_cat)


def show_summary():
    ym = input("Month (MM-YYYY, Enter for current): ").strip() or dt.date.today().strftime("%m-%Y")
    rows = month_filter(read_expenses(), ym)
    if not rows:
        print("No expenses found for", ym)
        return
    total, by_cat = summary(rows)
    print(f"\nMonthly Summary: {ym}")
    print(f"Total Spending: Rs. {total:.2f}")
    print("Category Breakdown:")
    for k, v in sorted(by_cat.items(), key=lambda x: x[1], reverse=True):
        print(f"- {k:<10} Rs. {v:>8.2f} ({(v / total) * 100:>5.1f}%)")
    top_cat, top_amt = max(by_cat.items(), key=lambda x: x[1])
    print(f"Highest Spending Category: {top_cat} (Rs. {top_amt:.2f})")
    if top_amt / total > 0.4:
        print(f"Insight: '{top_cat}' is over 40% of your spending. Consider reducing this area.")


def plot_pie():
    ym = input("Month for chart (MM-YYYY, Enter for current): ").strip() or dt.date.today().strftime("%m-%Y")
    rows = month_filter(read_expenses(), ym)
    if not rows:
        print("No expenses found for", ym)
        return
    total, by_cat = summary(rows)
    labels = list(by_cat.keys())
    values = list(by_cat.values())
    plt.figure(figsize=(6, 6))
    plt.pie(values, labels=labels, autopct="%1.1f%%", startangle=120)
    plt.title(f"Expense Breakdown - {ym} | Total Rs. {total:.2f}")
    plt.tight_layout()
    plt.show()


def main():
    menu = {
        "1": ("Add Expense", add_expense),
        "2": ("Monthly Summary", show_summary),
        "3": ("Category Pie Chart", plot_pie),
        "4": ("Exit", None),
    }
    while True:
        print("\n=== Smart Expense Tracker ===")
        for k, (name, _) in menu.items():
            print(f"{k}. {name}")
        choice = input("Select option: ").strip()
        if choice == "4":
            print("Goodbye.")
            break
        action = menu.get(choice, (None, None))[1]
        action() if action else print("Invalid choice.")


if __name__ == "__main__":
    main()
