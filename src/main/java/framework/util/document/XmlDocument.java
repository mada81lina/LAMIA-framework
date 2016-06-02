package framework.util.document;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlDocument {
    private Document doc;
    private String docPath;


    public XmlDocument(String docPath) {
        this.docPath = docPath;
        File xmlFile = new File(docPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public void saveAndCloseXml() {
        try {
            if (doc != null) {
                doc.getDocumentElement().normalize();
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(docPath).getPath());
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
    }

    public void updateEventsImportNameValue(String newValue) {
        NodeList records = doc.getElementsByTagName("Record");
        for (int i = 0; i < records.getLength(); i++) {
            NodeList fields = doc.getElementsByTagName("Field");
            Element field;
            for (int j = 0; j < fields.getLength(); j++) {
                field = (Element) fields.item(j);
                if (field.getAttribute("FieldName").equalsIgnoreCase("Veranstaltung.Titel")) {
                    Node stringTag = field.getElementsByTagName("String").item(0).getFirstChild();
                    stringTag.setNodeValue(newValue);
                }
            }
        }
    }

    public void updateEventsImportNumberValue(String newValue) {
        NodeList records = doc.getElementsByTagName("Record");
        for (int i = 0; i < records.getLength(); i++) {
            NodeList fields = doc.getElementsByTagName("Field");
            Element field;
            for (int j = 0; j < fields.getLength(); j++) {
                field = (Element) fields.item(j);
                if (field.getAttribute("FieldName").equalsIgnoreCase("Veranstaltung.Schluessel")) {
                    Node stringTag = field.getElementsByTagName("String").item(0).getFirstChild();
                    stringTag.setNodeValue(newValue);
                }
            }
        }
    }

    public void updateVacanciesImportAttributeValue(String newValue) {
        NodeList records = doc.getElementsByTagName("Record");
        for (int i = 0; i < records.getLength(); i++) {
            NodeList fields = doc.getElementsByTagName("Field");
            Element field;
            for (int j = 0; j < fields.getLength(); j++) {
                field = (Element) fields.item(j);
                if (field.getAttribute("FieldName").equalsIgnoreCase("Branch")) {
                    Node stringTag = field.getElementsByTagName("String").item(0).getFirstChild();
                    stringTag.setNodeValue(newValue);
                }
            }
        }
    }
}