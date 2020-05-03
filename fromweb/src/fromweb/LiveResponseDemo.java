package fromweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.logging.LogFactory;

public class LiveResponseDemo {

	 // essential URL structure is built using constants
	 public static final String ACCESS_KEY = "d1145c1b532a09318f9e66dfacaec924";
	 public static final String BASE_URL = "http://api.currencylayer.com/";
	 public static final String ENDPOINT = "live";

	 // this object is used for executing requests to the (REST) API
	 static CloseableHttpClient httpClient = HttpClients.createDefault();

	 // sendLiveRequest() function is created to request and retrieve the data
	 public static void sendLiveRequest(){

	     // The following line initializes the HttpGet Object with the URL in order to send a request
	     HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
	     for (int i = 0; i < 5; i++) {
	    	 try {
	    		 CloseableHttpResponse response =  httpClient.execute(get);
	    		 HttpEntity entity = response.getEntity();

	    		 //the following line converts the JSON Response to an equivalent Java Object
	    		 JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

	    		 System.out.println("Live Currency Exchange Rates");
	    		    // Parsed JSON Objects are accessed according to the JSON resonse's hierarchy, output strings are built
	    	         Date timeStampDate = new Date((long)(exchangeRates.getLong("timestamp")*1000)); 
	    	         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	    	         String formattedDate = dateFormat.format(timeStampDate);
	    	         System.out.println("1 " + exchangeRates.getString("source") + " in INR : " + exchangeRates.getJSONObject("quotes").getDouble("USDINR") + " (Date: " + formattedDate + ")");   
	    	         System.out.println("1 " + exchangeRates.getString("source") + " in GBP : " + exchangeRates.getJSONObject("quotes").getDouble("USDGBP") + " (Date: " + formattedDate + ")");
	    	         System.out.println("1 " + exchangeRates.getString("source") + " in CAD : " + exchangeRates.getJSONObject("quotes").getDouble("USDCAD") + " (Date: " + formattedDate + ")");
	    	         System.out.println("\n");
	                 response.close();
	     } catch (ClientProtocolException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     } catch (ParseException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     } catch (JSONException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	     }
	 }

	     // sendLiveRequest() function is executed
	 public static void main(String[] args) throws IOException{
	     sendLiveRequest();
	     httpClient.close();
	     new BufferedReader(new InputStreamReader(System.in)).readLine();
	 }
}
