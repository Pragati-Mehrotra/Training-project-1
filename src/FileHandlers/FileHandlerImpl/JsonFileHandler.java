package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class JsonFileHandler  implements MyFileHandler {

    private String readFileName, writeFileName;

    @Override
    public Employee read(){


        try{
/*            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(this.readFileName);
            Object object = jsonParser.parse(fileReader);

            Employee employeeData = (Employee) object;
            return employeeData;*/

            ObjectMapper objectMapper = new ObjectMapper();
            Employee employee = objectMapper.readValue(new File(this.readFileName), Employee.class);
            return employee;

        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void write(Employee employee) throws Exception{

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

