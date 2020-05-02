package com.multithreading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonToDatabase {
	public static Connection ConnectToDB() throws Exception {
      //Registering the Driver
      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
      //Getting the connection
      String mysqlUrl = "jdbc:mysql://localhost:3306/emp?serverTimezone=UTC";
      Connection con = DriverManager.getConnection(mysqlUrl, "root", "1234");
      System.out.println("Connection established......");
      return con;
    }
//-----------------new ----------------	
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
//-----------------new ----------------	
     public static void insertToDb() {
      //Creating a JSONParser object
      JSONParser jsonParser = new JSONParser();
      try {
         //Parsing the contents of the JSON file
    	  JSONObject jsonObject = readJsonFromUrl("http://dummy.restapiexample.com/api/v1/employees");
         //JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\bhaga\\eclipse-workspace\\filereading\\src\\main\\java\\com\\multithreading\\employees_data.json"));
         //Retrieving the array
         JSONArray jsonArray = (JSONArray) jsonObject.get("data");
         Connection con = ConnectToDB();
         //Insert a row into the Employees table
         PreparedStatement pstmt = con.prepareStatement("INSERT INTO employees values (?, ?, ?, ?, ? )");
         for(Object object : jsonArray) {
            JSONObject record = (JSONObject) object;
            int id = Integer.parseInt((String) record.get("id"));
            String employee_name = (String) record.get("employee_name");
            int employee_salary = Integer.parseInt((String) record.get("employee_salary"));
            int employee_age = Integer.parseInt((String) record.get("employee_age"));
            String profile_image = (String) record.get("profile_image");
//            String last_name = (String) record.get("Last_Name");
//            String date = (String) record.get("Date_Of_Birth");
//            long date_of_birth = Date.valueOf(date).getTime();
//            String place_of_birth = (String) record.get("Place_Of_Birth");
//            String country = (String) record.get("Country");
            pstmt.setInt(1, id);
            pstmt.setString(2, employee_name);
            pstmt.setInt(3, employee_salary);
            pstmt.setInt(4, employee_age);
            pstmt.setString(5, profile_image);
/*            pstmt.setString(3, employee_salary);
            pstmt.setDate(4, new Date(date_of_birth));
            pstmt.setString(5, place_of_birth);
            pstmt.setString(6, country);
*/            pstmt.executeUpdate();
         }  
         System.out.println("Records inserted.....");
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}