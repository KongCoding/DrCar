public class QAMain {
    /*Author:
        Zhenhao Lu

    */

    public static Page page;
    public static void main(String[] args) {
        page = null;
        GUI ui = new GUI();
        //ui.OpenGUI();
        page = new Welcome(ui);
        page.start();
    }
}
