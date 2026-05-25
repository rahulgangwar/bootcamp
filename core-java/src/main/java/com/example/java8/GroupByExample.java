package com.example.java8;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class GroupByExample {
  public static void main(String[] args) {
    List<BlogPost> posts =
        Arrays.asList(
            new BlogPost("Java blog", "Rahul", BlogPostType.TECH, 100),
            new BlogPost("Java advanced", "Rahul", BlogPostType.TECH, 200),
            new BlogPost("Angular blog", "Arun", BlogPostType.TECH, 500),
            new BlogPost("News blog", "Sony", BlogPostType.NEWS, 200));

    System.out.println("Posts : " + posts);

    // Group posts by type
    Map<BlogPostType, List<BlogPost>> postsPerType =
        posts.stream().collect(groupingBy(x -> x.type, toList()));
    System.out.println("Posts per type: " + postsPerType);

    // Count posts per type
    Map<BlogPostType, Long> countPostsPerType =
        posts.stream().collect(groupingBy(BlogPost::getType, Collectors.counting()));
    System.out.println("Count of posts per type: " + countPostsPerType);

    // Forming complex keys
    Map<Tuple, List<BlogPost>> postsForTuple =
        posts.stream().collect(groupingBy(post -> new Tuple(post.getType(), post.getAuthor())));
    System.out.println("Posts for tuple: " + postsForTuple);

    // Group by multiple fields
    Map<String, Map<BlogPostType, List<BlogPost>>> groupByAuthorAndType =
        posts.stream().collect(groupingBy(BlogPost::getAuthor, groupingBy(BlogPost::getType)));
    System.out.println("Post grouped by type and author: " + groupByAuthorAndType);

    // Average likes by type
    Map<BlogPostType, Double> averageLikesPerType =
        posts.stream()
            .collect(groupingBy(BlogPost::getType, Collectors.averagingInt(BlogPost::getLikes)));
    System.out.println("Average likes per type: " + averageLikesPerType);

    // Total likes per type
    Map<BlogPostType, Integer> totalLikesPerType =
        posts.stream()
            .collect(groupingBy(BlogPost::getType, Collectors.summingInt(BlogPost::getLikes)));
    System.out.println("Total likes per type: " + totalLikesPerType);

    // Posts per type
    Map<BlogPostType, String> postTitlePerType =
        posts.stream()
            .collect(
                groupingBy(
                    BlogPost::getType,
                    mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));
    System.out.println("Posts title per type: " + postTitlePerType);

    // Max likes for type
    Map<BlogPostType, Optional<BlogPost>> maxLikesPerPostType =
        posts.stream()
            .collect(groupingBy(BlogPost::getType, maxBy(comparingInt(BlogPost::getLikes))));
    System.out.println("Max likes per post: " + maxLikesPerPostType);
  }

  enum BlogPostType {
    NEWS,
    TECH,
    GUIDE
  }

  @AllArgsConstructor
  @Getter
  static class BlogPost {
    String title;
    String author;
    BlogPostType type;
    int likes;

    public String toString() {
      return String.format("%s: %s (%s) - %s", type, title, author, likes);
    }
  }

  @AllArgsConstructor
  static class Tuple {
    BlogPostType type;
    String author;

    public String toString() {
      return String.format("%s: %s", type, author);
    }
  }
}
