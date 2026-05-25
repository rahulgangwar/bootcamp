package com.example.gof.structural.composite;

// Leaf Class - File (No children)
public class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("------------ File: " + name + " (Size: " + size + " KB)");
    }

    @Override
    public int getSize() {
        return size;
    }
}
