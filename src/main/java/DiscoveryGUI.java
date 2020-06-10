import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiscoveryGUI {
    private Page discoveryControl;
    private AISentences currentAImode;
    private AskCarsName returnDestination;
    private JFrame frame;
    private JTextArea chat;
    private JTextField textInput;
    private JComboBox<String> languageChooser;
    private JButton[] rightButtons;
    public DiscoveryGUI(){
        discoveryControl = new DiscoveryService(this);
        returnDestination = new AskCarsName(this);
        currentAImode = returnDestination;
    }

    public void openGUI(){
        DiscoveryButtonListener BL = new DiscoveryButtonListener(); //Create Listener for buttons
        //Set the frame
        frame = new JFrame("Discovery Service");
        frame.setLayout(new BorderLayout(30,5));
        frame.addWindowListener(new WindowListen());

        //Set the bottom panel = {TextField, 1 button}
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2,4,4));
        textInput = new JTextField(20);
        textInput.addKeyListener(new KeyListen());
        JButton ask = new JButton("Enter");
        ask.addActionListener(BL);
        bottom.add(textInput);
        bottom.add(ask);
        frame.add(bottom, BorderLayout.SOUTH);

        //Set the middle panel = {1 TextArea}
        chat = new JTextArea();
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);
        JScrollPane chatPane = new JScrollPane(chat);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(chatPane);

        //Set the right panel = {4 buttons}
        JPanel numberPane = new JPanel();
        numberPane.setLayout(new GridLayout(4,1,4,4));
        //numbers = new JButton[4];
        JButton voiceInput = new JButton("Voice Input");
        voiceInput.addActionListener(BL);
        numberPane.add(voiceInput);
        JButton picture = new JButton("Load Icon");
        picture.addActionListener(BL);
        numberPane.add(picture);
        JButton read = new JButton("Read");
        read.addActionListener(BL);
        numberPane.add(read);
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(BL);
        numberPane.add(returnButton);
        rightButtons = new JButton[]{voiceInput, picture, read, returnButton};

//        for(int i = 1; i <= numbers.length; i++){
//            JButton number = i != 4? new JButton("" + i):new JButton("Return");
//            if(i == 3){number = new JButton("Read");}
//            number.addActionListener(BL);
//            numberPane.add(number);
//            numbers[i - 1] = number;
//        }
        frame.add(numberPane, BorderLayout.EAST);

        //Set frame's size and make it visible.
        frame.setBounds(400,300,600,400);
        //f.pack();
        frame.setVisible(true);

        //Initialize the content in Chat.
        discoveryControl.start();
        //currentAImode.initialize();
    }

    public void replacePage(AISentences newMode){
        currentAImode.nextSentence();
        currentAImode = newMode;
        currentAImode.initialize();
    }

    public void lockButtons(int[] lockList, boolean lock){
        for (int number: lockList) {
            rightButtons[number].setEnabled(!lock);
        }
    }

    /**
     * this method can help service classes to know what user enters.
     * @return User's answers or questions.
     **/
    public String getInput(){
        return textInput.getText();
    }

    /**
     * This method will clean user's input. It will be used as soon as user's latest input is caught by system.
     */
    public void cleanInput(){textInput.setText("");}


    /**
     * This method will publish both user's and AI's messages on the chat.
     * @param newMessage The message that is going to be published.
     */
    public void addText(String newMessage){
        chat.append(newMessage + "\n");
    }
    public void addTextWithTranslation(String newMessage){

    }
    public void addTextLine(String newMessage){
        chat.append(newMessage);
    }

    /**
     * Clean the chat.
     */
    public void cleanText(){
        chat.setText("");
    }


    /**
     * This method can make chat block move to the bottom (show the last message) automatically.
     * After I chose a picture and click "identify the picture", the new message was published, but the bar doesn't move.
     * If someone finds any better way to implement this function. Feel free to modify it.
     */
    public void moveBarBottom(){
        chat.selectAll();
    }
    /**
     * This class describes what system should do when user clicks the buttons.
     */
    class DiscoveryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Enter":
                    //discoveryControl.next();
                    currentAImode.enter();
                    break;
                case "Voice Input":

                        discoveryControl.one();

                    break;
                case "Load Icon": discoveryControl.two();break;
                case "Read": discoveryControl.three();break;
                case "Return":
                    //discoveryControl.four();
                    currentAImode = returnDestination;
                    currentAImode.initialize();
                    break;
                default:break;
            }
        }
    }

    /**
     * This class describes what system should do when user clicks the red "X" on the right up corner.
     */
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
    class KeyListen implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                currentAImode.enter();
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
