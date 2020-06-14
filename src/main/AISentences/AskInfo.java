import java.util.ArrayList;

public class AskInfo implements AISentences {
    private static final String AIname = "Dr. Car: ";
    private DiscoveryGUI ui;
    private String carName;
    private DiscoveryService discovery;

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
//        ArrayList<String> answer = new ArrayList<>(){{
//            add("The top speed is 30 mph.");
//            add("B");
//        }};
        if(answer.size() == 0){
            ui.addTextWithTranslation(AIname + "Sorry, I do not understand this question. Please be more specific.\n");
        }else{
            ui.addTextWithTranslation(AIname + "There are " + answer.size() + " answers for your question: ");
            for(int i = 0; i < answer.size();i++){
                ui.addTextWithTranslation((i + 1) + ". " + answer.get(i));
            }
        }
        ui.addText("");
        askUser();

    }

    private void askUser(){
        ui.addTextWithTranslation(AIname + "What would you like to know about " + carName + "?\n");
    }

    @Override
    public void initialize() {
        ui.cleanInput();
        askUser();
    }

    @Override
    public void nextSentence() {
        ui.lockButtons(new int[]{0,1,2}, false);
    }

    @Override
    public void enter() {
        askService();
    }
}
