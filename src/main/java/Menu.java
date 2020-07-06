public class Menu implements Page {
    private GUI ui;
    private String[] descriptions;
    private static final String notification = "Please click Enter to continue or click Menu to go back to main menu";
    private static final String transDescription = "This service can translate sentences among five " +
            "languages (Chinese, English, Spanish, French and Japanese)";
    private static final String DiscoveryDescription = "Welcome to the core service of our program. Here you can " +
            "ask questions with the program, and it will attempt to return the best answer!.";
    private static final String DealerSearchDescription = "Welcome to Dealer Search. " +
            "You can search for different car dealers all across America! " +
            "It will provide relevant dealers' names, addresses and telephone numbers.";
    private int mode = 0;
    public Menu(GUI gui){
        ui = gui;
        descriptions = new String[]{transDescription, DiscoveryDescription,DealerSearchDescription,""};
    }


    @Override
    public void start() {
        mode = 0;
        ui.cleanText();
        ui.addText("Hello, What can I help you?");
        ui.addText("1. Translation sentences.");
        ui.addText("2. Ask questions about cars");
        ui.addText("3. Search for car dealers");
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
            case 3:
                new DealerGUI();
                start();
                break;
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
        mode = 3;
        ui.lockUnlockAllButtons(true);
        ui.lockUnlockSpecificButton(false, 3);
        ui.cleanText();
        ui.addText(descriptions[2]);
        ui.addText(notification);
    }

    @Override
    public void tutorial() {
        switch (mode){
            case 0:Emergency.openTutorial();break;
            case 1:Emergency.openTutorial1();break;
            case 2:Emergency.openTutorial2();break;
            case 3:Emergency.openTutorial3();break;
            default:break;
        }
    }

}
