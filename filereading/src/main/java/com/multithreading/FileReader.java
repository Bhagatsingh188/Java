package com.multithreading;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader implements ReaderWriter{
    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    private List<String> files;

    public int getSleepMilli() {
        return sleepMilli;
    }

    public void setSleepMilli(int sleepMilli) {
        this.sleepMilli = sleepMilli;
    }

    private int sleepMilli;

    public FileReader(List<String> files, int sleepMilli) {
        this.files = files;
        this.sleepMilli = sleepMilli;
    }

    public boolean isProcessable(String fileName){
        if (fileName.endsWith(".txt")){
            return true;
        }
        return false;
    }

    @Override
    public void readFile() {
        getFiles().stream().filter(this::isProcessable).forEach(file -> {
            try {
                List<String> lines = Files.readAllLines(Paths.get(file));
                lines.stream().forEach(line -> {
                    System.out.println("File: " + file + " Line: " + line);
                    try {
                        Thread.sleep(sleepMilli);
                    } catch (InterruptedException ex) {

                    }

                });
            } catch (Exception ex) {

            }
        });
    }
}
