package com.example.scenarios.TinyUrl;

import java.util.HashMap;
import java.util.Map;

public class TinyUrlSimulator {

    private static final String BASE62_CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;
    private static long idCounter = 1; // Simulated auto-increment ID

    // Maps to simulate database
    private static Map<Long, String> idToUrl = new HashMap<>();
    private static Map<String, Long> urlToId = new HashMap<>();

    // Encode ID to Base62
    public static String encode(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62_CHARS.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return sb.reverse().toString();
    }

    // Decode Base62 to ID
    public static long decode(String shortUrl) {
        long id = 0;
        for (char c : shortUrl.toCharArray()) {
            id = id * BASE + BASE62_CHARS.indexOf(c);
        }
        return id;
    }

    // Shorten a long URL
    public static String shortenURL(String longUrl) {
        if (urlToId.containsKey(longUrl)) {
            return encode(urlToId.get(longUrl));
        }

        long id = idCounter++;
        urlToId.put(longUrl, id);
        idToUrl.put(id, longUrl);

        return encode(id);
    }

    // Expand a short URL back to the original
    public static String expandURL(String shortUrl) {
        long id = decode(shortUrl);
        return idToUrl.get(id);
    }

    public static void main(String[] args) {
        String longUrl =
                "https://marcelclasses.udemy.com/course/complete-generative-ai-course-with-langchain-and-huggingface/learn/lecture/44151284?course_portion_id=1188655&learning_path_id=9787195#overview";

        String shortCode = shortenURL(longUrl);
        String shortUrl = "https://short.ly/" + shortCode;

        System.out.println("Original URL: " + longUrl);
        System.out.println("Shortened URL: " + shortUrl);

        // Optional: Expand back
        String expandedUrl = expandURL(shortCode);
        System.out.println("Expanded URL: " + expandedUrl);
    }
}
