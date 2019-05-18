package FileHandlers.FileHandlerImpl;
import FileHandlers.MyFileHandler;

import model.Employee;
import java.io.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
public class JsonFileHandler  implements MyFileHandler {
    private String readFileName;

    private String writeFileName;

    private static int count;

    private PrintWriter pw;
    public JsonFileHandler(String readPath, String writePath){
        this.readFileName = readPath;
        this.writeFileName = writePath;
        count = 0;

        try{
            pw = new PrintWriter(writePath);
        }
        catch(Exception  e){
            e.printStackTrace();
        }

    }
    @Override
    public Employee read() throws Exception {
      Employee employee=new Employee();
        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

  return  employee;
    }

    @Override
    public void write(Employee employee) {
        JSONObject jo = new JSONObject();
        jo.put("firstName", employee.getFirstName());
        jo.put("lastName", employee.getLastName());
        jo.put("dateOfBirth", employee.getDateOfBirth());
        jo.put("experience", employee.getExperience());
        pw.println();
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}