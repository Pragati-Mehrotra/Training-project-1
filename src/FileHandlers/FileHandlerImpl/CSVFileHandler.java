package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;
import model.Employee;

import java.io.*;
import java.text.SimpleDateFormat;

public class CSVFileHandler implements MyFileHandler {
    String csvFileForRead;
    String csvFileForWrite;
    BufferedReader bufferedReader = null;
    FileWriter fileWriter = null;


    public CSVFileHandler(String ReadPath,String WritePath) throws IOException {
        csvFileForRead=ReadPath;
        csvFileForWrite=WritePath;
        bufferedReader = new BufferedReader(new FileReader(csvFileForRead));
        fileWriter = new FileWriter(csvFileForWrite);

    }

    @Override
    public Employee read() throws Exception {
        Employee employee=new Employee();
        String line = "";
        String cvsSplitBy = ",";
        try {
            line = bufferedReader.readLine();
            if(line==null)
                return null;
            //System.out.println(line);
            String d=line;
            String[] g=d.split(cvsSplitBy);
                if (g.length != 3)
                    throw new IllegalAccessException();
            employee.setFirstName(g[0]);
            employee.setLastName(g[1]);
            employee.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(g[2]));
            employee.setExperience(Double.parseDouble(g[3]));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public void write(Employee employee) throws IOException {
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        if(employee==null)
            return ;

            fileWriter.append(employee.getFirstName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(employee.getLastName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(employee.getDateOfBirth()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(employee.getExperience()));
            fileWriter.append(NEW_LINE_SEPARATOR);

        fileWriter.flush();
    }
}
