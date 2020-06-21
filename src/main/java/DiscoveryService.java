import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.QueryOptions;
import com.ibm.watson.discovery.v1.model.QueryPassages;
import com.ibm.watson.discovery.v1.model.QueryResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.lang.*;
import java.util.List;


public class DiscoveryService{

//    private static final String Apikey = "wyb2SZ175eQrG4-eFlz9uMnX9Xr36dAd2vCOetLjEtVG";
//    private static final String URL = "https://api.eu-gb.discovery.watson.cloud.ibm.com/instances/6b7aebbb-db7d-4618-80c5-ffeb06e9a064";
//    private static final String Environment = "4aa11f0e-787b-41c5-ac74-f19435b97a2e";
//    private static final String Collection = "0ea5c731-6289-46cd-8060-85e19c570880";
    private static final String filter1 = "extracted_metadata.filename:";

//    private FileChooser choose;
    private String filter;
    private String environment;
    private String collectionID;
//    private DiscoveryGUI ui;
    private static Discovery discovery;
//    private ReadService read;
//    private String latestAnswer = "Hello, I'm Dr. Car!. What car would you like to know about?"; //used for ReadService to read
//    boolean voice;
//    boolean img;
//    private int plansPos;
//    private int scriptPos;
//    private int imgPos;
//
//    private static String[] plans = {"Hello, I'm Dr. Car!. What car would you like to know about?",
//
//            "You have selected: 2017 Toyota Camry. What information would you like to know about this car?",
//            "From cars.com:\n\"Standard Features: \n178-horsepower, 2.5-liter four-cylinder engine\n" +
//                    "Six-speed automatic transmission\n" +
//                    "Front-wheel drive\n" +
//                    "Bluetooth streaming audio\n" +
//                    "iPod/USB-compatible stereo\n" +
//                    "60/40-split folding backseat\n" +
//                    "Required in every new car: front airbags, antilock brakes and an electronic stability system\"\n " +
//                    "If you would like to know more information about this car, type another question. If you would like to search other cars. Please click \"Return\"",
//    "From www.morortrend.com:\n\"Our 2017 Toyota Camry XSE tester is powered the base 2.5-liter I-4 making 178 hp and 170 lb-ft of torque. " +
//            "A six-speed automatic sends power to the front wheels. The Camry accelerated to 60 mph in 8.5 seconds and finished the quarter mile in 16.5 seconds at 86.5 mph\"",
//    "If you would like to know more information about this car, type another question. If you would like to search other cars. Please click \"Return\""};
//    public static String[] voicescript = {"You have selected to search using voice input!" + "\n" + " Press \"Enter\" and begin speaking, then enter again to stop",
//            "listening . . . . . ",
//            "Transcribed text: \"Please search for a Ford F150 2018 model\"","Is this correct? Please type \"yes\" or \"no\"",
//            "Great, you have selected the Ford F150 2018 model. What information would you like to find for you?",
//            "Searching for relevant information . . . . . . . . . . . . .",
//            "According to the \"2018 Ford F-150 Performance Review\" from cars.usnews.com, here is some information relating to \"performance\" for your selected car:\n\n" +
//                    "\"The F-150 comes standard with a 3.3-liter V6 engine that puts out 290 horsepower and 265 pound-feet of torque. " +
//                    "Though it’s only the base engine, that doesn’t mean it’s bad. It still has plenty of power for daily driving, and many buyers will be perfectly happy sticking with this V6.\n" +
//                    "For more power, you have several options. There’s a turbocharged, 2.7-liter V6 – one of two EcoBoost engines – " +
//                    "that produces 325 horsepower and 400 pound-feet of torque. There’s one V8 in the lineup too. " +
//                    "It’s a 5.0-liter engine that makes 395 horsepower and 400 pound-feet of torque." +
//                    " These engines are more capable, and the V8 has a nice engine note.\"" +"\n\n"+
//            "Any other information I can find for you?","Ok, please press \"Return\" or \"Enter\" to get back to the starting page"};
//    public static String[] imageScript = {"This appears to be a \"Volvo XC90 AWD SUV 2017 model. Is this correct?","Great! What information can I find for you?",
//    "Ok, le me find some information about price . . . .",
//    "according to \"https://cars.usnews.com\", the Volvo XC90 AWD SUV 2017 model IS #12 in Luxury Midsize SUVs.\n Average price paid ranged from \"$29,919 - $56,690 \".",
//    "Would you like any more information about this car?",
//    "Looking up safety information . . . . . ",
//    "Here some safety information I found from \"https://cars.usnews.com\":\n\n" +
//            "\"The Insurance Institute for Highway Safety named the XC90 a 2017 Top Safety Pick and gave it the highest rating of Good in all five safety categories. " +
//            "The National Highway Traffic Safety Administration gave the XC90 five out of five stars in frontal and side crash tests, along with four stars in the rollover test.\"",
//    "Would you like anymore information about this car?",
//            "Ok, please press \"Return\" to get back to the main page"};

