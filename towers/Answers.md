#  MCON 264 — Recursion Assignment: Towers of Hanoi × 3


##  Overview

In this lab, you implemented three recursive versions of the Towers of Hanoi problem and (optionally) one iterative version.  
Each part reinforces a key concept of recursion — base case, recursive case, and growth pattern — and illustrates how recursion can be refactored or replaced by iteration.

---

## Part 1 — Classic Towers of Hanoi (`TowersOfHanoi.java`)

### 1. Base Case
_Describe the base condition that stops recursion (for example, what happens when `n == 0`?)._

> When n == 0, there ar eno disks to move, so the function returns immediately. 
> This stops the recursion from continuing infinitely and provides the terminating condition. 

### 2. Recursive Case
_Explain the sequence of recursive calls and what each represents._

> For n >= 1, the algorithm performs: 
> 1. Move n-1 disk from 'from' -> 'aux'.
> 2. Move the largest disk (n) from 'from' -> 'to'. 
> 3. Move n-1 disks from 'aux' -> 'to'. 
> Each recursive call handles a smaller subproblem until the base case is reached. 

### 3. Sample Trace (for n = 3)

| Move # | From | To |
|:------:|:----:|:--:|
|   1    |  A   | C  |
|   2    |  A   | B  |
|   3    |  C   | B  |
|   4    |  A   | C  |
|   5    |  B   | A  |
|   6    |  B   | C  |
|   7    |  A   | C  |
|   …    |      |    |

_Total moves = 2ⁿ − 1 = 7 (for n = 3)_

---

## Part 2 — Counting Moves (`TowersExercise21.java`)

### 1. Approach
_How did you modify the standard recursion to count rather than print moves?_

> The standard recursive algorithm was modified to increment a static counter each time a disk is moved, instead of printing moves. 
> This allows measurement of recursion growth without displaying every step. 

### 2. Verification of Formula
_Complete the table and verify that count = 2ⁿ − 1._

| n | Expected (2ⁿ − 1) | Program Output | Matches? (Y/N) |
|::|:--:|:--:|:--:|
| 1 | 1 | 1 | Y |
| 2 | 3 | 3 | Y |
| 3 | 7 | 7 | Y |
| 4 | 15 | 15 | Y |
| 5 | 31 | 31 | Y |
| 6 | 63 | 63 | Y |
| 7 | 127 | 127 | Y |
| 8 | 255 | 255 | Y |
| 9 | 511 | 511 | Y |
| 10 | 1023 | 1023 | Y |

### 3. Reflection
_What changes when you replace printed moves with a counter? What are the pros and cons?_

> Replacing printed moves with a counter makes the reursion faster and clearer to analyze. 
> The advantage is efficiency (no console overhead).
> The trade-off is loss of visibility. You no longer see the actual sequence of moves, only the count. 

---

## Part 3 — Hanoi Variation (`TowersVariations.java`)

### 1. New Rule
_Every move must pass through the middle peg. How does this alter the recursion?_

> Every move must pass through the middle peg (peg 2). 
> This means a direct move like 1 -> 3 becomes two steps: 1 -> 2 and 2 -> 3. 
> The recursion must account for this constraint by doubling the action for each disk. 

### 2. Observed Move Counts

| n | Expected ≈ 3ⁿ − 1 | Program Output | Matches? (Y/N) |
|:-:|:-----------------:|:--------------:|:--------------:|
| 1 |         2         |       2        |       Y        |
| 2 |         8         |       8        |       Y        |
| 3 |        26         |       26       |       Y        |
| 4 |        80         |       80       |       Y        |
| 5 |        242        |      242       |       Y        |

### 3. Analysis
_Why does this variation grow faster than the standard version? How do additional move constraints affect complexity?_

> This variation grows faster than the standard version because each move now requires two sub-moves (via the middle peg). 
> The additional constraint increases recursion depth and total moves from 2ⁿ − 1 to approximately 3ⁿ − 1, which grows exponentially faster. 

---

## Comparative Analysis

### 1. Growth Patterns

| Version              | Approx. Formula | Growth Type                        |
|:---------------------|:--|:-----------------------------------|
| Classic              | 2ⁿ − 1 | Exponential                        |
| Counting             | 2ⁿ − 1 | Exponential (same logic, no print) |
| Variation            | 3ⁿ − 1 | Exponential (faster)               |
| Iterative (Optional) | 2ⁿ − 1 | Same as recursive but loop based   |

### 2. Stack Depth and Memory
_Estimate the maximum recursion depth before StackOverflowError and discuss how stack size (–Xss flag) affects this._

> Recursion depth equals the number of disks (n). 
> A StackOverFlowError occurs if n becomes extrememly large (usually around 10,000 recursive calls, depending on the JVM's -Xss stack size).
> Each recursive call stores local variables and returns addresses on the stack, consuming memory per frame. 

---

## Part 4 — Beyond Recursion (Extra Credit)

### Iterative / Explicit-Stack Version (`TowersIterative.java`)

1. How does your iterative version simulate recursion?
2. How did you track pending calls or frames?
3. Which version (r vs iterative) is clearer? Why?

> 1. How does your iterative version simulate recursion?
>   - It replaces the call stack with an explicit Stack<Frame>. Each Frame stores the parameters 
      for a "call" - (n, from, aux, to) - plus a small stage field so we can resume work after the first subcall
      (exactly like returning to a caller). The loop repeatedly pops a frame, and: 
    - If n==0: skip (base case), 
    - otherwise push frames in reverse order of the recursive steps: 
        - 1. (n-1, aux, from, to) (stage after the move),
        - 2. a marker / stage-2 frame to perform the single move (or count++), 
        - 3. (n-1, from, to, aux) (first subproblem). 
          This produces the same order of actions as the recursive solution without using the JVM call stack.

---

## Discussion &amp; Reflection

1. What three questions can you use to verify a recursive solution works?
2. How do the base case and recursive case cooperate to guarantee termination?
3. What is the trade-off between elegance and efficiency in recursion?

> 1. Three questions to verify recursion works: 
>    - Does the algorithm have a valid base case? 
>    - Does each recursive call handle a smaller problem? 
>    - Assuming smaller cases work, does the general case work too? 
> 2. Base vs Recursive Cooperation: 
>    - The base case garantees termination. 
>    - The recursive case reduces the problem toward the base case. 
> 3. Elegance vs Efficiency: 
>    - Reursion is elegant and matches the mathematical structure of the problem. 
>    - Iteration is more memory-efficient but harder to visualize. 
>    - The recursive approach best demonstrates the logical flow of problem decomposition. 

---

## References (APA or MLA format)

- Dale, N., Joyce, D., &amp; Weems, C. (2016). *Object-Oriented Data Structures Using Java* (Ch. 3 Recursion). Jones &amp; Bartlett.
- Additional videos or websites you consulted (YouTube CS50 Recursion, GeeksForGeeks Hanoi, etc.)
- Downey, A. B. (2016). Think Data Structures: Algorithms and Information Retrieval in Java. Green Tea Press.
- GeeksforGeeks (n.d.). Tower of Hanoi Recursive Explanation.
- CS50 YouTube. (n.d.). Recursion and Stack Frames Visualization.

---

 **Submission Checklist**

- [ ] `TowersOfHanoi.java` — implemented and tested
- [ ] `TowersExercise21.java` — counts moves correctly
- [ ] `TowersVariations.java` — middle-peg constraint implemented
- [ ] (Extra Credit) `TowersIterative.java` — loop or explicit stack solution
- [ ] All JUnit tests pass (green on GitHub Actions)
- [ ] This `ANSWERS.md` file completed and committed to repo  
