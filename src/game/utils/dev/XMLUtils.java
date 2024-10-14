package game.utils.dev;

import game.utils.file_system.Paths;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLUtils {
    public static String readConfig(String key) {
        Document document = readXml(Paths.getConfigXml());

        if (document == null)
            return null;

        // Get the root element
        Element root = document.getDocumentElement();
        Element config = (Element) root.getElementsByTagName("config").item(0);

        return config.getAttribute(key);
    }

    public static String readXmlKey(String path, String key) {
        Document document = readXml(path);

        if (document == null)
            return null;

        // Get the root element
        Element root = document.getDocumentElement();
        return root.getElementsByTagName(key).item(0).getTextContent();
    }

    public static Document readXmlExternal(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Document readXml(String path) {
        // Create a DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file to create a Document
            return builder.parse(XMLUtils.class.getResourceAsStream("/" + path));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            return null;
        }
    }

    public static String[] parseXmlArray(String path, String arrayKey) {
        try {
            Document document = readXml(path);

            if (document == null) {
                return null;
            }

            // Get the root element
            Element root = document.getDocumentElement();

            NodeList array = root.getElementsByTagName("array");
            for (int i = 0; i < array.getLength(); i++) {
                Element arrayElement = (Element) array.item(i);
                String arrayName = arrayElement.getAttribute("name");

                if (arrayKey.equals(arrayName)) {
                    // Get the list of <item> elements
                    NodeList nodeList = arrayElement.getElementsByTagName("item");

                    String[] items = new String[nodeList.getLength()];

                    // Print the extracted skins
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Element itemElement = (Element) nodeList.item(j);
                        String skinPath = itemElement.getTextContent();
                        items[j] = skinPath;
                    }

                    return items;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
