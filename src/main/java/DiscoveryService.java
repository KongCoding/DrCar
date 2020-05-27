import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.QueryOptions;
import com.ibm.watson.discovery.v1.model.QueryResponse;
import com.ibm.watson.discovery.v1.model.QueryResult;

import java.util.ArrayList;

public class DiscoveryService implements Page{

    private static final String Apikey = "wyb2SZ175eQrG4-eFlz9uMnX9Xr36dAd2vCOetLjEtVG";
    private static final String URL = "https://api.eu-gb.discovery.watson.cloud.ibm.com/instances/6b7aebbb-db7d-4618-80c5-ffeb06e9a064";
    private static final String Environment = "4aa11f0e-787b-41c5-ac74-f19435b97a2e";
    private static final String Collection = "69e12469-4a83-4a75-96db-7f835148d78b";

    private DiscoveryGUI ui;
    private static Discovery discovery;

    private static final String AIname = "Dr. Car: ";

    private int plansPos;
    private static String[] plans = {"Hello, welcome to QA system. I'm Dr. Car, which car would you want?",
            "Greate, what information do you want to know about it?",
            "Nice, it has many wonderful aspects. Which one do you want to know?",
            "<Answer to its performance>\n If you want to know more information about this car. " +
                    "Just type your question and ask me. If you want to find some other cars. Please click \"Return\"",
            "Hello, welcome to QA system. I'm Dr. Car, which car would you want?", "Identifying cars......\n\n\n\n" +
            "Success, this car is <Car name> If that is the car you are looking for, please tell me your question. " +
                    "If not, please click \"Return\" to ask me again.", ""};

    public static void setService(){
        IamAuthenticator authenticator = new IamAuthenticator(Apikey);
        discovery = new Discovery("2019-04-30", authenticator);
        discovery.setServiceUrl(URL);
    }

    public DiscoveryService(DiscoveryGUI gui){
        ui = gui;
        setService();
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

    @Override
    public void start() {
        //ui.addText("Eyes: Hi, what can I do for you?\n");
        plansPos = 0;
        ui.addText(AIname + ": " + plans[plansPos] + "\n");
    }

    @Override
    public void next() {
        String question = ui.getInput();
        ui.cleanInput();
        ui.addText("User: " + question + "\n");
        plansPos++;
        ui.addText(AIname + plans[plansPos] + "\n");
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
    public void four() {
        plansPos++;
        ui.cleanText();
        ui.addText(AIname + plans[plansPos] + "\n");
    }
}
