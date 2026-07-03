# Executor Service

## Table of Contents
1. [Basic Concepts](#basic-concepts)
2. [Thread Pools](#thread-pools)
3. [Practical Usage](#practical-usage)
4. [Exception Handling](#exception-handling)
5. [Advanced Scenarios](#advanced-scenarios)
6. [Common Pitfalls](#common-pitfalls)

---

## Basic Concepts

### Q1: What is an Executor Service and why would you use it instead of creating threads manually?

**Answer:**

An `ExecutorService` is a high-level abstraction for managing and executing tasks asynchronously using a thread pool. Instead of creating a new `Thread` for each task, the executor manages a pool of reusable threads.

**Practical Comparison:**

```java
// ❌ Bad approach - Creating threads manually
for (int i = 0; i < 100; i++) {
    new Thread(() -> {
        // Do some work
        performHeavyTask();
    }).start();
}
// This creates 100 new threads, which is resource-intensive

// ✅ Good approach - Using ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 100; i++) {
    executor.execute(() -> performHeavyTask());
}
executor.shutdown();
```

**Why ExecutorService is better:**
- **Resource Management**: Reuses threads instead of creating new ones
- **Thread Pooling**: Limits the number of concurrent threads
- **Better Performance**: Reduces thread creation overhead (threads are expensive)
- **Simplified API**: Abstracts away thread lifecycle management
- **Built-in Task Queuing**: Automatically queues tasks when all threads are busy

**Real-world scenario:** If you're building a web server that receives 10,000 requests per second, creating a new thread per request would crash the server. With ExecutorService using a fixed pool of 50 threads, you handle requests efficiently and queue excess ones.

---

### Q2: What's the difference between `execute()` and `submit()` methods?

**Answer:**

| Method | Return Type | Exception Handling | Use Case |
|--------|-------------|-------------------|----------|
| `execute()` | void | Throws exceptions immediately | Fire-and-forget tasks |
| `submit()` | Future | Wraps exceptions in Future | Need result or exception handling |

**Practical Code:**

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

// Using execute() - fire and forget
executor.execute(() -> {
    int result = 10 / 0; // Exception happens but you won't know!
    System.out.println("Result: " + result);
});

// Using submit() - you can get the result or exception
Future<Integer> future = executor.submit(() -> {
    int result = 10 / 0; // Exception is captured
    return result;
});

try {
    Integer result = future.get(); // Exception is rethrown here
    System.out.println("Result: " + result);
} catch (ExecutionException e) {
    System.out.println("Exception occurred: " + e.getCause());
}
```

**Interviewer's perspective:** This question checks if you understand that `execute()` silently swallows exceptions, which is a common bug in production code. Using `submit()` with proper exception handling is the better practice.

---

## Thread Pools

### Q3: What are the different types of thread pools and when would you use each?

**Answer:**

```java
// 1. FixedThreadPool - Fixed number of threads
ExecutorService fixed = Executors.newFixedThreadPool(10);
// Use case: Processing tasks with predictable load, like a batch processor
// Example: Processing 1000 database records with 10 worker threads

// 2. CachedThreadPool - Creates threads as needed, reuses idle ones
ExecutorService cached = Executors.newCachedThreadPool();
// Use case: Handling variable/bursty workloads
// Example: Web server with variable request rates
// ⚠️ WARNING: Can create unlimited threads and cause OutOfMemoryError!

// 3. SingleThreadExecutor - Only 1 thread
ExecutorService single = Executors.newSingleThreadExecutor();
// Use case: Sequential task processing, ensuring tasks execute in order
// Example: Writing logs sequentially to avoid file corruption

// 4. ScheduledExecutorService - Execute tasks at scheduled times
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(5);
// Use case: Periodic tasks, delayed execution
// Example: Health checks every 5 minutes, cleanup tasks

// 5. ForkJoinPool - Divide and conquer, work-stealing algorithm
ForkJoinPool forkJoin = ForkJoinPool.commonPool();
// Use case: Large problems that can be divided into smaller subproblems
// Example: Merging large arrays, parallel sorting
```

**Practical Scenario - Production Decision:**

```java
// Scenario: Building a payment processing system
// - Variable number of payment requests (10-1000 per second)
// - Need to avoid system overload
// - Must handle all requests gracefully

// ❌ Wrong choice
ExecutorService executor = Executors.newCachedThreadPool();
// This could create 1000 threads during peak hours = CRASH

// ✅ Correct choice
ExecutorService executor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() * 2
);
// This uses a reasonable pool size based on CPU cores
```

**Interview insight:** The key is understanding the trade-offs between thread creation, queue depth, and system resources.

---

### Q4: What happens when you submit tasks to a full thread pool?

**Answer:**

When all threads in a pool are busy and you submit a new task, it gets **queued**. The behavior depends on the thread pool type:

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

// Submit 5 tasks to a pool with 2 threads
for (int i = 0; i < 5; i++) {
    final int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " started");
        Thread.sleep(2000); // Simulates 2-second work
        System.out.println("Task " + taskId + " completed");
    });
}

// Output:
// Task 0 started
// Task 1 started
// [After 2 seconds]
// Task 0 completed
// Task 2 started
// Task 1 completed
// Task 3 started
// ... and so on
```

**Real Problem - Unbounded Queue:**

```java
// ❌ DANGER: This can lead to OutOfMemoryError
ExecutorService executor = Executors.newFixedThreadPool(1);

for (int i = 0; i < 1_000_000; i++) {
    executor.submit(() -> {
        // Very short task
        Thread.sleep(1000);
    });
}
// All 1,000,000 tasks are queued in memory!
```

**Solution - Use ThreadPoolExecutor with Bounded Queue:**

```java
BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5,           // Core threads
    10,          // Max threads
    1,           // Keep-alive time
    TimeUnit.MINUTES,
    queue,
    new ThreadPoolExecutor.CallerRunsPolicy() // Handle overflow
);

for (int i = 0; i < 10_000; i++) {
    executor.submit(() -> performTask());
}
// If queue is full, the caller thread executes the task itself
```

**Interview insight:** This shows understanding of resource constraints and production-ready code.

---

## Practical Usage

### Q5: How do you properly shutdown an ExecutorService?

**Answer:**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
executor.submit(() -> System.out.println("Task 1"));
executor.submit(() -> System.out.println("Task 2"));

// ❌ WRONG - Abrupt shutdown
executor.shutdownNow(); // Interrupts all tasks, may leave work incomplete

// ✅ CORRECT - Graceful shutdown
executor.shutdown(); // No new tasks accepted
// Wait for existing tasks to complete
if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
    executor.shutdownNow(); // Force shutdown if timeout
    executor.awaitTermination(10, TimeUnit.SECONDS);
}
```

**Complete Production Pattern:**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);
try {
    for (int i = 0; i < 10; i++) {
        executor.submit(() -> {
            // Task implementation
            System.out.println("Working...");
            Thread.sleep(1000);
        });
    }
} catch (RejectedExecutionException e) {
    System.err.println("Task rejected - executor shutting down");
} finally {
    executor.shutdown();
    try {
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Forcing shutdown...");
            executor.shutdownNow();
        }
    } catch (InterruptedException e) {
        executor.shutdownNow();
        Thread.currentThread().interrupt();
    }
}
```

**Interview insight:** This shows you understand resource management and proper cleanup patterns.

---

### Q6: How do you retrieve results from ExecutorService tasks using Future?

**Answer:**

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

// Submit a task that returns a value
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);
    return 42;
});

// Get the result (blocks until available)
try {
    Integer result = future.get();
    System.out.println("Result: " + result);
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}

// Get with timeout
try {
    Integer result = future.get(5, TimeUnit.SECONDS);
    System.out.println("Result: " + result);
} catch (TimeoutException e) {
    System.out.println("Task took too long!");
    future.cancel(true); // Cancel the task
}

// Check if task is done without waiting
if (future.isDone()) {
    System.out.println("Task completed");
}
```

**Real-world Scenario - Multiple Tasks:**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);
List<Future<String>> futures = new ArrayList<>();

// Submit multiple tasks
for (int i = 1; i <= 5; i++) {
    final int userId = i;
    Future<String> future = executor.submit(() -> 
        fetchUserDataFromDB(userId)
    );
    futures.add(future);
}

// Collect results
List<String> results = new ArrayList<>();
for (Future<String> future : futures) {
    try {
        results.add(future.get());
    } catch (ExecutionException e) {
        System.err.println("Failed to fetch: " + e.getCause());
    }
}

executor.shutdown();
```

**Better Approach - Using invokeAll():**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

List<Callable<String>> tasks = new ArrayList<>();
for (int i = 1; i <= 5; i++) {
    final int userId = i;
    tasks.add(() -> fetchUserDataFromDB(userId));
}

// Execute all and wait for completion
List<Future<String>> futures = executor.invokeAll(tasks, 10, TimeUnit.SECONDS);

for (Future<String> future : futures) {
    if (future.isDone() && !future.isCancelled()) {
        System.out.println(future.get());
    }
}

executor.shutdown();
```

---

### Q7: Demonstrate a practical scenario using ExecutorService with invokeAny()

**Answer:**

`invokeAny()` executes multiple tasks and returns the result of **the first task that completes successfully**. This is useful for redundancy and failover scenarios.

```java
// Real-world scenario: Fetching data from multiple APIs, use the first that responds

ExecutorService executor = Executors.newFixedThreadPool(5);

List<Callable<String>> apiCalls = new ArrayList<>();

// API 1 - might be slow
apiCalls.add(() -> {
    Thread.sleep(5000); // Simulates slow API
    return "Data from API 1";
});

// API 2 - usually fast
apiCalls.add(() -> {
    Thread.sleep(500);
    return "Data from API 2";
});

// API 3 - backup
apiCalls.add(() -> {
    Thread.sleep(2000);
    return "Data from API 3";
});

try {
    // Returns the result of the first task to complete
    String result = executor.invokeAny(apiCalls, 10, TimeUnit.SECONDS);
    System.out.println("Fastest response: " + result);
    // Output: "Data from API 2" (completed first)
} catch (TimeoutException e) {
    System.out.println("All APIs timed out");
} catch (ExecutionException e) {
    System.out.println("All tasks failed: " + e.getCause());
}

executor.shutdown();
```

**Another Practical Example - Database Query Redundancy:**

```java
// If you have 3 database replicas and want the fastest response
ExecutorService executor = Executors.newFixedThreadPool(3);

List<Callable<ResultSet>> queries = Arrays.asList(
    () -> queryDatabaseReplica1("SELECT * FROM users"),
    () -> queryDatabaseReplica2("SELECT * FROM users"),
    () -> queryDatabaseReplica3("SELECT * FROM users")
);

try {
    ResultSet fastestResult = executor.invokeAny(queries);
    System.out.println("Using result from fastest replica");
    processResults(fastestResult);
} catch (ExecutionException | InterruptedException e) {
    System.err.println("All replicas failed");
}
```

---

## Exception Handling

### Q8: How do you properly handle exceptions in ExecutorService?

**Answer:**

Exceptions in `execute()` are swallowed. Exceptions in `submit()` are wrapped in `ExecutionException`.

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

// ❌ WRONG - Exception is silently ignored
executor.execute(() -> {
    int x = 10 / 0; // ArithmeticException - you won't know!
});

// ✅ CORRECT - Use submit() and get()
Future<Integer> future = executor.submit(() -> {
    int x = 10 / 0; // Exception is captured
    return x;
});

try {
    future.get();
} catch (ExecutionException e) {
    System.out.println("Exception: " + e.getCause().getMessage());
}
```

**Thread-safe Exception Handler:**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit task with proper exception handling
executor.submit(() -> {
    try {
        // Your work
        performDangerousOperation();
    } catch (Exception e) {
        logException(e);
        notifyAdmins(e);
    }
});

// Or better - wrapper method
executor.submit(wrapWithExceptionHandling(() -> {
    performDangerousOperation();
}));

// Exception handling wrapper
private static Runnable wrapWithExceptionHandling(Runnable task) {
    return () -> {
        try {
            task.run();
        } catch (Exception e) {
            logger.error("Task failed", e);
            metrics.incrementErrorCount();
            alerting.sendAlert(e);
        }
    };
}
```

**Using ThreadPoolExecutor with RejectedExecutionHandler:**

```java
BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5, 10, 60, TimeUnit.SECONDS, queue,
    new ThreadPoolExecutor.RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.warn("Task rejected - queue full");
            // Handle rejection - could log, discard, or run in caller thread
        }
    }
);
```

---

## Advanced Scenarios

### Q9: How would you implement a worker pool pattern with ExecutorService?

**Answer:**

A worker pool processes tasks from a shared queue. This is useful for producer-consumer patterns.

```java
public class WorkerPool {
    private final ExecutorService executor;
    private final BlockingQueue<Task> taskQueue;
    private volatile boolean running = true;

