# Simple Java Quiz App
This is a simple console-based Java quiz application built in a single file, making it easy to understand and explain.It asks five random Java questions, calculates score and percentage, and assigns a grade from A to F.
Users can view their own quiz history and check a top-10 leaderboard based on performance.The app uses clean menu flow, input validation, and in-memory storage, so data resets each time the program restarts..

## What It Does
- Take a quiz with 5 random Java questions
- See your score, percentage, and grade (A/B/C/D/F)
- View your own quiz history
- See the leaderboard (top 10 users)

## How To Run
From the project folder:
```powershell
javac Main.java
java Main
```

## Features
- Simple menu-driven interface
- No login required (just enter your name)
- In-memory data (resets each time you run)
- 102 lines of clean code

## Interview Explanation
1. **Main loop**: Menu (lines 31-38)
2. **takeQuiz**: Shuffle questions, ask, score, grade (lines 48-62)
3. **viewHistory**: Show user's attempts (lines 64-71)
4. **leaderboard**: Show top 10 users (lines 73-83)
5. **Input helpers**: Validate int and text inputs (lines 85-102)

## Note About .class Files
When you run `javac Main.java`, Java creates `.class` files automatically. This is normal and unavoidable. These are compiled bytecode that the JVM runs. To clean up after running:
```powershell
del *.class
```

## Default Quiz Questions
- What is Java inheritance keyword?
- Which collection has no duplicates?
- Exception handling block?
- What is JVM?
- How to start a thread?

