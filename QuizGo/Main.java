import java.util.*;

public class Main {
    static final Scanner in = new Scanner(System.in);
    static final Map<String, String> users = new HashMap<>();
    static final List<Question> questions = new ArrayList<>();
    static final List<Result> results = new ArrayList<>();
    static final int QUIZ_SIZE = 5, TIME_PER_Q_SEC = 15;

    static class Question {
        String t, a, b, c, d, level; int correct;
        Question(String t, String a, String b, String c, String d, int k, String l) {
            this.t = t; this.a = a; this.b = b; this.c = c; this.d = d; correct = k; level = l;
        }
    }
    static class Result {
        String user, grade; int score, total; double pct;
        Result(String u, int s, int t, double p, String g) { user = u; score = s; total = t; pct = p; grade = g; }
    }

    public static void main(String[] args) {
        seed();
        while (true) {
            System.out.println("\n=== Online Quiz & Assessment ===\n1.Register\n2.Login\n3.Exit");
            int ch = readInt("Choose: ", 1, 3);
            if (ch == 1) register();
            else if (ch == 2) { String u = login(); if (u != null) menu(u); }
            else break;
        }
    }

    static void seed() {
        users.put("admin", "admin123");
        questions.add(new Question("Java inheritance keyword?", "this", "extends", "super", "implements", 2, "Easy"));
        questions.add(new Question("Collection with no duplicates?", "ArrayList", "HashSet", "Vector", "Stack", 2, "Easy"));
        questions.add(new Question("Block always runs in exception handling?", "try", "catch", "throw", "finally", 4, "Medium"));
        questions.add(new Question("Method that starts a thread?", "run", "start", "execute", "init", 2, "Medium"));
        questions.add(new Question("Time complexity of binary search?", "O(n)", "O(log n)", "O(n log n)", "O(1)", 2, "Hard"));
    }

    static void register() {
        String u = readText("Username: ");
        if (users.containsKey(u)) { System.out.println("Username exists."); return; }
        users.put(u, readText("Password: ")); System.out.println("Registered.");
    }
    static String login() {
        String u = readText("Username: "), p = readText("Password: ");
        if (!users.containsKey(u) || !users.get(u).equals(p)) { System.out.println("Invalid credentials."); return null; }
        return u;
    }

    static void menu(String user) {
        while (true) {
            if (user.equals("admin")) System.out.println("\n1.Take Quiz\n2.Add Question\n3.My Results\n4.Logout");
            else System.out.println("\n1.Take Quiz\n2.My Results\n3.Logout");
            int ch = readInt("Choose: ", 1, user.equals("admin") ? 4 : 3);
            if (ch == 1) takeQuiz(user);
            else if (user.equals("admin") && ch == 2) addQuestion();
            else if ((user.equals("admin") && ch == 3) || (!user.equals("admin") && ch == 2)) showResults(user);
            else return;
        }
    }

    static void addQuestion() {
        String t = readText("Question: "), a = readText("Opt1: "), b = readText("Opt2: "), c = readText("Opt3: "), d = readText("Opt4: ");
        int k = readInt("Correct option (1-4): ", 1, 4);
        String lv = readText("Level (Easy/Medium/Hard): ");
        questions.add(new Question(t, a, b, c, d, k, lv));
        System.out.println("Question added.");
    }

    static void takeQuiz(String user) {
        if (questions.isEmpty()) { System.out.println("No questions available."); return; }
        List<Question> list = new ArrayList<>(questions); Collections.shuffle(list);
        int total = Math.min(QUIZ_SIZE, list.size()), score = 0, correctEasy = 0, correctMH = 0;
        for (int i = 0; i < total; i++) {
            Question q = list.get(i);
            System.out.println("\nQ" + (i + 1) + " [" + q.level + "] (" + TIME_PER_Q_SEC + "s): " + q.t);
            System.out.println("1." + q.a + "  2." + q.b + "  3." + q.c + "  4." + q.d);
            long st = System.currentTimeMillis();
            int ans = readInt("Your answer: ", 1, 4);
            long sec = (System.currentTimeMillis() - st) / 1000;
            if (sec > TIME_PER_Q_SEC) { System.out.println("Time up. 0 marks."); continue; }
            if (ans == q.correct) {
                score++;
                if (q.level.equalsIgnoreCase("Easy")) correctEasy++; else correctMH++;
            }
        }
        double pct = Math.round(score * 10000.0 / total) / 100.0;
        String grade = pct >= 90 ? "A" : pct >= 75 ? "B" : pct >= 60 ? "C" : pct >= 40 ? "D" : "F";
        results.add(new Result(user, score, total, pct, grade));
        System.out.println("\nScore: " + score + "/" + total + "  (" + pct + "%)  Grade: " + grade);
        System.out.println("Analysis: Easy correct=" + correctEasy + ", Medium/Hard correct=" + correctMH +
                ", Remark=" + (pct >= 75 ? "Strong" : pct >= 50 ? "Average" : "Needs improvement"));
    }

    static void showResults(String user) {
        boolean found = false;
        for (Result r : results) if (r.user.equals(user)) {
            found = true; System.out.println(r.score + "/" + r.total + " | " + r.pct + "% | Grade " + r.grade);
        }
        if (!found) System.out.println("No attempts yet.");
    }

    static int readInt(String p, int lo, int hi) {
        while (true) {
            try {
                System.out.print(p); int v = Integer.parseInt(in.nextLine().trim());
                if (v >= lo && v <= hi) return v;
            } catch (Exception ignored) {}
            System.out.println("Enter " + lo + "-" + hi + ".");
        }
    }
    static String readText(String p) {
        while (true) { System.out.print(p); String s = in.nextLine().trim(); if (!s.isEmpty()) return s; }
    }
}
