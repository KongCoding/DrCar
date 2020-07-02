import java.util.ArrayList;

public class AskInfo implements AISentences {
    private static final String AIname = "Dr. Car: ";
    private DiscoveryGUI ui;
    private String carName;
    private DiscoveryService discovery;
    private String sentenceToRead;
    private ArrayList<String> answer;

    public AskInfo(DiscoveryGUI gui, String name){
        ui = gui;
        carName = name;
        discovery = new DiscoveryServiceIBM(name, ui.getFrame()); //ready for connect to service.
    }

    private void askService(){
        String question = ui.getInput();
        ui.addText("User: " + question + "\n");
        ui.cleanInput();
        answer = discovery.ask(question);
        if(answer.size() == 0){
            sentenceToRead = "Sorry, I can't answer this question. Please be more specific. " +
                    "What would you like to know about " + carName + "?\n";
            ui.addTextWithTranslation(AIname + sentenceToRead);
        }else{
            ui.addTextWithTranslation(AIname + "There are " + answer.size() + " answers for your question: ");
            sentenceToRead = "The first answer is " + answer.get(0);
            ui.addTextWithTranslation("1. " + answer.get(0));
            ui.lockButtons(new int[]{3}, answer.size() == 1);
            if(answer.size() > 1){
                ui.addTextWithTranslation("\n" + AIname + "If you want to view other answers, please " +
                        "click \"View Results\" on the right side. If not, what else would you want to know about " +
                        carName + "?\n");
            }else
                ui.addTextWithTranslation("\n" + AIname + "What else would you like to know about " +
                        carName + "? If you want to ask about other cars, please click \"Return\"\n");
        }
        ui.moveBarBottom();
        ui.addText("");
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
        ui.lockButtons(new int[]{1}, false);
    }

    @Override
    public void enter() {
        askService();
    }

    @Override
    public String read() {
        return sentenceToRead;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answer;
    }
}
