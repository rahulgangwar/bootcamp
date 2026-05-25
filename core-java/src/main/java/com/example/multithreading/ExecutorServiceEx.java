package com.example.multithreading;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceEx {

  private ExecutorService executorService;

  public static void main(String[] args) {
    new ExecutorServiceEx().testThings();
  }

  private void testThings() {
    /*
     * Executor service with single thread
     * Runs the submitted tasks sequentially
     */
    System.out.println("========Running single thread executor========");
    executorService = Executors.newSingleThreadExecutor();
    Future<?> future = executorService.submit(() -> {});
    executorService.isTerminated();
    print(submitWork(executorService));

    /*
     * Creates a thread pool executor that reuses a fixed number of threads
     * operating off a shared unbounded LinkedBlockingQueue.
     */
    System.out.println("========Running fixed thread executor========");
    executorService = Executors.newFixedThreadPool(3);
    print(submitWork(executorService));

    /*
     * Creates a thread pool executor that creates new threads as needed, but
     * will reuse previously constructed threads when they are available using a SynchronousQueue.
     */
    System.out.println("========Running cached thread executor========");
    executorService = Executors.newCachedThreadPool();
    print(submitWork(executorService));

    // Note: BlockingQueue is super class of LinkedBlockingQueue and SynchronousQueue
  }

  private List<Future> submitWork(final ExecutorService executorService) {
    List<Future> list = new ArrayList<>();
    IntStream.range(0, 10)
        .asLongStream()
        .forEach(
            it -> {
              list.add(executorService.submit(new MyCallable(it)));
            });
    return list;
  }

  private void print(List<Future> results) {
    for (Future future : results) {
      try {
        System.out.println(future.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  @AllArgsConstructor
  private class MyCallable implements Callable {
    private long id;

    @Override
    public String call() throws Exception {
      System.out.println("Executing " + id + " - " + Thread.currentThread().getName());
      Thread.sleep(1000);
      return Thread.currentThread().getName();
    }
  }
}
