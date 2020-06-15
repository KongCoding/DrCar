import javax.swing.*;

public class DiscoveryGUI2 extends DiscoveryGUI {
    public DiscoveryGUI2() {
        super(false);
    }

    @Override
    public void addText(String newMessage){
        chat.setText("");
        super.addText(newMessage);
    }

    @Override
    public void addTextWithTranslation(String message){
        chat.setText("");
        super.addTextWithTranslation(message);
    }

    public String getChat(){
        return chat.getText();
    }

    public void setLanguage(String language){
        languageChooser = new JComboBox<>(new String[]{language});
    }

    public void userInput(String message){textInput.setText("");}

    public boolean getRightButtonsState(int number){
        JButton button = rightButtons[number];
        return button.isEnabled();
    }
}
