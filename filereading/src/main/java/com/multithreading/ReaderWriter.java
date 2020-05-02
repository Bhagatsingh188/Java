package com.multithreading;

public interface ReaderWriter {
    void readFile();
    boolean isProcessable(String fileName);
}
