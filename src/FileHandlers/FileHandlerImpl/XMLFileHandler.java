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

    private Document docRead;

    private Document docWrite;

    private int readCounter ;

    private Element rootElement;

    private StreamResult file;


    private  TransformerFactory transformerFactory;


   public XMLFileHandler(String path1,String path2) {
        this.inputPath = path1;
        this.outputPath = path2;
        readCounter = 0;

        try {
            File inputFile = new File(inputPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            docRead = dBuilder.parse(inputFile);
            docRead.getDocumentElement().normalize();
        }
        catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            docWrite = dBuilder.newDocument();
            //add elements to Document
            rootElement =
                    docWrite.createElementNS("com.coviam.TrainingProject", "Employees");
            //append root element to document
            docWrite.appendChild(rootElement);

            file = new StreamResult(new File(outputPath));
            transformerFactory = TransformerFactory.newInstance();


            transformerFactory = TransformerFactory.newInstance();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Employee read() {

        Employee emp =null;

            NodeList nList = docRead.getElementsByTagName("employee");
            emp = getEmployeeRead(nList.item(readCounter));
            readCounter++;
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
            //System.out.println(s);
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
        try {
            //append first child element to root element
            rootElement.appendChild(getWriteEmployee(docWrite,emp.getFirstName(), emp.getLastName(), emp.getDateOfBirth().toString(), emp.getExperience().toString()));


            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(docWrite);

            //write data
            //transformer.transform(source, console);
            transformer.transform(source, file);
            //System.out.println("Done with X");

        }
        catch (Exception e) {
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
