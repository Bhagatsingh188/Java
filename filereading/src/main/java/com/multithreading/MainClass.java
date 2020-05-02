package com.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class MainClass {

    public static void main(String[] args) throws InterruptedException{
        List<String> filesToRead = new ArrayList<>();
//        filesToRead.add("C:\\Users\\bhaga\\eclipse-workspace\\filereading\\src\\main\\resources\\text_file1.txt");
//        filesToRead.add("C:\\Users\\bhaga\\eclipse-workspace\\filereading\\src\\main\\resources\\text_file2.txt");
        filesToRead.add("C:\\Users\\bhaga\\eclipse-workspace\\filereading\\src\\main\\java\\com\\multithreading\\employees_data.json");
//        filesToRead.add("C:\\Users\\bhaga\\eclipse-workspace\\filereading\\src\\main\\resources\\json_file2.json");               

        List<MyThread> threads = new ArrayList<>();
        FileReader fileReader = new FileReader(filesToRead, 50);
        JsonFileReader jsonFileReader = new JsonFileReader(filesToRead, 50);

        threads.add(new MyThread(fileReader));
        threads.add(new MyThread(jsonFileReader));

        for (MyThread thread: threads){
            thread.start();
        }

        for (MyThread thread: threads){
            thread.join();
        }
       
         JsonToDatabase emp = new JsonToDatabase();
         
         try {
 			emp.ConnectToDB();
 			emp.insertToDb();
 		} catch (Exception e) {
 		System.out.println(e);
           }
  }
}
