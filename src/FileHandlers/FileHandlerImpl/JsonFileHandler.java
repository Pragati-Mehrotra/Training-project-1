package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class JsonFileHandler  implements MyFileHandler {

    private String readFileName, writeFileName;
    private static int count;

    JsonFileHandler(String readPath, String writePath){
        this.readFileName = readPath;
        this.writeFileName = writePath;
        count = 0;
    }

    @Override
    public Employee read(){

        //JSONParser jsonParser = new JSONParser();


        try{

                ObjectMapper mapper = new ObjectMapper();

            // Deserialize JSON file into Java object.
                Employee newEmployee = mapper.readValue(writeFileName, Employee.class);
                return newEmployee;
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*FileReader fileReader = new FileReader(this.readFileName);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);


            JSONObject jsonObject = jsonArray.getJSONObject(count);
            count++;

            Employee employeeData = new Employee();
            //employeeData
//            Employee employeeData = (Employee) jsonArray;
            return employeeData;*/



        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public void write(Employee employee){

        try{



            ObjectMapper mapper = new ObjectMapper();

            File file = new File(writeFileName);
            try {
                // Serialize Java object info JSON file.
                mapper.writeValue(file, employee);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*JSONObject employeeObject = new JSONObject();

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
*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {



    }
}
