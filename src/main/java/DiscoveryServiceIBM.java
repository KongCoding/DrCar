import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.QueryOptions;
import com.ibm.watson.discovery.v1.model.QueryPassages;
import com.ibm.watson.discovery.v1.model.QueryResponse;
import com.ibm.watson.discovery.v1.model.QueryResult;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.swing.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.List;


public class DiscoveryServiceIBM implements DiscoveryService {

    private static final String filter1 = "extracted_metadata.filename:";

    private String filter;
    private String environment;
    private String collectionID;
    private static Discovery discovery;

    @SuppressWarnings("all")
    public void setService(String carName, JFrame frame) throws NullPointerException{
        Document company = DiscoveryService.load("Company.xml");
        Document service = DiscoveryService.load("Service.xml");
        Element rootCompany = company.getRootElement();
        Element rootService = service.getRootElement();
        Element targetCar = rootCompany.element(carName.replaceAll(" ", "-").toLowerCase());
        //targetCar = null;
        if(targetCar == null)
            Emergency.emergencyPlanCLoseWindow(frame, "We cannot find the database of this car");
        String collectionName = targetCar.element("company").getText();
        filter = targetCar.element("filter").getText();

        List<Element> filters = targetCar.elements("filter");
        filter = filter1 + filters.get(0).getText();
        for(int i = 1; i < filters.size();i++){
            filter += "|" + filter1 + filters.get(i).getText();
        }

        Element collection = rootService.element(collectionName);
        String apikey = collection.element("api").getText();
        String url = collection.element("url").getText();
        IamAuthenticator authenticator = new IamAuthenticator(apikey);
        discovery = new Discovery("2019-04-30", authenticator);
        discovery.setServiceUrl(url);

        environment = collection.element("environment").getText();
        collectionID = collection.element("collection").getText();
    }

    public DiscoveryServiceIBM(String carName, JFrame frame){
        try{
            setService(carName, frame);
        }catch (NullPointerException e){
            System.out.println("Didn't find match car's information from xml file.");
        }
    }

    public ArrayList<String> ask(String question) throws ServiceResponseException {
        ArrayList<String> answers = new ArrayList<>();
        QueryOptions.Builder queryBuilder = new QueryOptions.Builder(environment, collectionID);
        queryBuilder.naturalLanguageQuery(question);
//        queryBuilder.filter(filter1 + filter);
        queryBuilder.filter(filter);
        QueryResponse queryResponse = discovery.query(queryBuilder.passages(true).build()).execute().getResult();
//        for(QueryPassages passage:queryResponse.getPassages()){
//            answers.add(passage.getPassageText());
//        }
        for(QueryResult result: queryResponse.getResults()){
            answers.add((String)result.get("text"));
        }
        return answers;
    }
}
