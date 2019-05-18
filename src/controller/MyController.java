package controller;

import FileHandlers.FileHandlerImpl.CSVFileHandler;
import FileHandlers.FileHandlerImpl.JsonFileHandler;
import FileHandlers.FileHandlerImpl.XMLFileHandler;
import FileHandlers.MyFileHandler;
import model.MyCollection;

public class MyController {

    public static void main(String[] args) {
        try {
            MyFileHandler xmlHandler = new XMLFileHandler("/Users/amanchauhan/Downloads/employee.xml", "/Users/amanchauhan/Downloads/employee_out.xml");
            MyFileHandler jsonHandler = new JsonFileHandler("/Users/amanchauhan/Downloads/employee.json", "/Users/amanchauhan/Downloads/employee_out.json");
            MyFileHandler csvHandler = new CSVFileHandler("/Users/amanchauhan/Downloads/employee.csv", "/Users/amanchauhan/Downloads/employee_out.csv");
            ReadThread csvRead = new ReadThread(csvHandler);
            ReadThread xmlRead = new ReadThread(xmlHandler);
            ReadThread jsonRead = new ReadThread(jsonHandler);

            xmlRead.start();
            jsonRead.start();
            csvRead.start();
            try {
                xmlRead.join();
                jsonRead.join();
                csvRead.join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }

            MyCollection myCollection = MyCollection.getInstance();

            System.out.println("Write counter:" + myCollection.getWriteCounter());
            System.out.println("List size:" + myCollection.getSize());


            WriteThread xmlWrite = new WriteThread(xmlHandler);
            WriteThread  jsonWrite = new WriteThread(jsonHandler);
            WriteThread csvWrite = new WriteThread(csvHandler);

            xmlWrite.start();
            jsonWrite.start();
            csvWrite.start();

            try {
                xmlWrite.join();
                jsonWrite.join();
                csvWrite.join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }

            System.out.println("Read Counter:" + myCollection.getReadCounter());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
