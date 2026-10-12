# Merge Sort on { 40, 10, 60, 5, 30, 15, 50, 20 }

---

## The Full Code

```java
import java.util.Arrays;

public class MergeSort {

    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) return array;

        int mid = array.length / 2;

        int[] left  = mergeSort(Arrays.copyOfRange(array, 0,   mid));
        int[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length));

        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int leftIndex = 0, rightIndex = 0, resultIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex  < left.length)  result[resultIndex++] = left[leftIndex++];
        while (rightIndex < right.length) result[resultIndex++] = right[rightIndex++];

        return result;
    }

    public static void main(String[] args) {
        int[] numbers = { 40, 10, 60, 5, 30, 15, 50, 20 };

        System.out.println("Before: " + Arrays.toString(numbers));
        int[] sorted = mergeSort(numbers);
        System.out.println("After:  " + Arrays.toString(sorted));
    }
}
```

---

## Step 1 — Splitting

This code finds the middle and splits the array in two, then keeps calling itself on each half:

```java
int mid = array.length / 2;

int[] left  = mergeSort(Arrays.copyOfRange(array, 0,   mid));
int[] right = mergeSort(Arrays.copyOfRange(array, mid, array.length));
```

```
[40, 10, 60, 5, 30, 15, 50, 20]
          /                 \
  [40, 10, 60, 5]      [30, 15, 50, 20]       
      /       \              /        \
  [40, 10]  [60, 5]    [30, 15]    [50, 20]  
   /    \    /    \     /    \      /     \
 [40]  [10][60]  [5] [30]  [15]  [50]   [20]  
```

---

## Step 2 — Base Case (single elements)

When an array has only 1 element, this line fires and returns it immediately — nothing left to split:

```java
if (array.length <= 1) return array;
```

```
[40]  [10]  [60]  [5]  [30]  [15]  [50]  [20]
```

---

## Step 3 — Merging

Once both halves are sorted, this line kicks off the merge:

```java
return merge(left, right);
```

Inside merge(), this loop compares the front of each half and picks the smaller one:

```java
while (leftIndex < left.length && rightIndex < right.length) {
    if (left[leftIndex] <= right[rightIndex]) {
        result[resultIndex++] = left[leftIndex++];
    } else {
        result[resultIndex++] = right[rightIndex++];
    }
}
```

When one half runs out of elements, these lines pour the rest of the other half straight in:

```java
while (leftIndex  < left.length)  result[resultIndex++] = left[leftIndex++];
while (rightIndex < right.length) result[resultIndex++] = right[rightIndex++];
```

### Round 1

```
[40] + [10]  →  40 vs 10  →  picks 10, pours 40  →  [10, 40]
[60] + [5]   →  60 vs 5   →  picks 5,  pours 60  →  [5, 60]
[30] + [15]  →  30 vs 15  →  picks 15, pours 30  →  [15, 30]
[50] + [20]  →  50 vs 20  →  picks 20, pours 50  →  [20, 50]
```

### Round 2

```
[10, 40] + [5, 60]
  10 vs 5  → pick 5    (compare loop)
  10 vs 60 → pick 10   (compare loop)
  40 vs 60 → pick 40   (compare loop)
             pour 60   (leftover loop)
             → [5, 10, 40, 60]

[15, 30] + [20, 50]
  15 vs 20 → pick 15   (compare loop)
  30 vs 20 → pick 20   (compare loop)
  30 vs 50 → pick 30   (compare loop)
             pour 50   (leftover loop)
             → [15, 20, 30, 50]
```

### Round 3 — Final Merge

```
[5, 10, 40, 60] + [15, 20, 30, 50]

  5  vs 15 → pick 5    (compare loop)
  10 vs 15 → pick 10   (compare loop)
  40 vs 15 → pick 15   (compare loop)
  40 vs 20 → pick 20   (compare loop)
  40 vs 30 → pick 30   (compare loop)
  40 vs 50 → pick 40   (compare loop)
  60 vs 50 → pick 50   (compare loop)
             pour 60   (leftover loop)

→ [5, 10, 15, 20, 30, 40, 50, 60] ✅
```
## Why is it O(n log n)?

Two simple questions answer this:

**Question 1 — How many rounds of merging are there?**

We start with 8 elements and keep halving until we reach 1:

```
8 → 4 → 2 → 1
```

That took 3 steps. 8 halved 3 times = **3 rounds**.

In general, however many times you can halve n before reaching 1 is called **log n**.
For 8 elements, log₂(8) = **3 rounds**.
 
---

**Question 2 — How much work happens in each round?**

Look at our example — every round touches all 8 elements exactly once:

```
Round 1:  [40][10]  [60][5]  [30][15]  [50][20]  →  8 elements touched
Round 2:  [10,40]+[5,60]   [15,30]+[20,50]       →  8 elements touched
Round 3:  [5,10,40,60] + [15,20,30,50]           →  8 elements touched
```

Every round does **n** comparisons (roughly).
 
---

**Put them together:**

```
Total work = rounds × work per round
           = log n  ×       n
           = n log n
```

> For our 8 elements: 3 rounds × 8 elements = **24** maximum comparisons.
 
---

**Why is this fast?**

Because halving is powerful. No matter how big the array gets, the number of rounds barely grows:

| Elements | Rounds (log n) |
|----------|----------------|
| 8        | 3              |
| 1,000    | 10             |
| 1,000,000| 20             |

Even with a million elements, you only need **20 rounds**. That's why merge sort is fast.