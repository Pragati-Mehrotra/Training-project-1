package FileHandlers;

import model.Employee;

public interface MyFileHandler {

     Employee read() throws  Exception;

     void write(Employee emp) throws  Exception;

}

