package com.example.filehandling;

import java.io.*;

public class IoOperations {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/rahul/sample.txt";

        // Read using file input stream
        // Reads one byte at a time from the file
        InputStream fis = new FileInputStream(filePath);
        int data;
        System.out.println("Using file input stream");
        System.out.println("=======================");
        while ((data = fis.read()) != -1) {
            System.out.print((char) data );
        }
        fis.close();

        // Using Buffered input stream
        // BufferedInputStream is a wrapper around another InputStream that reads data in larger chunks and keeps it in memory (a buffer).
        // This reduces expensive disk/network reads and improves performance.
        // It loads a large chunk into memory once and then serves subsequent read() calls from the buffer.
        InputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        System.out.println("Using buffered input stream");
        System.out.println("============================");
        while ((data = bis.read()) != -1) {
            System.out.print((char) data);
        }
        bis.close();


        // Using buffered input stream with byte array
        InputStream bis2 = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buffer = new byte[1024];
        int bytesRead;
        System.out.println("Using buffered input stream with byte array");
        System.out.println("==========================================");
        while ((bytesRead = bis2.read(buffer)) != -1) {
            System.out.write(buffer, 0, bytesRead);
        }
        bis2.close();
    }
}