    public static Document load(String filename){
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public void setService(String carName){
        Document company = load("Company.xml");
        Document service = load("Service.xml");
        Element rootCompany = company.getRootElement();
        Element rootService = service.getRootElement();
        Element targetCar = rootCompany.element(carName.replaceAll(" ", "-").toLowerCase());
        if(targetCar == null)
            Emergency.emergencyPlan("We cannot find the database of this car");
        String collectionName = targetCar.element("company").getText();
        filter = targetCar.element("filter").getText();

//        //Need Tests
//        List<Element> filters = targetCar.elements("filter");
//        filter = filter1 + filters.get(0).getText();
//        for(int i = 1; i < filters.size();i++){
//            filter += "|" + filter1 + filters.get(i).getText();
//        }

        Element collection = rootService.element(collectionName);
        String apikey = collection.element("api").getText();
        String url = collection.element("url").getText();
        IamAuthenticator authenticator = new IamAuthenticator(apikey);
        discovery = new Discovery("2019-04-30", authenticator);
        discovery.setServiceUrl(url);

        environment = collection.element("environment").getText();
        collectionID = collection.element("collection").getText();
    }

    public DiscoveryService(String carName){
//        voice=false;
//        img=false;
        //ui = gui;
        setService(carName);
        //read = new ReadService();
    }
//What are the minimum hardware requirements
    public ArrayList<String> ask(String question){
        ArrayList<String> answers = new ArrayList<>();
        QueryOptions.Builder queryBuilder = new QueryOptions.Builder(environment, collectionID);
        queryBuilder.naturalLanguageQuery(question);
        queryBuilder.filter(filter1 + filter);
//        queryBuilder.filter(filter);
        QueryResponse queryResponse = discovery.query(queryBuilder.passages(true).build()).execute().getResult();
        for(QueryPassages passage:queryResponse.getPassages()){
            answers.add(passage.getPassageText());
        }
//        for(QueryResult result: queryResponse.getResults()){
//            answers.add((String)result.get("text"));
//        }
        return answers;
    }

//    public void identifyPicture(String path){
//        ui.addText(AIname + imageScript[imgPos] + "\n");
//        ui.moveBarBottom();
//    }
//
//    public void next() {
//        String question = ui.getInput();
//        ui.cleanInput();
//        if (!question.isEmpty()) {
//            ui.addText("User: " + question + "\n");
//        }
//
//        if(!voice) {
//
//            if(img){
//                if (imgPos < imageScript.length - 1) {
//                    imgPos++;
//                    ui.addText(AIname + imageScript[imgPos] + "\n");
//
//                    latestAnswer = imageScript[imgPos];
//                } else {
//                    img=false;
//                    four();
//                }
//            }
//            else {
//                if (plansPos < plans.length - 1) {
//                    plansPos++;
//                    ui.addText(AIname + plans[plansPos] + "\n");
//
//                    latestAnswer = plans[plansPos];
//                } else {
//                    four();
//                }
//            }
//
//        }
//
//        else{
//
//            if (scriptPos < voicescript.length-1) {
//                scriptPos++;
//                ui.addText(AIname + voicescript[scriptPos] + "\n");
//                latestAnswer = voicescript[scriptPos];
//            } else {
//                four();
//                voice=false;
//            }
//
//        }
//    }
//
//
//    public void one()  {
//        voice=true;
//        scriptPos = 0;
//        ui.addText(AIname + ": " + voicescript[scriptPos] + "\n");
//    }
//
//    public void two() {
//        img=true;
//        imgPos=0;
//        choose = new FileChooser(this);
//    }
//
//    public void three() {
//        read.Read(latestAnswer);
//    }
//
//    public void four() {
//        plansPos=0;
//        voice=false;
//        img=false;
//        ui.cleanText();
//        ui.addText(AIname + plans[plansPos] + "\n");
//    }
}