    public WorkerPool(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
        this.taskQueue = new LinkedBlockingQueue<>();
        
        // Start worker threads
        for (int i = 0; i < poolSize; i++) {
            executor.submit(this::processTasksFromQueue);
        }
    }

    private void processTasksFromQueue() {
        while (running) {
            try {
                Task task = taskQueue.poll(5, TimeUnit.SECONDS);
                if (task != null) {
                    try {
                        task.execute();
                    } catch (Exception e) {
                        logger.error("Task failed", e);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void submitTask(Task task) {
        try {
            taskQueue.put(task); // Blocks if queue is full
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

// Usage
WorkerPool pool = new WorkerPool(5);
for (int i = 0; i < 100; i++) {
    pool.submitTask(new ProcessingTask("data-" + i));
}
pool.shutdown();
```

---

### Q10: How do you use ExecutorService with CompletableFuture for complex async operations?

**Answer:**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Chain multiple async operations
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchUserData(123), executor)
    .thenApplyAsync(user -> enrichUserData(user), executor)
    .thenApplyAsync(enrichedUser -> formatForDisplay(enrichedUser), executor)
    .exceptionally(ex -> {
        logger.error("Pipeline failed", ex);
        return "Error occurred";
    });

try {
    String result = future.get(10, TimeUnit.SECONDS);
    System.out.println("Final result: " + result);
} catch (TimeoutException e) {
    future.cancel(true);
}

executor.shutdown();
```

**Practical Example - Parallel API Calls:**

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

// Fetch user and their posts in parallel
CompletableFuture<User> userFuture = 
    CompletableFuture.supplyAsync(() -> fetchUser(1), executor);

CompletableFuture<List<Post>> postsFuture = 
    CompletableFuture.supplyAsync(() -> fetchPosts(1), executor);

// Combine results
CompletableFuture<UserWithPosts> combined = userFuture.thenCombine(
    postsFuture,
    (user, posts) -> new UserWithPosts(user, posts)
);

UserWithPosts result = combined.get();
executor.shutdown();
```

---

## Common Pitfalls

### Q11: What are common mistakes developers make with ExecutorService?

**Answer:**

```java
// ❌ MISTAKE 1: Never shutdown the executor
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> System.out.println("Task"));
// Executor threads keep running! Memory leak!

// ✅ CORRECT
executor.shutdown();


// ❌ MISTAKE 2: Using cached thread pool without bounds
ExecutorService cached = Executors.newCachedThreadPool();
for (int i = 0; i < 100_000; i++) {
    cached.submit(() -> Thread.sleep(Long.MAX_VALUE));
}
// Creates 100,000 threads = OutOfMemoryError


// ❌ MISTAKE 3: Ignoring exceptions
executor.submit(() -> {
    int x = 10 / 0; // Silently fails
});

// ✅ CORRECT
Future<?> future = executor.submit(() -> {
    int x = 10 / 0;
});
try {
    future.get();
} catch (ExecutionException e) {
    logger.error("Task failed", e);
}


// ❌ MISTAKE 4: Blocking get() without timeout
Integer result = future.get(); // Can block forever!

// ✅ CORRECT
Integer result = future.get(10, TimeUnit.SECONDS);


// ❌ MISTAKE 5: Creating too many thread pools
ExecutorService pool1 = Executors.newFixedThreadPool(10);
ExecutorService pool2 = Executors.newFixedThreadPool(10);
ExecutorService pool3 = Executors.newFixedThreadPool(10);
// 30 threads total - might exhaust system resources

// ✅ CORRECT - Use a single shared pool or tune carefully
ExecutorService executor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() * 2
);
```

---

### Q12: What's the difference between FixedThreadPool and ForkJoinPool?

**Answer:**

```java
// FixedThreadPool - Work queue model
ExecutorService fixed = Executors.newFixedThreadPool(10);
// - Each thread has its own work queue
// - Better for I/O-bound tasks
// - Use when tasks are independent

fixed.submit(() -> fetchDataFromDatabase());
fixed.submit(() -> callExternalAPI());


// ForkJoinPool - Work-stealing model (divide and conquer)
ForkJoinPool forkJoin = ForkJoinPool.commonPool();
// - Threads can "steal" work from other threads' queues
// - Better for CPU-bound tasks that can be subdivided
// - Use for recursive/parallel algorithms

RecursiveTask<Long> task = new RecursiveTask<Long>() {
    protected Long compute() {
        // Divide problem into subproblems
        if (problem.size() <= THRESHOLD) {
            return solveDirectly();
        } else {
            subTask1.fork();
            long result2 = subTask2.compute();
            return subTask1.join() + result2;
        }
    }
};

long result = forkJoin.invoke(task);
```

**Comparison Table:**

| Feature | FixedThreadPool | ForkJoinPool |
|---------|-----------------|--------------|
| Best for | I/O-bound tasks | CPU-bound, recursive tasks |
| Work distribution | Queue-based | Work-stealing |
| Scalability | Fixed threads | Adaptive |
| Overhead | Low | Medium |
| Example use | Web requests, DB queries | Sorting, searching, matrix ops |

---

## Summary & Best Practices

1. **Always shutdown** ExecutorService in a finally block or try-with-resources
2. **Use submit() over execute()** to handle exceptions properly
3. **Choose the right thread pool** based on workload (I/O vs CPU-bound)
4. **Set reasonable pool sizes** - typically 2x CPU cores for I/O tasks
5. **Handle RejectedExecutionException** when submitting tasks
6. **Monitor thread pool metrics** - queue size, active threads, rejected tasks
7. **Use CompletableFuture** for complex async workflows
8. **Never block indefinitely** - always use timeouts with get()
9. **Test with load** - ensure thread pool size handles peak load
10. **Document your choice** of thread pool type in code comments

