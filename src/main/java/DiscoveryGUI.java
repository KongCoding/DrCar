import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DiscoveryGUI {
    private Page discoveryControl;
    private JFrame frame;
    private JTextArea chat;
    private JTextField textInput;
    private JComboBox<String> languageChooser;
    private JButton[] numbers;
    public DiscoveryGUI(){
        discoveryControl = new DiscoveryService(this);
    }

    public void openGUI(){
        DiscoveryButtonListener BL = new DiscoveryButtonListener();
        frame = new JFrame("Discovery Service");
        frame.setLayout(new BorderLayout(30,5));
        frame.addWindowListener(new WindowListen());
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2,4,4));
        textInput = new JTextField(20);
        JButton ask = new JButton("Ask");
        ask.addActionListener(BL);
        bottom.add(textInput);
        bottom.add(ask);
        frame.add(bottom, BorderLayout.SOUTH);

        chat = new JTextArea();
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);
        JScrollPane chatPane = new JScrollPane(chat);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(chatPane);

        JPanel numberPane = new JPanel();
        numberPane.setLayout(new GridLayout(4,1,4,4));
        numbers = new JButton[4];
        for(int i = 1; i <= numbers.length; i++){
            JButton number = new JButton("" + i);
            number.addActionListener(BL);
            numberPane.add(number);
            numbers[i - 1] = number;
        }
        frame.add(numberPane, BorderLayout.EAST);
        frame.setBounds(400,300,600,400);
        //f.pack();
        frame.setVisible(true);
        discoveryControl.start();
    }

    public String getText(){
        return textInput.getText();
    }

    public void addText(String newMessage){
        chat.append(newMessage + "\n");
    }

    public void cleanText(){
        chat.setText("");
    }

    class DiscoveryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Ask": discoveryControl.next();
                case "1": discoveryControl.one();
                case "2": discoveryControl.two();
                case "3": discoveryControl.three();
                case "4": discoveryControl.four();
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
