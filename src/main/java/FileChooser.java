import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.util.Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FileChooser {
    private String path = "";
    private AskCarsName ai;
    private AipImageClassify aip;

    public FileChooser(AskCarsName AiMode){
        aip = new AipImageClassify("20497789", "1W5Y9LcU5Tb5CohbMOuywWOE",
                "ce4fVAOohFw326Kv5BT6R0UjEB283Ia6");
        ai = AiMode;
        openGUI();
    }

    /**
     * Used as a train method to check each car's possible Chinese answer and write it and corresponding English into xml file.
     * @param args useless
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        String fileName = "Pictures/Volvo/Train/2019-Volvo-XC90-Armoured-Heavy-01.jpg";
        AipImageClassify aip = new AipImageClassify("20497789", "1W5Y9LcU5Tb5CohbMOuywWOE",
                "ce4fVAOohFw326Kv5BT6R0UjEB283Ia6");
        JSONObject res = null;
        try{
            res = aip.carDetect(Util.readFileByBytes(fileName), new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray result = res.getJSONArray("result");
        JSONObject first = (JSONObject) result.get(0);
        String name = (String)first.get("name");
        /*
        The two lines below is for programmers to check whether the English name is what they want.
         */
        System.out.println("English name: " + EnglishName(name));
        System.out.println("Chinese name: "+ name);

        //The line below is for programmers to view all possible Chinese names it could have.
        System.out.println(res.toString());
    }

    private void identify(){
        JSONObject res;
        try{
            res = aip.carDetect(Util.readFileByBytes(path), new HashMap<>());
            JSONArray result = res.getJSONArray("result");
            JSONObject first = (JSONObject) result.get(0);
            String name = (String)first.get("name");
            ai.pictureInput(EnglishName(name));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    private static String EnglishName(String ChineseName){
        String EnglishName = "";
        Document dictionary = DiscoveryService.load("PictureIdentification.xml");
        Element rootElement = dictionary.getRootElement();
        List<Element> names = rootElement.elements("name");
        boolean find = false;
        for(int i = 0; i < names.size() && !find; i++){
            Element name = names.get(i);
            if(ChineseName.equalsIgnoreCase(name.element("original").getText())){
                find = true;
                EnglishName = name.element("translation").getText();
            }
        }
        return EnglishName;
    }

    private void openGUI(){
        JFrame jf = new JFrame("Choose your file");
        JLabel iconShow = new JLabel();
        JFileChooser chooser = new JFileChooser(".");
        JMenu menu = new JMenu("Files");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(event -> {
            chooser.setCurrentDirectory(new File("."));
            int result = chooser.showDialog(jf, "Open the icon file");
            if(result == JFileChooser.APPROVE_OPTION){
                String name = chooser.getSelectedFile().getPath();
                path = name;
                iconShow.setIcon(new ImageIcon(name));
            }
        });
        JMenuItem determine = new JMenuItem("Identify this picture");
        determine.addActionListener(event -> {
            if(!path.equals("")){
                //discoveryControl.identifyPicture(path);
                identify();
                path = "";
                jf.setVisible(false);
            }
        });
        menu.add(open);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.add(determine);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        menu.add(exitItem);
        jf.setJMenuBar(menuBar);
        jf.add(new JScrollPane(iconShow));
        jf.setBounds(100,100,900,700);
        jf.setVisible(true);
    }

}
