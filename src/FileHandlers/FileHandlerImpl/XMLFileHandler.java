package FileHandlers.FileHandlerImpl;

import FileHandlers.MyFileHandler;
import model.Employee;
import java.io.File;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.List;
import java.util.ArrayList;
import org.xml.sax.SAXException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import java.text.DateFormat;
import java.text.ParseException;


public class XMLFileHandler implements MyFileHandler {

    private String inputPath;

    private String outputPath;



    private int counter ;

    XMLFileHandler(String path1,String path2){
        this.inputPath = path1;
        this.outputPath =path2;
        counter =0;
        }

    @Override
    public Employee read() {

        Employee emp =null;

        try {
            File inputFile = new File(inputPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("employee");
            emp = getEmployee(nList.item(counter));
            counter++;
            return emp;

        }

        catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        return emp;

        }

    private Employee getEmployee(Node node) {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        Employee emp = new Employee();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            emp.setFirstName(getTagValue("firstName", element));
            emp.setLastName((getTagValue("lastName", element)));
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String s =getTagValue("dateOfBirth", element);
            System.out.println(s);
            Date dob;
            try {
                dob = df.parse(s);
                emp.setDateOfBirth(dob);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            emp.setExperience(Double.parseDouble(getTagValue("experience", element)));
        }

        return emp;
    }


    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void write(Employee emp) {

    }

}
