package FileHandlers;

import model.Employee;

public interface MyFileHandler {

     Employee read();

     void write(Employee emp);

}

