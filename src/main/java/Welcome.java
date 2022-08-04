public class Welcome implements Page {
    private GUI ui;
    public Welcome(GUI gui){
        ui = gui;
    }

    @Override
    public void start() {
        //WelcomePage();
        ui.openPicture("Dr. Car logo design.png");
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ui.OpenGUI();
        menu();
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

    }

    @Override
    public void two() {

    }

    @Override
    public void three() {

    }

    @Override
    public void tutorial() {

    }
}
