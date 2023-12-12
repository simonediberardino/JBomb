package game.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLUtils {
    public static String[] parseXmlArray(String path, String arrayKey) {
        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file to create a Document
            Document document = builder.parse(XMLUtils.class.getResourceAsStream("/" + path));

            // Get the root element
            Element root = document.getDocumentElement();

            // Find the <array> element with name="skins"
            NodeList skinsArray = root.getElementsByTagName("array");
            for (int i = 0; i < skinsArray.getLength(); i++) {
                Element arrayElement = (Element) skinsArray.item(i);
                String arrayName = arrayElement.getAttribute("name");

                if (arrayKey.equals(arrayName)) {
                    // Get the list of <item> elements
                    NodeList skinItems = arrayElement.getElementsByTagName("item");

                    String[] items = new String[skinItems.getLength()];

                    // Print the extracted skins
                    for (int j = 0; j < skinItems.getLength(); j++) {
                        Element itemElement = (Element) skinItems.item(j);
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
