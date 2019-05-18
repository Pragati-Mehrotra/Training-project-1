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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.DateFormat;
import java.text.ParseException;


public class XMLFileHandler implements MyFileHandler {

    private String inputPath;

    private String outputPath;



    private int readCounter ;

    XMLFileHandler(String path1,String path2){
        this.inputPath = path1;
        this.outputPath =path2;
        readCounter =0;
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
            emp = getEmployeeRead(nList.item(readCounter));
            readCounter++;
            return emp;

        }

        catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        return emp;

        }

    private static Employee getEmployeeRead(Node node) {
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


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void write(Employee emp) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement =
                    doc.createElementNS("https://www.journaldev.com/employee", "Employees");
            //append root element to document
            doc.appendChild(rootElement);

            //append first child element to root element
            rootElement.appendChild(getWriteEmployee(doc, emp.getFirstName(), emp.getLastName(), emp.getDateOfBirth().toString(), emp.getExperience().toString()));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(outputPath));

            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static Node getWriteEmployee(Document doc,  String firstname, String lastname, String dateofbirth,
                                         String experience) {
        Element employee = doc.createElement("Employee");


        employee.appendChild(getEmployeeElements(doc, employee, "firstname", firstname));


        employee.appendChild(getEmployeeElements(doc, employee, "lastName", lastname));


        employee.appendChild(getEmployeeElements(doc, employee, "dateOfBirth", dateofbirth));

        employee.appendChild(getEmployeeElements(doc, employee, "experience", experience));

        return employee;
    }


    //utility method to create text node
    private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }


}
