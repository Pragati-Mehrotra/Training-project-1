package FileHandlers.FileHandlerImpl;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import FileHandlers.MyFileHandler;
import model.Employee;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class JsonFileHandler implements MyFileHandler {
    private Object obj;
    private JSONParser parser;

    private String readFile;

    private String writeFile;

   private int count=0;
    private PrintWriter pw;
    public JsonFileHandler(String readPath,String writePath) throws Exception {

        this.readFile = readPath;
        this.writeFile = writePath;

        parser = new JSONParser();
        obj = parser.parse(new FileReader(readFile));

        try{
            pw = new PrintWriter(writeFile);
        }
        catch(Exception  e){
            e.printStackTrace();
        }


    }

    public Employee read() throws Exception
    {
        JSONParser parser = new JSONParser();
        Employee  employee=new Employee();


        JSONArray jsonArray =  (JSONArray)obj;

        String name = (String) jsonArray.toString();

        Object o =jsonArray.get(count);
        JSONObject jsonLineItem = (JSONObject) o;
        String a=jsonLineItem.toString();
        System.out.println(a);
        String firstName=(String)  jsonLineItem.get("firstName");
        String LastName=(String) jsonLineItem.get("lastName");
        String dateOfBirth=(String) jsonLineItem.get("dateOfBirth");
        long experience=(long) jsonLineItem.get("experience");
        System.out.println(firstName+" "+LastName+" "+dateOfBirth+" "+experience);
        employee.setFirstName(firstName);
        employee.setLastName(LastName);
        employee.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
        employee.setExperience(new Double(experience));
        count++;

        return  employee;
    }


    public void write(Employee employee) {
        JSONObject jo = new JSONObject();
        jo.put("firstName", employee.getFirstName());
        jo.put("lastName", employee.getLastName());
        jo.put("dateOfBirth", employee.getDateOfBirth());
        jo.put("experience", employee.getExperience());
        System.out.println(jo);
        System.out.println("done");
        pw.write(jo.toJSONString());
        pw.println();
        pw.flush();

    }

}