package FileHandlers.FileHandlerImpl;


import java.io.FileReader;

import FileHandlers.MyFileHandler;
import model.Employee;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.text.SimpleDateFormat;

public class JsonFileHandler implements MyFileHandler {
    private Object arrayObj;
    private JSONParser parser;

    private String readFile;

    private String writeFile;

    private int count=0;
    private PrintWriter printWriter;

    public JsonFileHandler(String readPath,String writePath) throws Exception {

        this.readFile = readPath;
        this.writeFile = writePath;

        parser = new JSONParser();
        arrayObj = parser.parse(new FileReader(readFile));

        printWriter = new PrintWriter(writeFile);

    }

    public Employee read() throws Exception
    {
        Employee  employee=new Employee();

        JSONArray jsonArray =  (JSONArray)arrayObj;

        Object jsonObj =jsonArray.get(count);
        JSONObject jsonLineItem = (JSONObject) jsonObj;

        String firstName=(String)  jsonLineItem.get("firstName");
        String LastName=(String) jsonLineItem.get("lastName");
        String dateOfBirth=(String) jsonLineItem.get("dateOfBirth");
        long experience=(long) jsonLineItem.get("experience");
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
        printWriter.write(jo.toJSONString());
        printWriter.println();
        printWriter.flush();

    }

}