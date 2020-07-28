import java.util.ArrayList;
import java.util.List;

public class AskCarsName implements AISentences{

    private static final String AIname = "Dr. Car: ";
    private static List<String> cars = new ArrayList<>(){{
        add("Volvo XC90");
        add("Volvo XC60");
        add("Volvo XC40");
        add("Volvo V60");
        add("Volvo S90");
        add("Mercedes Benz A-Class");
        add("Mercedes Benz C-Class");
        add("Mercedes Benz E-Class");
        add("Mercedes Benz GLC");
        add("Mercedes Benz GLE");
        add("Honda CR-V");
        add("Honda Insight");
        add("Honda Fit");
        add("Honda Ridgeline");
        add("Honda Odyssey");
        add("Chevrolet Camaro");
        add("Chevrolet Colorado");
        add("Chevrolet Impala");
        add("Chevrolet Malibu");
        add("Chevrolet Traverse");
    }};
    private static List<String> companies = new ArrayList<>(){{
            add("Volvo");
            add("Mercedes");
            add("Benz");
            add("Honda");
            add("Chevrolet");
    }};
    private DiscoveryGUI ui;
    private String sentenceToRead;
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
        sentenceToRead = "Hello, I'm Dr. Car!. Which car would you like to know about? \n";
        for(int space = 0; space < 14; space++)
            sentenceToRead += " ";
        sentenceToRead += "I know ";
        for(int i = 0; i < companies.size();i++){
            if(i == companies.size() - 1)
                sentenceToRead += companies.get(i) + ".\n";
            else if(!companies.get(i).equals("Mercedes"))
                sentenceToRead += companies.get(i) + ", ";
        }
        ui.addText(AIname + sentenceToRead);
    }

    /**
     *
     */
    @Override
    public void nextSentence() {
        //Link to next mode (with a given mode, ask different questions.)
        ui.lockButtons(new int[]{1}, true);
    }

    @Override
    public void enter() {
        String carName = ui.getInput();
        ui.cleanInput();
        ui.addText("User: " + carName + "\n");
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

    @Override
    public String read() {
        return sentenceToRead;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return new ArrayList<>();
    }

    public void pictureInput(String carName){
        ui.addTextWithTranslation(AIname + "I think this car is " + carName +
                ". If you are not looking for this car, please click \"Return\" and try again.\n");
        if(cars.contains(carName)){
            ui.replacePage(new AskInfo(ui, carName));
        }else{
            ui.addTextWithTranslation("Sorry, our database does not contain any information about " + carName + "\n");
            sayHello();
        }
    }

    private void carList(int company){
        sentenceToRead = "I know multiple" + companies.get(company) + " cars!";
        String temp = "Please choose the car you want to know about and type its whole name: ";
        ui.addTextWithTranslation(AIname + sentenceToRead);
        ui.addTextWithTranslation(temp);
        sentenceToRead += temp;
        for (String car : cars) {
            if (car.contains(companies.get(company))) {
                ui.addText("\t" + car);
            }
        }
        ui.addText("\n");
    }

    private void misunderstand() {
        sentenceToRead = "Sorry, I cannot identify this car. Please give me an exact name\n";
        ui.addTextWithTranslation(AIname + "Sorry, I cannot identify this car. Please give me an exact name\n");
        sayHello();
    }
}
