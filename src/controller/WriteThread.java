package controller;

import FileHandlers.MyFileHandler;
import model.Employee;
import model.MyCollection;

public class WriteThread extends Thread{

    MyFileHandler myFileHandler;
    MyCollection myCollection = MyCollection.getInstance();

    public WriteThread(MyFileHandler myFileHandler) {
        this.myFileHandler=myFileHandler;
    }

   public void run() {
        for(int writecounter = 0; writecounter <100; writecounter++) {
            Employee employee = myCollection.get();
            try {
                myFileHandler.write(employee);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
