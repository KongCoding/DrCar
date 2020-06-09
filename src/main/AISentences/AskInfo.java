public class AskInfo implements AISentences {
    private DiscoveryGUI ui;
    private String carName;

    public AskInfo(DiscoveryGUI gui, String name){
        ui = gui;
        carName = name;
    }

    @Override
    public void initialize() {
        ui.cleanInput();
        ui.addText("What do you want to know about " + carName + "?");
        System.out.println("Success");
    }

    @Override
    public void nextSentence() {
        ui.lockButtons(new int[]{0,1,2}, false);
    }

    @Override
    public void enter() {
        System.out.println("Act Right");
    }
}
