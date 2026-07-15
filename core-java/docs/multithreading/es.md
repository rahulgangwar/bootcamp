# ExecutorService

> **Difficulty:** ⭐⭐⭐☆☆
>
> **Interview Frequency:** ⭐⭐⭐⭐⭐
>
> **Prerequisites:**
> - Threads
> - Runnable
> - Callable
> - Thread Lifecycle
> - Thread Interruption
> - Basic Synchronization

---

# Table of Contents

- [1. Introduction](#1-introduction)
- [2. Why was ExecutorService Introduced?](#2-why-was-executorservice-introduced)
- [3. Problems with Creating Threads Manually](#3-problems-with-creating-threads-manually)
- [4. Mental Model](#4-mental-model)
- [5. Architecture](#5-architecture)
- [6. Key Components](#6-key-components)
- [7. Interview Questions](#7-interview-questions)

---

# 1. Introduction

`ExecutorService` is a high-level concurrency framework introduced in Java 5 as part of the `java.util.concurrent` package.

It provides a mechanism to execute asynchronous tasks **without manually creating and managing threads**.

Instead of focusing on **how** a task should run, developers only describe **what** task needs to be executed.

The framework takes responsibility for:

- Creating worker threads
- Reusing existing threads
- Scheduling tasks
- Managing thread lifecycle
- Returning task results
- Gracefully shutting down workers

This separation between **task submission** and **task execution** makes concurrent applications more scalable and maintainable.

---

## Simple Example

Instead of writing:

```java
new Thread(() -> processOrder()).start();
```

we write:

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

executor.submit(() -> processOrder());
```

Notice that we never create a thread ourselves.

---

# 2. Why was ExecutorService Introduced?

Before Java 5, every asynchronous task generally required creating a new thread.

```java
new Thread(() -> {
    sendEmail();
}).start();
```

This works for small programs.

It becomes a disaster for enterprise applications.

Imagine an e-commerce website receiving:

- 500 requests per second
- Each request creates one thread

Within seconds, thousands of threads may exist simultaneously.

This introduces several problems.

---

## Problem 1 - Thread Creation is Expensive

Creating a thread is **not just creating a Java object**.

The JVM asks the operating system to create a native thread.

The OS allocates:

- Native thread structures
- Thread stack
- Scheduling metadata
- Kernel resources

Thread creation is therefore relatively expensive compared to creating ordinary Java objects.

---

## Problem 2 - Threads Consume Memory

Every thread owns a stack.

Suppose:

- Thread Stack = 1 MB
- 5,000 threads

Memory consumption becomes approximately:

```
5000 × 1 MB

≈ 5 GB
```

Most of that memory may be wasted if threads spend their time waiting.

---

## Problem 3 - Context Switching

The CPU cannot execute thousands of threads simultaneously.

It constantly switches between them.

```
CPU

↓

Thread A

↓

Thread B

↓

Thread C

↓

Thread D

↓

...
```

Each switch requires:

- Saving CPU registers
- Restoring another thread's state
- Invalidating CPU caches
- Scheduling overhead

More threads eventually reduce throughput instead of increasing it.

---

## Problem 4 - No Thread Reuse

Consider:

```java
for (Order order : orders) {
    new Thread(() -> process(order)).start();
}
```

Every iteration creates a completely new thread.

After finishing the task, the thread dies.

Creating and destroying thousands of threads repeatedly wastes CPU time.

---

## Problem 5 - No Lifecycle Management

Suppose your application is shutting down.

How do you wait until all background tasks finish?

With manually created threads:

```java
new Thread(...).start();
```

You have very little control.

ExecutorService provides:

- shutdown()
- shutdownNow()
- awaitTermination()

---

## Problem 6 - No Result Handling

Using Thread:

```java
new Thread(() -> calculatePrice()).start();
```

How do you obtain the calculated value?

There is no built-in mechanism.

ExecutorService solves this using:

- Callable
- Future

---

## Problem 7 - No Cancellation Support

Suppose a user cancels a report generation.

How do you stop the running thread?

ExecutorService supports:

```java
future.cancel(true);
```

which attempts cooperative cancellation by interrupting the worker thread.

---

# 3. Problems with Creating Threads Manually

Creating a thread for every task:

❌ Expensive

❌ Poor scalability

❌ Difficult lifecycle management

❌ Difficult exception handling

❌ No return value

❌ No cancellation

❌ Difficult monitoring

❌ Thread explosion

---

# 4. Mental Model

Think of ExecutorService as a restaurant.

Customers represent tasks.

Chefs represent worker threads.

Instead of hiring a new chef for every customer, the restaurant maintains a fixed number of chefs.

```
Customers

↓

Waiter

↓

Kitchen Queue

↓

Chef 1

Chef 2

Chef 3

↓

Food Ready
```

The waiter accepts orders.

The kitchen queue stores pending work.

Chefs continuously pick up the next order.

Exactly the same thing happens inside ExecutorService.

---

## Another Analogy

Airport Security.

Passengers

↓

Waiting Queue

↓

Security Officers

↓

Security Check

↓

Exit

Passengers never choose a security officer.

The airport assigns available officers.

ExecutorService works the same way.

---

# 5. Architecture

```
Application

↓

ExecutorService

↓

ThreadPoolExecutor

↓

BlockingQueue

↓

Worker Threads

↓

Task Execution
```

---

## Execution Flow

```
submit(task)

↓

ExecutorService

↓

ThreadPoolExecutor

↓

BlockingQueue

↓

Worker Thread

↓

Task Executes

↓

Future Updated
```

---

# 6. Key Components

## 1. Task

Represents the work to perform.

Usually implemented using:

- Runnable
- Callable

---

## 2. ExecutorService

Accepts tasks from the application.

Responsible for task submission.

---

## 3. ThreadPoolExecutor

The default implementation of ExecutorService.

Responsible for:

- Thread creation
- Thread reuse
- Queue management
- Rejection policies
- Shutdown

---

## 4. BlockingQueue

Stores tasks waiting to execute.

Common implementations:

- LinkedBlockingQueue
- ArrayBlockingQueue
- SynchronousQueue

---

## 5. Worker Threads

Threads that continuously execute tasks.

Instead of dying after every task, workers are reused.

```
Worker

↓

Take Task

↓

Execute

↓

Take Next Task

↓

Execute

↓

Repeat
```

This dramatically reduces thread creation overhead.

---

# Why Thread Reuse is Faster

Without ExecutorService:

```
Create Thread

↓

Execute Task

↓

Destroy Thread

↓

Create Thread

↓

Execute Task

↓

Destroy Thread
```

With ExecutorService:

```
Create Thread

↓

Task 1

↓

Task 2

↓

Task 3

↓

Task 4

↓

Task 5
```

The same worker thread executes multiple tasks.

---

# 7. Interview Questions

## Basic

### Q1. What problem does ExecutorService solve?

### Q2. Why is creating threads manually expensive?

### Q3. Why are thread pools faster than creating threads?

### Q4. What is the difference between a task and a thread?

---

## Intermediate

### Q5. Why does thread reuse improve performance?

### Q6. Why can't an application create unlimited threads?

### Q7. Explain the architecture of ExecutorService.

### Q8. Explain the lifecycle of a submitted task.

---

## Senior

### Q9. Why does ExecutorService separate task submission from task execution?

### Q10. Why is thread creation considered expensive?

### Q11. In a high-throughput REST application, why would creating one thread per request be dangerous?

### Q12. Explain how ExecutorService improves scalability.

---

# Key Takeaways

- ExecutorService separates **task submission** from **task execution**.
- Threads are expensive OS resources and should be reused.
- Thread pools improve throughput by reducing thread creation overhead.
- Tasks are placed into a queue and executed by reusable worker threads.
- ExecutorService provides lifecycle management, result handling, cancellation, and monitoring capabilities that are difficult to implement with manually created threads.