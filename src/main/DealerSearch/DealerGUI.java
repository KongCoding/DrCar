import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class DealerGUI {

    public static void main(String[] args) {
        new DealerGUI();
    }

    private JFrame frame;
    protected JTextArea chat;
    private DealerDBQuery db;
    private JComboBox<String> car;
    private JComboBox<String> state;

    @SuppressWarnings("all")
    public DealerGUI(){
        db = new DealerDBQuerySQLite();
        if(db.checkConnect()){
            ArrayList<String> states = db.setStates();
            openGUI(states.toArray(new String[states.size()]));
        }
    }

    private void openGUI(String[] states){
        DealerButton buttonListener = new DealerButton();
        frame = new JFrame("Dealer Searching");
        frame.setLayout(new BorderLayout(30,5));
        frame.addWindowListener(new WindowListen());

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,4,4,4));
        car = new JComboBox<>(new String[]{"Select A Car", "Chevrolet", "Honda", "Mercedes Benz","Volvo"});
        state = new JComboBox<>(states);
        JButton search = new JButton("Search");
        search.addActionListener(buttonListener);
        JButton close = new JButton("Close");
        close.addActionListener(buttonListener);
        bottom.add(car);
        bottom.add(state);
        bottom.add(search);
        bottom.add(close);
        frame.add(bottom, BorderLayout.SOUTH);

        chat = new JTextArea();
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);
        JScrollPane chatPane = new JScrollPane(chat);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(chatPane);

        //Set frame's size and make it visible.
        frame.setBounds(400,300,600,400);
        //f.pack();
        frame.setVisible(true);
    }

    class DealerButton implements ActionListener{

        @Override
        @SuppressWarnings("all")
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Search":
                    String carName = (String)car.getSelectedItem();
                    String stateName = (String) state.getSelectedItem();
                    if(!carName.contains("Select") && !stateName.contains("Select")){
                        try{
                            ArrayList<String> answer = db.Search(carName, stateName);;
                            StringBuilder sb = new StringBuilder();
                            if(answer.size() > 0){
                                sb.append("We find " + answer.size() + " dealers for you:\n");
                                for(String str: answer)
                                    sb.append(str + "\n");
                            }else
                                sb.append("Sorry, we didn't find any dealers in this region");
                            chat.setText(sb.toString());
                        }catch (SQLException e1){
                            Emergency.emergencyPlanCLoseWindow(frame,"Sorry, your search is illegal.");
                        }
                    }
                    break;
                case "Close":
                    frame.setVisible(false);break;
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
