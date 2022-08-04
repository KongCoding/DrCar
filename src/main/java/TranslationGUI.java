import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TranslationGUI{

    private Translate translateControl;
    private JFrame frame;
    private JTextArea left, right;
    private JTextField textInput;
    private JComboBox<String> languageChooser1, languageChooser2;
    public TranslationGUI(){
        translateControl = new TranslateService(this);
    }

    public void openGUI(){
        ButtonListener BL = new ButtonListener();
        frame = new JFrame("Translation Service");
        frame.setLayout(new BorderLayout(30,5));
        JPanel bottom = new JPanel();
//        textInput = new JTextField(20);
//        textInput.addKeyListener(new KeyListen());
        JButton trans = new JButton("Translate");
        trans.addActionListener(BL);
        JButton clean = new JButton("Clean");
        clean.addActionListener(BL);
        String[] languages = {"English-en","Chinese-zh","Spanish-es","France-fr","Japanese-ja"};
        languageChooser1 = new JComboBox<>(languages);
        languageChooser2 = new JComboBox<>(languages);
        bottom.add(languageChooser1);
        bottom.add(languageChooser2);
        //bottom.add(textInput);
        bottom.add(trans);
        bottom.add(clean);
        left = new JTextArea();
        right = new JTextArea();
        left.setLineWrap(true);
        right.setLineWrap(true);
        left.setWrapStyleWord(true);
        right.setWrapStyleWord(true);
        //left.setEditable(false);
        right.setEditable(false);
        JScrollPane leftPane = new JScrollPane(left);
        JScrollPane rightPane = new JScrollPane(right);
        leftPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2, 10, 10));
        p.add(leftPane);
        p.add(rightPane);
        frame.add(p);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setBounds(500,300,600,300);
        frame.addWindowListener(new WindowListen());
        frame.setVisible(true);
        cleanText();
        setLeft("Please enter the message you want to translate");
    }

    public String getText(){ return left.getText(); }
    public void cleanText(){left.setText("");right.setText("");}
    public void setLeft(String message){left.setText(message);}
    public void setRight(String message){right.setText(message);}
    public String[] getTranslationMode(){
        String source = (String)languageChooser1.getSelectedItem();
        String destination = (String)languageChooser2.getSelectedItem();
        return new String[]{source.substring(source.indexOf('-') + 1),
                destination.substring(destination.indexOf('-') + 1)};
    }

    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Translate": translateControl.translateFromAnyToAny();break;
                case "Close": frame.setVisible(false);
                case "Clean": cleanText();
                default:break;
            }
        }
    }

    class WindowListen implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            frame.setVisible(false);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            System.out.println("Window has been closed");
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    /**
     * This class describes what system should fo when user click "Enter" on keyboard.
     */
    class KeyListen implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                translateControl.translateFromAnyToAny();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
