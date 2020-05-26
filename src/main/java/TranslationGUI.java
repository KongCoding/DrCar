import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TranslationGUI{

    private Page translateControl;
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
        textInput = new JTextField(20);
        JButton trans = new JButton("Translate");
        trans.addActionListener(BL);
        String[] languages = {"English-en","Chinese-zh","Spanish-es","France-fr","Japanese-ja"};
        languageChooser1 = new JComboBox<>(languages);
        languageChooser2 = new JComboBox<>(languages);
        bottom.add(languageChooser1);
        bottom.add(languageChooser2);
        bottom.add(textInput);
        bottom.add(trans);
        left = new JTextArea();
        right = new JTextArea();
        left.setLineWrap(true);
        right.setLineWrap(true);
        left.setWrapStyleWord(true);
        right.setWrapStyleWord(true);
        left.setEditable(false);
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
        translateControl.start();
    }

    public String getText(){ return textInput.getText(); }
    public void addText(String newMessage){ left.append(newMessage + "\n");}
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
                case "Translate": translateControl.next();break;
                case "Close": frame.setVisible(false);
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
}
