package controller;

import FileHandlers.MyFileHandler;
import model.Employee;
import model.MyCollection;

public class ReadThread extends Thread{


    MyFileHandler fileHandler;
    MyCollection myCollection = MyCollection.getInstance();


    public ReadThread(MyFileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void run(){

        for(int readcounter = 0; readcounter < 100; readcounter++) {
            try {
                Employee employee = fileHandler.read();
                myCollection.add(employee);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
