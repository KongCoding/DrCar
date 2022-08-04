import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public interface DiscoveryService {
    static Document load(String filename){
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // read XML file to get Document variable.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    void setService(String carName, JFrame frame);
    ArrayList<String> ask(String question);
}
