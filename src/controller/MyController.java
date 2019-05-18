package controller;

import FileHandlers.FileHandlerImpl.CSVFileHandler;
import FileHandlers.FileHandlerImpl.JsonFileHandler;
import FileHandlers.FileHandlerImpl.XMLFileHandler;
import FileHandlers.MyFileHandler;
import model.MyCollection;
import sun.jvm.hotspot.debugger.ThreadAccess;

public class MyController {

    public static void main(String[] args) {
        MyFileHandler xmlHandler = new XMLFileHandler("employee.xml", "employee_out.xml");
        MyFileHandler jsonHandler = new JsonFileHandler("employee.json", "employee_out.xml");
        MyFileHandler csvHandler = new CSVFileHandler("employee.csv", "employee_out.csv");

        ReadThread xmlRead = new ReadThread(xmlHandler);
        ReadThread jsonRead = new ReadThread(jsonHandler);
        ReadThread csvRead = new ReadThread(csvHandler);

        xmlRead.start();
        jsonRead.start();
        csvRead.start();
        xmlRead.join();
        jsonRead.join();
        csvRead.join();

        MyCollection myCollection = MyCollection.getInstance();
        System.out.println(myCollection.getWriteCounter());

        

    }
}
