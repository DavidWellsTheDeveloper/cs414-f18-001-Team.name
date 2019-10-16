package GameServer;

import GameLogic.exception.PlayerNameException;
import GameLogic.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.sql.*;
import java.util.*;

public class Database{

	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static String PROXY_ADDRESS = "jdbc:mariadb://proxy19.rt3.io:39136/cs414";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "the_password_123";
    
    private static String devAPIKey = "MTA2M0FGNDUtM0M1QS00ODMyLUFDNDgtOEVBQ0E1Q0JBRUU1";
    private static String deviceAddress = "80:00:00:00:01:01:38:E9";
    
	static final String RESET_TEXT_COLOR = "\u001B[0m";
    static final String RED_TEXT = "\u001B[38;5;196m";
    static final String BLUE_TEXT = "\u001B[38;5;14m";
    static final String GRAY_TEXT = "\u001B[38;5;7m";
    static final String GREEN_TEXT = "\u001B[38;5;82m";
    static final String YELLOW_TEXT = "\u001B[38;5;11m";

    static final String INFO_TAG = BLUE_TEXT + "==INFO==:: " + RESET_TEXT_COLOR;
    static final String DEBUG_TAG = GRAY_TEXT + "==DEBUG==:: " + RESET_TEXT_COLOR;
    static final String ERROR_TAG = RED_TEXT + "==ERROR==:: " + RESET_TEXT_COLOR;
    static final String WARNING_TAG = YELLOW_TEXT + "==WARNING==:: " + RESET_TEXT_COLOR;
    static final String SUCCESS_TAG = GREEN_TEXT + "==SUCCESS==:: " + RESET_TEXT_COLOR;
    
    protected Database(){
    
    }
    
    public static void establishDatabaseProxyAddress() throws IOException, InterruptedException, ClassNotFoundException, SQLException {

        try {
            System.out.println(DEBUG_TAG + "Attempting to connect to remote database with existing proxy server address.");
            Class.forName(JDBC_DRIVER); // register the JDBC driver.
            DriverManager.getConnection(
                    PROXY_ADDRESS, DB_USERNAME, DB_PASSWORD); // Open a connection to the database.
            System.out.println(SUCCESS_TAG + "Using the existing proxy server address was successful.");
            return;
        }catch(SQLNonTransientConnectionException sql){
            System.out.println(WARNING_TAG + "Using the existing proxy server address failed;" +
                    " executing curl commands to retrieve new proxy server address.");
        }
        Process process = Runtime.getRuntime().exec("GameServer/getSessionToken.sh");
        process.waitFor();
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String part = "";
        String response = "";
        while((part = bufferedReader.readLine()) != null){
            response += part;
        }
        // System.out.println("==DEBUG==:: getSessionToken.sh response: " + response);
        String sessionToken = response.split(":")[2].replace("\"", "").split(",")[0];
        System.out.println(DEBUG_TAG + "Parsed remote proxy session token: " + sessionToken);

        String getProxyAddress = String.format("curl -X POST -H \"token:%s\" -H \"developerkey\":\"%s\" -d \'{\"wait\":\"true\", " +
                "\"deviceaddress\":\"\'%s\'\"}' https://api.remot3.it/apv/v27/device/connect", sessionToken, devAPIKey, deviceAddress);
        // System.out.println(getProxyAddress);

        FileWriter fileWriter = new FileWriter("GameServer/getProxyAddress.sh");
        fileWriter.write(getProxyAddress);
        fileWriter.close();
        process = Runtime.getRuntime().exec("chmod 775 GameServer/getProxyAddress.sh");
        process.waitFor();
        process = Runtime.getRuntime().exec("GameServer/getProxyAddress.sh");
        process.waitFor();

        inputStream = process.getInputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        response = "";
        while((part = bufferedReader.readLine()) != null){
            response += part;
        }
        process.destroy();
        // System.out.println("==DEBUG==:: getProxyAddress.sh response: " + response);
        String[] components = response.split(",");
        for(String component: components){
            if(component.contains("\"proxy\"")){
                PROXY_ADDRESS = component.replace("\\", "").replace("\"", "")
                        .split("proxy:")[1].replace("http://", "");
                break;
            }
        }
        PROXY_ADDRESS = "jdbc:mariadb://" + PROXY_ADDRESS + "/cs414";
        System.out.println(DEBUG_TAG + "Parsed remote proxy database address: " + PROXY_ADDRESS);
    }
    
    public static ResultSet executeDatabaseQuery(String query){
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        try{
            establishDatabaseProxyAddress();
            Class.forName(JDBC_DRIVER); // register the JDBC driver.
            connection = DriverManager.getConnection(
                    PROXY_ADDRESS, DB_USERNAME, DB_PASSWORD); // Open a connection to the database.
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch(SQLNonTransientConnectionException e){
            System.out.println(ERROR_TAG + "Encountered an error while connecting to the database. (HINT: the proxy server is likely different than what is set.)");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch(InterruptedException | IOException e) {
            System.out.println(ERROR_TAG + "Encountered an error while attempting to curl proxy server credentials for the database.");
            e.printStackTrace();
        }finally {
            return resultSet;
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, SQLException, SQLNonTransientConnectionException{
		String query = args[0];
		System.out.println(String.format("Executing the following SQL query: \"%s\".", query));
		ResultSet resultSet = executeDatabaseQuery(query);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		for(int i = 1; i<=numberOfColumns; i++){
			System.out.printf("| %-25s", rsmd.getColumnName(i));
		}
		for(int i = 1; i<=numberOfColumns; i++){
			for(int j = 0; j<25; j++){
				System.out.print("=");
			}
		}System.out.println();
		while(resultSet.next()){
			for (int i = 1; i <= numberOfColumns; i++) {
					String columnValue = resultSet.getString(i);
					System.out.printf("| %-25s", columnValue);
				}
				System.out.println();
		}
    }
}

















