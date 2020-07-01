import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public class GUI {
    private JFrame f;
    private JTextArea ta;
    private JTextField tf;
    private JButton[] ButtonList;
    public GUI(){
        f = new JFrame("Main Window");
    }
    public void OpenGUI(){
        System.out.println("Hello, Welcome to Dr. Car!");
        f.setContentPane(new JPanel());
        f.setLayout(new BorderLayout(30, 5));
        ButtonListener bL = new ButtonListener();
        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(1,5,4,4));
        ButtonList = new JButton[4];
        for(int i = 1; i <= 4;i++){
            JButton numberButton = new JButton(""+i);
            numberButton.addActionListener(bL);
            digitPanel.add(numberButton);
            ButtonList[i - 1] = numberButton;
        }

        JButton webButton = new JButton("About Us");
        webButton.addActionListener(bL);
        digitPanel.add(webButton);

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

    public void openPicture(String fileName){
        JPanel background = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                try{
                    Image bg = ImageIO.read(new File(fileName));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        f.setContentPane(background);
        f.setBounds(400,200,600,400);
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

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Close": ReadService.Delete();System.exit(0);break;
                case "Menu": QAMain.page.menu();break;
                case "Enter": QAMain.page.next();break;
                case "1":QAMain.page.one(); break;
                case "2": QAMain.page.two();break;
                case "3": QAMain.page.three();break;
                case "4": QAMain.page.four();break;
                case "About Us": Emergency.externalWebsite();
                default:break;
            }
        }
    }
}
