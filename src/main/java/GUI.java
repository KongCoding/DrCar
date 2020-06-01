import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GUI {
    private JFrame f;
    private JTextArea ta;
    private JTextField tf;
    private JButton[] ButtonList;
    public void OpenGUI(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello, Welcome Dr. Car!");
        f = new JFrame("Main Window");
        f.setLayout(new BorderLayout(30, 5));
        ButtonListener bL = new ButtonListener(this);
        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(1,4,4,4));
        ButtonList = new JButton[4];
        for(int i = 1; i <= 4;i++){
            JButton numberButton = new JButton(""+i);
            numberButton.addActionListener(bL);
            digitPanel.add(numberButton);
            ButtonList[i - 1] = numberButton;
        }
        f.add(digitPanel, BorderLayout.NORTH);
        JPanel newP = new JPanel();
        newP.setLayout(new GridLayout(1,1));
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        newP.add(ta);
        JPanel p = new JPanel();
        JButton hit = new JButton("Enter");
        hit.addActionListener(bL);
        JButton close = new JButton("Close");
        close.addActionListener(bL);
        JButton menu = new JButton("Menu");
        menu.addActionListener(bL);
        tf = new JTextField(20);
        p.add(tf);
        p.add(hit);
        p.add(close);
        p.add(menu);
        f.add(p, "South");
        f.addWindowListener(new WindowListen());
        f.add(newP);
        f.setBounds(400,200,600,400);
        //f.pack();
        f.setVisible(true);
    }

    public void addText(String newMessage){
        ta.append(newMessage + "\n");
    }

    public void cleanText(){ta.setText("");}

    /*
    True to lock all buttons, false to unlock.
    */
    public void lockUnlockAllButtons(boolean lock){
        for(int i = 0; i < ButtonList.length; i++){
            ButtonList[i].setEnabled(!lock);
        }
    }

    public void lockUnlockSpecificButton(boolean lock, int button){
        ButtonList[button - 1].setEnabled(!lock);
    }

    class WindowListen implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            ReadService.Delete();
            System.exit(0);
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
    class ButtonListener implements ActionListener{
        private GUI ui;
        public ButtonListener(GUI gui){
            ui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Close": ReadService.Delete();System.exit(0);break;
                case "Menu": QAMain.page.menu();break;
                case "Enter": QAMain.page.next();break;
                case "1": QAMain.page.one();break;
                case "2": QAMain.page.two();break;
                case "3": QAMain.page.three();break;
                case "4": QAMain.page.four();break;
                default:break;
            }
        }
    }
}
