package com.example.gof.structural.composite;

public class CompositePatternExample {
    public static void main(String[] args) {
        // Create files
        FileSystemComponent file1 = new File("Resume.pdf", 100);
        FileSystemComponent file2 = new File("Photo.png", 500);
        FileSystemComponent file3 = new File("Notes.txt", 50);

        // Create a folder and add files
        Folder documents = new Folder("Documents");
        documents.addComponent(file1);
        documents.addComponent(file2);

        // Create another folder with nested structure
        Folder personalFolder = new Folder("Personal");
        personalFolder.addComponent(file3);
        personalFolder.addComponent(documents);

        // Display structure and size
        System.out.println("File System Structure:");
        personalFolder.display();
        System.out.println("Total Size: " + personalFolder.getSize() + " KB");
    }
}
