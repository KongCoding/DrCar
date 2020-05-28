import javax.swing.*;
import java.io.File;

public class FileChooser {
    private String path = "";
    private DiscoveryService discoveryControl;

    public FileChooser(DiscoveryService ds){
        discoveryControl = ds;
        openGUI();
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
                discoveryControl.identifyPicture(path);
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
