package com.example.gof.structural.adapter;

public class AdapterPatternExample {
    public static void main(String[] args) {
        new ModernPrinterAdapter(
                new LegacyPrinter(){
                    @Override
                    public void printDocument() {
                        System.out.println("Printing document");
                    }
                }
        ).print();
    }
}

