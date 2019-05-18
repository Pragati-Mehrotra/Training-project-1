package FileHandlers.FileHandlerImpl;

import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import FileHandlers.MyFileHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import model.Employee;

import java.io.*;

public class JsonFileHandler implements MyFileHandler {

    private String readFileName;

    private String writeFileName;
    private static int readCount;
    private JSONArray jsonArray;

    private PrintWriter pw;

    private Object obj;

    public JsonFileHandler(String readPath, String writePath) {
        this.readFileName = readPath;
        this.writeFileName = writePath;
        readCount = 0;

        try {
            obj = new JSONParser().parse(new FileReader(readFileName));
            jsonArray = (JSONArray) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            pw = new PrintWriter(writePath);
        }
        catch(Exception  e){
            e.printStackTrace();
        }
    }

    @Override
    public Employee read() {

        Employee employeenew = new Employee();

       if(readCount< 99){
           jsonArray.getJSONObject(readCount);
       }

       return employeenew;
    }


    private static Employee parseEmployeeObject(JSONObject employee)
    {
        Employee emp = new Employee();
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        emp.setFirstName(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        emp.setLastName(lastName);

        //Get employee website name
        Date dateOfBirth = (Date) employeeObject.get("dateOfBirth");
        emp.setDateOfBirth(dateOfBirth);
        Double experience = (Double) employeeObject.get("experience");
        emp.setExperience(experience);

        return emp;

    }

    @Override
    public void write(Employee employee) {

        JSONObject jo = new JSONObject();
        jo.put("firstName", employee.getFirstName());
        jo.put("lastName", employee.getLastName());
        jo.put("dateOfBirth", employee.getDateOfBirth());
        jo.put("experience", employee.getExperience());

        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();

    }

}