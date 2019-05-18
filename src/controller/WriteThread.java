package controller;

import FileHandlers.MyFileHandler;
import model.Employee;
import model.MyCollection;

public class WriteThread extends Thread{

    MyFileHandler myFileHandler;


    MyCollection myCollection = MyCollection.getInstance();

    WriteThread(MyFileHandler myFileHandler) {

        this.myFileHandler=myFileHandler;

    }


   public void run() {



        for(int i=0;i<100;i++)
        {
            Employee employee = myCollection.get();
            try {
                myFileHandler.write(employee);
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }

    }
}
