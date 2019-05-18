package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;
import model.Employee;

import java.io.*;
import java.text.SimpleDateFormat;

public class CSVFileHandler implements MyFileHandler {
    String csvFileForRead;
    String csvFileForWrite;
    BufferedReader br = null;
    FileWriter fileWriter = null;


    public CSVFileHandler(String ReadPath,String WritePath) throws FileNotFoundException,IOException {
        csvFileForRead=ReadPath;
        csvFileForWrite=WritePath;
        br = new BufferedReader(new FileReader(csvFileForRead));
        fileWriter = new FileWriter(csvFileForWrite);

    }

    @Override
    public Employee read() throws Exception {
        Employee employee=new Employee();
        String line = "";
        String cvsSplitBy = ",";
        try {
            line = br.readLine();
            if(line==null)
                return null;
            System.out.println(line);
            String d=line;
            String[] g=d.split(cvsSplitBy);
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
        System.out.println(employee);
        try {
            fileWriter.append(employee.getFirstName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(employee.getLastName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(employee.getDateOfBirth()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(employee.getExperience()));
            fileWriter.append(NEW_LINE_SEPARATOR);

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
        fileWriter.flush();
    }
}
