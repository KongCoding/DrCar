import java.util.ArrayList;

public class AskInfo implements AISentences {
    private static final String AIname = "Dr. Car: ";
    private DiscoveryGUI ui;
    private String carName;
    private DiscoveryService discovery;

    public AskInfo(DiscoveryGUI gui, String name){
        ui = gui;
        carName = name;
        //discovery = new DiscoveryService(name); //ready for connect to service.
    }

    private void askService(){
        String question = ui.getInput();
        ui.cleanInput();
        //ArrayList<String> answer = discovery.ask(question);
        ArrayList<String> answer = new ArrayList<>(){{
            add("A");
            add("B");
        }};
        if(answer.size() == 0){
            ui.addText(AIname + "Sorry, I cannot answer your question. Please ask more specific.\n");
        }else{
            ui.addText(AIname + "There are totally " + answer.size() + " answers for your question: ");
            for(int i = 0; i < answer.size();i++){
                ui.addText((i + 1) + ". " + answer.get(i));
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ui.addText("");
        askUser();

    }

    private void askUser(){
        ui.addText(AIname + "What do you want to know about " + carName + "?\n");
    }

    @Override
    public void initialize() {
        ui.cleanInput();
        askUser();
        System.out.println("Success");
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
