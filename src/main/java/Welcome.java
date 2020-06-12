public class Welcome implements Page {
    private GUI ui;
    public Welcome(GUI gui){
        ui = gui;
    }
    private void WelcomePage(){
        ui.cleanText();
        ui.addText("Hello, Welcome to Dr. Car!.");
        ui.addText("Please click 1 to see tutorial or click ENTER to continue to the Dr. Car Application");
    }

    private void TourPage(){
        ui.cleanText();
        ui.addText("Sorry, we will add guides in the future.");
    }


    @Override
    public void start() {
        WelcomePage();
    }

    @Override
    public void next() {
//        QAMain.page = new Menu(ui);
//        QAMain.page.start();
        //directly opens the dr. car ui
        DiscoveryGUI QAService = new DiscoveryGUI(true);
        QAService.openGUI();
        start();
    }

    @Override
    public void menu() {
        QAMain.page = new Menu(ui);
        QAMain.page.start();
    }

    @Override
    public void one() {
        TourPage();
    }

    @Override
    public void two() {

    }

    @Override
    public void three() {

    }

    @Override
    public void four() {

    }
}
