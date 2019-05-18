package model.tests;

import model.Employee;
import model.MyCollection;

public class TestMyCollection {

    public static void main(String[] args) throws InterruptedException {
        TestMyCollection testMyCollection = new TestMyCollection();
        testMyCollection.testInsertToMyCollection();
        Thread.sleep(2000);
        testMyCollection.testGetFromMyCollection();
    }

    public void testInsertToMyCollection() {


        Thread thread1 = new Thread(new Insert100RecordsRunnable());
        Thread thread2 = new Thread(new Insert100RecordsRunnable());
        Thread thread3 = new Thread(new Insert100RecordsRunnable());

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void testGetFromMyCollection() {


        Thread thread1 = new Thread(new Get100RecordsRunnable());
        Thread thread2 = new Thread(new Get100RecordsRunnable());
        Thread thread3 = new Thread(new Get100RecordsRunnable());


        thread1.start();
        thread2.start();
        thread3.start();
    }

    private class Insert100RecordsRunnable implements  Runnable{
        MyCollection myCollection = MyCollection.getInstance();

        @Override
        public void run() {
            Employee employee;
            for(int i=0;i<100;i++){
                employee = new Employee();
                employee.setFirstName(""+i);
                myCollection.add(employee);
                System.out.println(i);
            }
        }
    }

    private class Get100RecordsRunnable implements Runnable{
        MyCollection myCollection = MyCollection.getInstance();

        @Override
        public void run() {
            Employee employee;
            for(int i=0;i<100;i++){
                employee = myCollection.get();
                System.out.println(employee);
            }
        }
    }
}
