import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.QueryOptions;
import com.ibm.watson.discovery.v1.model.QueryResponse;
import com.ibm.watson.discovery.v1.model.QueryResult;

import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.lang.*;


public class DiscoveryService implements Page{

    private static final String Apikey = "wyb2SZ175eQrG4-eFlz9uMnX9Xr36dAd2vCOetLjEtVG";
    private static final String URL = "https://api.eu-gb.discovery.watson.cloud.ibm.com/instances/6b7aebbb-db7d-4618-80c5-ffeb06e9a064";
    private static final String Environment = "4aa11f0e-787b-41c5-ac74-f19435b97a2e";
    private static final String Collection = "69e12469-4a83-4a75-96db-7f835148d78b";

    private FileChooser choose;
    private DiscoveryGUI ui;
    private static Discovery discovery;
    private ReadService read;
    private String latestAnswer = "Hello, I'm Dr. Car!. What car would you like to know about?"; //used for ReadService to read
    boolean voice=false;
    private static final String AIname = "Dr. Car: ";
    private int plansPos;
    private int scriptPos;
    private static String[] plans = {"Hello, I'm Dr. Car!. What car would you like to know about?",

            "Nice, it has many wonderful aspects. Which one do you want to know?",
            "Standard Features: \n178-horsepower, 2.5-liter four-cylinder engine\n" +
                    "Six-speed automatic transmission\n" +
                    "Front-wheel drive\n" +
                    "Bluetooth streaming audio\n" +
                    "iPod/USB-compatible stereo\n" +
                    "60/40-split folding backseat\n" +
                    "Required in every new car: front airbags, antilock brakes and an electronic stability system\n " +
                    "If you would like to know more information about this car, type another question. If you would like to search other cars. Please click \"Return\""};
    public static String[] voicescript = {"You have selected to search using voice input!" + "\n" + " Press \"Enter\" and begin speaking, then enter again to stop",
            "listening . . . . . ",
            "Transcribed text: \"Please search for a Ford F150 2018 model\"","Is this correct? Please type \"yes\" or \"no\"",
            "Great, you have selected the Ford F150 2018 model. What information would you like to find for you?",
            "Searching for relevant information . . . . . . . . . . . . .",
            "According to the \"2018 Ford F-150 Performance Review\" from cars.usnews.com, here is some information relating to \"performace\" for your selected car:\n" +
                    "\"The F-150 comes standard with a 3.3-liter V6 engine that puts out 290 horsepower and 265 pound-feet of torque. " +
                    "Though it’s only the base engine, that doesn’t mean it’s bad. It still has plenty of power for daily driving, and many buyers will be perfectly happy sticking with this V6.\\n\" +\n" +
                    "                \"For more power, you have several options. There’s a turbocharged, 2.7-liter V6 – one of two EcoBoost engines – " +
                    "that produces 325 horsepower and 400 pound-feet of torque. There’s one V8 in the lineup too. " +
                    "It’s a 5.0-liter engine that makes 395 horsepower and 400 pound-feet of torque." +
                    " These engines are more capable, and the V8 has a nice engine note.\"" +"\n\n"+
            "Any other information I can find for you?","Ok, please press \"Return\" or \"Enter\" to get back to the starting page"};
    public static String[] imageScript = {"You have selected to search using image Search!"};
    public static void setService(){
        IamAuthenticator authenticator = new IamAuthenticator(Apikey);
        discovery = new Discovery("2019-04-30", authenticator);
        discovery.setServiceUrl(URL);
    }

    public DiscoveryService(DiscoveryGUI gui){
         voice=false;

        ui = gui;
        setService();
        read = new ReadService();
    }
//What are the minimum hardware requirements
    private ArrayList<String> ask(String question){
        ArrayList<String> answers = new ArrayList<>();
        String answer = "Sorry, I don't know what you want.";
        QueryOptions.Builder queryBuilder = new QueryOptions.Builder(Environment, Collection);
        queryBuilder.naturalLanguageQuery(question);
        QueryResponse queryResponse = discovery.query(queryBuilder.passages(true).build()).execute().getResult();
        StringBuilder sb = new StringBuilder();
        for(QueryResult result: queryResponse.getResults()){
            answers.add((String)result.get("text"));
        }
        if(answers.size() == 0){answers.add(answer);}
//        System.out.println(queryResponse.getPassages().size());
//        for (QueryPassages passages:queryResponse.getPassages()) {
//            System.out.println(passages.getPassageText() + "\n");
//        }
        return answers;
    }

    public void identifyPicture(String path){
        plansPos++;
        ui.addText(AIname + plans[plansPos] + "\n");
        ui.moveBarBottom();
    }

    @Override
    public void start() {
        //ui.addText("Eyes: Hi, what can I do for you?\n");
        //latestAnswer = "Hi, what can I do for you?";
        plansPos = 0;
        ui.addText(AIname + ": " + plans[plansPos] + "\n");
    }

    @Override
    public void next() {
        String question = ui.getInput();
        ui.cleanInput();
        if (!question.isEmpty()) {
            ui.addText("User: " + question + "\n");
        }
        if(!voice) {



            if (plansPos < plans.length-1) {
                plansPos++;
                ui.addText(AIname + plans[plansPos] + "\n");

                latestAnswer = plans[plansPos];
            } else {
                four();
            }

        }
        else{

            if (scriptPos < voicescript.length-1) {
                scriptPos++;
                ui.addText(AIname + voicescript[scriptPos] + "\n");
                latestAnswer = voicescript[scriptPos];
            } else {
                four();
                voice=false;
            }

        }
//        ArrayList<String> answers = ask(question);
//        ui.addText("Eyes:\n");
//        int i = 1;
//        for(String answer : answers){
//            if(answer.equalsIgnoreCase("Sorry, I don't know what you want.")){
//                ui.addText(answer);
//            }else{
//                ui.addText(i + ": " + answer + "\n");
//            }
//            i++;
//        }
//        ui.addText("Eyes: Hi, what can I do for you?");
//        ui.addText("\n");
    }


    @Override
    public void menu() {

    }

    public void getuserinput(){
        String question = ui.getInput();
        ui.cleanInput();
        if (!question.isEmpty()) {
            ui.addText("User: " + question + "\n");
        }
    }
    @Override
    public void one()  {
        voice=true;
        scriptPos = 0;
        ui.addText(AIname + ": " + voicescript[scriptPos] + "\n");



    }

    @Override
    public void two() {
        choose = new FileChooser(this);
        ui.addText(AIname+" \"Identifying car......\"\n\n\n\n") ;


    }

    @Override
    public void three() {
        read.Read(latestAnswer);
    }

    @Override
    public void four() {
        plansPos=0;
        ui.cleanText();
        ui.addText(AIname + plans[plansPos] + "\n");
    }
}
