import java.util.ArrayList;
import java.util.List;

public class AskCarsName implements AISentences{

    private static final String AIname = "Dr. Car: ";
    private static List<String> cars = new ArrayList<>(){{
        add("Volvo XC90");
        add("Volvo XC60");
    }};
    private static List<String> companies = new ArrayList<>(){{
            add("Volvo");
            add("Other companies");
    }};
    private DiscoveryGUI ui;
    public AskCarsName(DiscoveryGUI gui){
        ui = gui;
    }

    @Override
    public void initialize() {
        ui.cleanText();
        ui.cleanInput();
        sayHello();
    }

    private void sayHello(){
        ui.addText(AIname + "Hello, I'm Dr. Car!. Which car would you like to know about?\n");
    }

    /**
     *
     */
    @Override
    public void nextSentence() {
        //Link to next mode (with a given mode, ask different questions.)
        ui.lockButtons(new int[]{0, 1, 2}, true);
    }

    @Override
    public void enter() {
        String carName = ui.getInput();
        ui.cleanInput();
        ui.addText("User: " + carName);
        boolean findCar = false, findCompany = false;
        int company = -1;
        for(int i = 0; i < cars.size() && !findCar; i++){
            findCar = cars.get(i).equalsIgnoreCase(carName);
            if(findCar)
                carName = cars.get(i);
        }
        if(findCar){
            System.out.println("Find");
            ui.replacePage(new AskInfo(ui, carName));
        }else{
            for(int i = 0; i < companies.size() && !findCompany;i++){
                findCompany = carName.toLowerCase().contains(companies.get(i).toLowerCase());
                if(findCompany)
                    company = i;
            }
            if(findCompany)
                carList(company);
            else
                misunderstand();
        }
    }

    private void carList(int company){
        ui.addTextWithTranslation(AIname + "I know multiple types of cars belong to " + companies.get(company) + ".");
        ui.addTextWithTranslation("Please choose the car you want to know and type its whole name: ");
        for(int i = 0; i < cars.size();i++){
            if(cars.get(i).contains(companies.get(company))){
                ui.addText("\t" + cars.get(i));
            }
        }
        ui.addText("\n");
    }

    private void misunderstand() {
        ui.addTextWithTranslation(AIname + "Sorry, I cannot identify this car. Please give me concrete names\n");
        sayHello();
    }
}
