package com.multithreading;

public class MyThread extends Thread {

    ReaderWriter reader;

    public MyThread(ReaderWriter reader){
        super();
        this.reader = reader;
    }

    @Override
    public void run(){
        reader.readFile();
    }
}
