package com.multithreading;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements ReaderWriter{
    private List<String> files;

    public JsonFileReader(List<String> files, int sleepMilli) {
        this.files = files;
        this.sleepMilli = sleepMilli;
    }

    public int getSleepMilli() {
        return sleepMilli;
    }

    public void setSleepMilli(int sleepMilli) {
        this.sleepMilli = sleepMilli;
    }

    private int sleepMilli;

    public JsonFileReader(List<String> files) {
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFileName(List<String> files) {
        this.files = files;
    }

    @Override
    public void readFile() {

        getFiles().stream().filter(this::isProcessable).forEach(file -> {
            try {

                List<String> lines = Files.readAllLines(Paths.get(file));
                StringBuffer fileData = new StringBuffer();
                lines.stream().forEach(line -> fileData.append(line));

                ObjectMapper mapper = new ObjectMapper();
                Map data = mapper.readValue(fileData.toString(), Map.class);
                data.forEach((key,value) -> System.out.println("Key: " + key.toString() + " Value: " + value.toString()));

            } catch (Exception ex) {

            }
        });
    }

    @Override
    public boolean isProcessable(String fileName) {
        if (fileName.endsWith(".json")){
            return true;
        }
        return false;
    }
}
