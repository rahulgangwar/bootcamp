# Merge Sort Visualization with Pseudocode

# Example Input

```text
[38, 27, 43, 10]
```

---

# High-Level Idea

Merge Sort works in 2 phases:

1. Divide
2. Merge

---

# Pseudocode

```text
MERGE_SORT(arr, left, right)

1. IF left < right

2.     mid = (left + right) / 2

3.     MERGE_SORT(arr, left, mid)

4.     MERGE_SORT(arr, mid + 1, right)

5.     MERGE(arr, left, mid, right)
```

---

# Step 1 → Divide Whole Array

## Pseudocode Mapping

```text
Step 1:
IF left < right
mid = (left + right) / 2
```

Visualization:

```text
[38, 27, 43, 10]
        ↓
Left  = [38, 27]
Right = [43, 10]
```

---

# Step 2 → Divide Left Half

## Pseudocode Mapping

```text
MERGE_SORT(arr, left, mid)
```

Visualization:

```text
[38, 27]
   ↓
[38]   [27]
```

Single elements are already sorted.

---

# Step 3 → Divide Right Half

## Pseudocode Mapping

```text
MERGE_SORT(arr, mid + 1, right)
```

Visualization:

```text
[43, 10]
   ↓
[43]   [10]
```

Single elements are already sorted.

---

# Recursion Tree

```text
                    [38,27,43,10]
                     /          \
              [38,27]          [43,10]
               /    \            /    \
            [38]   [27]       [43]   [10]
```

---

# Merge Phase Begins

---

# Step 4 → Merge [38] and [27]

## Pseudocode Mapping

```text
MERGE(arr, left, mid, right)

Compare left element with right element
Place smaller element into original array
```

Visualization:

```text
L = [38]
R = [27]

38 <= 27 ? NO
```

Place 27 first:

```text
[27]
```

Copy remaining 38:

```text
[27, 38]
```

---

# Step 5 → Merge [43] and [10]

## Pseudocode Mapping

```text
Compare elements
Place smaller one first
```

Visualization:

```text
L = [43]
R = [10]

43 <= 10 ? NO
```

Place 10 first:

```text
[10]
```

Copy remaining 43:

```text
[10, 43]
```

---

# Step 6 → Merge Final Two Sorted Halves

```text
Left  = [27, 38]
Right = [10, 43]
```

---

# Comparison 1

## Pseudocode Mapping

```text
IF L[i] <= R[j]
    place L[i]
ELSE
    place R[j]
```

Visualization:

```text
27 <= 10 ? NO
```

Place 10:

```text
[10]
```

---

# Comparison 2

```text
27 <= 43 ? YES
```

Place 27:

```text
[10, 27]
```

---

# Comparison 3

```text
38 <= 43 ? YES
```

Place 38:

```text
[10, 27, 38]
```

---

# Copy Remaining Elements

## Pseudocode Mapping

```text
WHILE elements remain
    copy remaining elements
```

Visualization:

```text
43
```

Final Result:

```text
[10, 27, 38, 43]
```

---

# Final Recursion Tree

```text
                    [38,27,43,10]
                     /          \
              [38,27]          [43,10]
               /    \            /    \
            [38]   [27]       [43]   [10]
               \    /            \    /
             [27,38]           [10,43]
                    \          /
                 [10,27,38,43]
```

---

# Call Stack Flow

```text
mergeSort(0,3)
 |
 +-- mergeSort(0,1)
 |     |
 |     +-- mergeSort(0,0)
 |     |
 |     +-- mergeSort(1,1)
 |     |
 |     +-- merge(0,0,1)
 |
 +-- mergeSort(2,3)
 |     |
 |     +-- mergeSort(2,2)
 |     |
 |     +-- mergeSort(3,3)
 |     |
 |     +-- merge(2,2,3)
 |
 +-- merge(0,1,3)
```

---

# Merge Sort Complexity

## Time Complexity

```text
O(n log n)
```

Reason:

- Array divides into log n levels
- Each level processes n elements

---

# Space Complexity

```text
O(n)
```

Reason:

Temporary arrays are created during merging.