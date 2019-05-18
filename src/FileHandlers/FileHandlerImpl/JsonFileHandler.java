package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;

import jdk.nashorn.internal.parser.JSONParser;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;

import java.io.*;

public class JsonFileHandler  implements MyFileHandler {

    private String readFileName, writeFileName;
    private static int count=0;

    @Override
    public Employee read(){

        JSONParser jsonParser = new JSONParser();


        try{

            FileReader fileReader = new FileReader(this.readFileName);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);


            JSONObject jsonObject = jsonArray.getJSONObject(count);
            count++;

            Employee employeeData = new Employee();
            //employeeData
//            Employee employeeData = (Employee) jsonArray;
            return employeeData;



        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public void write(Employee employee){

        try{


            JSONObject employeeObject = new JSONObject();

            employeeObject.put("employee", employee);

            //Add employees to list
            JSONArray employeeList = new JSONArray();
            employeeList.add(employeeObject);

            //Write JSON file
            try (FileWriter file = new FileWriter(this.writeFileName)) {
                file.write(employeeList.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {



    }
}
