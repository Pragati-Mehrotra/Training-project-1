package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  MyCollection is the in-memory data store that holds Employee records.
 *  The class is implemented as singleton with thread safety.
 */

public class MyCollection
{
    // private instance, so that it can be
    // accessed by only by getInstance() method
    private static MyCollection instance;
    private List<Employee> employeeList;
    private static final Integer MAX_LIST_SIZE = 300;

    private Integer readCounter;
    private Integer writeCounter;

    private MyCollection()
    {
        // private constructor
        readCounter = writeCounter = 0;
        employeeList = Collections.synchronizedList( new ArrayList<>(MAX_LIST_SIZE));

    }

    public static MyCollection getInstance()
    {
        if (instance == null)
        {
            synchronized (MyCollection.class)
            {
                if(instance==null)
                {
                    // if instance is null, initialize
                    instance = new MyCollection();
                }

            }
        }
        return instance;
    }

    public synchronized void add(Employee employee){

        /**
         * @Param: employee Employee object to be inserted
         */

        employeeList.add(employee);
        writeCounter++;

    }

    public synchronized Employee get(){

        /**
         * @return: object Employee in employeeList at index readCounter;
         */

        Employee employee =  employeeList.get(readCounter);
        readCounter++;
        return employee;
    }

    public Integer getReadCounter() {
        return readCounter;
    }

    public Integer getWriteCounter() {
        return writeCounter;

    }

    
}