package com.example.multithreading;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

  public static void main(String[] args) {
    List<Integer> data = Arrays.asList(1, 2, 5,4,3,20,10);

    Collections.sort(data, Comparator.comparingInt(x -> x));

    System.out.println(data);


  }

  private static class MyTask extends RecursiveTask<Integer> {

    List<Integer> list;

    public MyTask(List<Integer> list) {
      this.list = list;
    }

    @Override
    protected Integer compute() {

      if (list.size() > 3) {  // <<<<<<<<< -------- Threshold Condition to divide

        List<MyTask> subTasks = new ArrayList<>(createSubtasks()); // <<<<<<<< ------------ Divide logic


        for (MyTask task : subTasks) {
          task.fork();    // <<<<<< --------------- Assign
        }


        Integer result = 0;
        for (MyTask task : subTasks) {
          result += task.join();  // <<<<<<< ----------- Wait for results and Aggregate
        }
        return result;

      } else {
        int sum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : list) {
          stringBuilder.append(i).append(", ");
          sum += i;
        }
        System.out.printf("%s : Adding : %s%n", Thread.currentThread().getName(), stringBuilder);
        return sum;
      }
    }

    private List<MyTask> createSubtasks() {
      List<MyTask> subTasks = new ArrayList<>();

      int mid = list.size() / 2;
      MyTask task1 = new MyTask(list.subList(0, mid));
      MyTask task2 = new MyTask(list.subList(mid, list.size()));

      subTasks.add(task1);
      subTasks.add(task2);

      return subTasks;
    }
  }
}
