import java.util.ArrayList;

public class AskInfo implements AISentences {
    private static final String AIname = "Dr. Car: ";
    private DiscoveryGUI ui;
    private String carName;
    private DiscoveryService discovery;
    private String sentenceToRead;

    public AskInfo(DiscoveryGUI gui, String name){
        ui = gui;
        carName = name;
        discovery = new DiscoveryService(name); //ready for connect to service.
    }

    private void askService(){
        String question = ui.getInput();
        ui.addText("User: " + question + "\n");
        ui.cleanInput();
        ArrayList<String> answer = discovery.ask(question);
        if(answer.size() == 0){
            sentenceToRead = "Sorry, I can't answer this question. Please be more specific.\n";
            ui.addTextWithTranslation(AIname + sentenceToRead);
        }else{
            ui.addTextWithTranslation(AIname + "There are " + answer.size() + " answers for your question: ");
            sentenceToRead = "The first answer is " + answer.get(0);
            for(int i = 0; i < answer.size();i++){
                ui.addTextWithTranslation((i + 1) + ". " + answer.get(i));
            }
        }
        ui.moveBarBottom();
        ui.addText("");
        askUserTimeBox2();
        //askUser();
    }

    /**
     * Will be deleted after TimeBox2
     */
    private void askUserTimeBox2(){
        ui.addTextWithTranslation(AIname + "What would you like to know about " + carName + "?\n");
    }

    private void askUser(){
        sentenceToRead = "What would you like to know about " + carName + "?\n";
        ui.addTextWithTranslation(AIname + sentenceToRead);
    }

    @Override
    public void initialize() {
        ui.cleanInput();
        askUser();
    }

    @Override
    public void nextSentence() {
        ui.lockButtons(new int[]{0,1}, false);
    }

    @Override
    public void enter() {
        askService();
    }

    @Override
    public String read() {
        return sentenceToRead;
    }
}
