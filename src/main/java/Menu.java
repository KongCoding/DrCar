public class Menu implements Page {
    private GUI ui;
    private String[] descriptions;
    private static final String notification = "Please click Enter to continue or click Menu to go back to main menu";
    private static final String transDiscription = "This service can translate sentences among five " +
            "languages (Chinese, English, Spanish, French and Japanese)";
    private static final String DiscoveryDiscription = "Welcome to the core service of our program. Here you can " +
            "ask questions you are interested in to the program, and it will find the best fitted answer for you.";
    private int mode = 0;
    public Menu(GUI gui){
        ui = gui;
        descriptions = new String[]{transDiscription, DiscoveryDiscription,"",""};
    }


    @Override
    public void start() {
        mode = 0;
        ui.cleanText();
        ui.addText("Hello, What can I help you?");
        ui.addText("1. Translation a sentence.");
        ui.addText("2. Ask questions to program");
        ui.lockUnlockAllButtons(false);
    }

    @Override
    public void next() {
        switch (mode){
            case 1: TranslationGUI translateService = new TranslationGUI();
                translateService.openGUI();
                start(); break;
            case 2:
                DiscoveryGUI QAService = new DiscoveryGUI(true);
                QAService.openGUI();
                start();break;
            case 3: break;
            case 4: break;
            default: break;
        }
    }

    @Override
    public void menu() {
        start();
    }

    @Override
    public void one() {
//        TranslationGUI translateService = new TranslationGUI();
//        translateService.openGUI();
        mode = 1;
        ui.lockUnlockAllButtons(true);
        ui.lockUnlockSpecificButton(false, 1);
        ui.cleanText();
        ui.addText(descriptions[0]);
        ui.addText(notification);
    }

    @Override
    public void two() {
        mode = 2;
        ui.lockUnlockAllButtons(true);
        ui.lockUnlockSpecificButton(false, 2);
        ui.cleanText();
        ui.addText(descriptions[1]);
        ui.addText(notification);
    }

    @Override
    public void three() {

    }

    @Override
    public void four() {
        System.out.println("4");
    }
}
