package FileHandlers.tests;

import FileHandlers.FileHandlerImpl.JsonFileHandler;
import FileHandlers.MyFileHandler;
import model.Employee;

public class TestJsonFileHandler {
    public static void main(String[] args) throws Exception {

        MyFileHandler myFileHandler = new JsonFileHandler("employee.json","out_employee.json");
        Employee employee = myFileHandler.read();

        System.out.println(employee);
    }
}
