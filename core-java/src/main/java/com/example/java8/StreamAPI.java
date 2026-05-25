package com.example.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"SimplifyStreamApiCallChains", "ResultOfMethodCallIgnored"})
public class StreamAPI {

  public static void main(String[] args) {
    terminalOps();
  }

  private static void create() {
    Stream streamUsingArray = Arrays.asList("a1", "a2", "a3").stream();
    Stream streamUsingStream = Stream.of("a1", "a2", "a3");

    try {
      Stream<String> streamUsingFile = Files.lines(Paths.get("/home/rahul/sample.txt"));
    } catch (IOException e) {
      System.out.println("Error while reading file : " + e.getMessage());
    }

    // todo primitive stream i.e IntStream, LongStream, DoubleStream etc
    IntStream primitiveStream = IntStream.range(1, 4);

    // todo converting primitive to ObjectStream
    Stream<Integer> convertedStream = primitiveStream.mapToObj(x -> x + 1);

    // todo converting back to primitive stream
    IntStream intStream = Stream.of(1.0, 2.0, 3.0).mapToInt(Double::intValue);
  }

  private static void intermediateOps() {
    Stream.of("d2", "a2", "b1", "b3", "c")
        .map(
            s -> { // todo accepts Function {R apply(T t)}
              System.out.println("map: " + s);
              return s.toUpperCase();
            })
        .filter(
            s -> { // todo accepts Predicate {boolean main.java.testUtil(T t)}
              System.out.println("filter: " + s);
              return s.startsWith("A");
            })
        .sorted(
            (s1, s2) -> { // todo accepts Comparator {int compare(T o1, T o2)}
              System.out.printf("sort: %s; %s\n", s1, s2);
              return s1.compareTo(s2);
            })
        .forEach( // todo accepts Consumer
            s -> System.out.println("forEach: " + s));
  }

  private static void terminalOps() {
    Supplier<Stream<Person>> personStream =
        () ->
            Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12))
                .stream();

    // todo ------------- collect ----------------------
    List<Person> filtered =
        personStream
            .get()
            .filter(p -> p.name.startsWith("P"))
            .collect( // todo accepts Collector
                Collectors.toList());
    System.out.println("Persons : " + personStream);

    // todo grouping data in collect
    Map<Integer, List<Person>> personsByAge =
        personStream.get().collect(Collectors.groupingBy(p -> p.age));
    System.out.println("Persons grouped by age : " + personsByAge);

    // todo ------------- forEach, accepts Consumer, returns void----------------------
    System.out.println("Printing employees using forEach : ");
    personStream.get().forEach(System.out::println);

    // todo ------------- match, accepts Predicate, returns boolean ----------------------
    System.out.println(
        "All emp name start with P : " + personStream.get().allMatch(x -> x.name.startsWith("P")));

    System.out.println(
        "Any emp name start with P : " + personStream.get().anyMatch(x -> x.name.startsWith("P")));

    System.out.println(
        "No emp name start with P : " + personStream.get().noneMatch(x -> x.name.startsWith("P")));

    // todo ------------- find, accepts nothing, returns Optional ----------------------
    System.out.println("Find first emp : ");
    personStream.get().findFirst().ifPresent(System.out::println);

    System.out.println("Find any emp : ");
    personStream.get().findAny().ifPresent(System.out::println);

    // todo ------------- count, returns Long ----------------------
    System.out.println("Count : " + personStream.get().count());
  }

  private static void parallel() {
    // todo Parallel streams use a common ForkJoinPool available
    // todo via the static ForkJoinPool.commonPool() method
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    System.out.println("ForkJoinPool : " + commonPool.getParallelism());

    Arrays.asList("a1", "a2", "b1", "c2", "c1").parallelStream()
        .sorted(
            (s1, s2) -> { // todo sort is run only on the main thread
              System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
              return s1.compareTo(s2);
            })
        .filter(
            s -> {
              System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
              return true;
            })
        .map(
            s -> {
              System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
              return s.toUpperCase();
            })
        .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
  }

  public static void reduce() {
    Stream.of(1, 2, 3, 4, 5, 6)
        .reduce( // todo accepts Binary operator
            // todo returns Optional {ifPresent()}
            (x, y) -> x + y)
        .ifPresent( // todo accepts Consumer
            x -> System.out.println("Total sum: " + x));
  }

  private static void nonReusable() {
    Stream<String> stream = Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> s.startsWith("a"));

    stream.anyMatch(s -> true); // ok

    // todo  As soon as you call any terminal operation the stream is closed
    // todo  exception (java.lang.IllegalStateException: stream has already been operated upon or
    // closed
    stream.noneMatch(s -> true);

    // todo To overcome this limitation we have to to create a new stream chain for every terminal
    // operation
    // todo Supplier Interface can be used to get new Stream
    Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c");

    streamSupplier.get().anyMatch(s -> true); // ok
    streamSupplier.get().noneMatch(s -> true); // ok
  }

  static class Person {
    String name;
    int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
  }
}
