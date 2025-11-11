package exercises;

import java.util.Scanner;

/**
 * Exercise 23 — Towers of Hanoi Variation
 * <p>
 * From: Object-Oriented Data Structures Using Java (Ch. 3 Recursion)
 * <p>
 * PROBLEM DESCRIPTION:
 * In this variation of the Towers of Hanoi puzzle, **every move must use the middle peg**.
 * If the pegs are labeled 1, 2, and 3 (left → right),
 * every disk move must either go:
 * 1 → 2,  2 → 1,  2 → 3, or  3 → 2.
 * You can never move directly between pegs 1 and 3.
 * <p>
 * EXAMPLE (for 2 rings):
 * move 1 from 1 → 2
 * move 1 from 2 → 3
 * move 2 from 1 → 2
 * move 1 from 3 → 2
 * move 1 from 2 → 1
 * move 2 from 2 → 3
 * move 1 from 1 → 2
 * move 1 from 2 → 3
 * <p>
 * LEARNING GOALS:
 * - Recognize that additional constraints change the recursive pattern.
 * - Modify the recursion to account for “must pass through middle peg.”
 * - Derive and verify the new move-count growth rate.
 * <p>
 * TASKS:
 * 1. Prompt the user for the number of rings (n).
 * 2. If n < 0 → quit.
 * 3. Otherwise, call a recursive method that
 * a) Prints or counts each move.
 * b) Ensures every move uses the middle peg.
 * 4. Report the total number of moves.
 * <p>
 * Hint: Try expressing this variation in terms of TWO standard-style
 * recursive calls — one to move from start to middle, and another
 * from middle to destination.
 * <p>
 * Base case: n == 0 → no moves.
 */
public class TowersVariations {

    // static counter for number of moves
    private static int count = 0;

    /**
     * Recursive method to solve the Towers of Hanoi variation.
     *
     * @param n    number of disks
     * @param from starting peg label (1, 2, or 3)
     * @param mid  middle peg (must be used for every move)
     * @param to   destination peg label
     */
    public static void solveVariation(int n, int from, int mid, int to) {
        // TODO 1: Base case — if n == 0 → return.

        // TODO 2: Recursive case —
        //  a) Move n-1 disks from 'from' to 'mid' (using 'to' as helper)
        //  b) Move disk n from 'from' to 'to' — BUT must go through 'mid':
        //        System.out.printf("Move disk %d: %d → %d → %d%n", n, from, mid, to);
        //     or if counting only: count += 2; // because two moves required
        //  c) Move n-1 disks from 'mid' to 'to' (using 'from' as helper)

        // Base case: no disks move
        if (n == 0) return;

        // Step 1: move n-1 disks from 'from' -> 'mid' (using 'to' as helper)
        solveVariation(n - 1, from, to, mid);

        // Step 2: move disk n from 'from' -> 'mid'
        count++;

        // Step 3: Move n-1 from 'to' back to 'from' (still constrained)
        solveVariation(n - 1, to, mid, from);

        // Step 4: move disk n from 'mid' -> 'to'
        count++;

        // Step 5: move n-1 disks from 'mid' -> 'to' (using 'from' as helper)
        solveVariation(n -1, from, mid, to);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Enter number of rings (negative to quit): ");
            int n = in.nextInt();
            if (n < 0) {
                System.out.println("Goodbye!");
                break;
            }

            count = 0; // reset count
            solveVariation(n, 1, 2, 3);
            System.out.printf("Number of moves (variation) = %d%n", count);
        }
        in.close();
    }
}

