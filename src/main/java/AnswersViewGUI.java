import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class AnswersViewGUI {

    private ArrayList<String> answers;
    private JFrame frame;
    private JTextArea chat;
    private JComboBox<String> language;
    private int answersNumber;
    private TranslateService translation;
    private JButton[] buttons;

//    public static void main(String[] args) {
//        ArrayList<String> j = new ArrayList<>(){{
//            add("1. B");
//            add("2. C");
//        }};
//        AnswersViewGUI a = new AnswersViewGUI(j);
//    }

    public AnswersViewGUI(ArrayList<String> results){
        answers = results;
        answersNumber = 0;
        translation = new TranslateService();
        openGUI();
    }

    private void openGUI(){
        AnswersButtonListener abl = new AnswersButtonListener();
        frame = new JFrame("Discovery Service");
        frame.setLayout(new BorderLayout(30,5));
        frame.addWindowListener(new WindowListen());

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,4,4,4));
        String[] languages = {"English","Japanese", "Chinese", "Spanish"};
        language = new JComboBox<>(languages);
        JButton previous = new JButton("Previous");
        previous.addActionListener(abl);
        JButton next = new JButton("Next");
        next.addActionListener(abl);
        JButton close = new JButton("Close");
        close.addActionListener(abl);
        bottom.add(language);
        bottom.add(previous);
        bottom.add(next);
        bottom.add(close);
        buttons = new JButton[]{previous, next};
        frame.add(bottom, BorderLayout.SOUTH);

        chat = new JTextArea();
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);
        JScrollPane chatPane = new JScrollPane(chat);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(chatPane);

        frame.setBounds(400,300,600,400);
        //f.pack();
        frame.setVisible(true);
        addTextWithTranslation();
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(!(answersNumber == (answers.size() - 1)));
    }

    private void addTextWithTranslation(){
        String newMessage = answers.get(answersNumber);
        String target = (String)language.getSelectedItem();
        String translatedAnswer = target.equals("English") ? newMessage : translation.translate(newMessage, target);
        chat.setText(translatedAnswer);
    }


    class AnswersButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Previous":
                    answersNumber -= 1;
                    buttons[0].setEnabled(!(answersNumber == 0));
                    buttons[1].setEnabled(true);
                    addTextWithTranslation();
                    break;
                case "Next":
                    answersNumber ++;
                    buttons[1].setEnabled(!(answersNumber == (answers.size() - 1)));
                    buttons[0].setEnabled(true);
                    addTextWithTranslation();
                    break;
                case "Close":
                    frame.setVisible(false);
                    break;
                default:break;
            }
        }
    }

    private class WindowListen implements WindowListener {

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
