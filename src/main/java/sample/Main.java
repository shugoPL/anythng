package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);

        //Declare JDBC objects
        Connection con = null;
        Statement stmt = null;
        CallableStatement cstmt = null;
        ResultSet rst = null;

        String serverName = null;
        String portNumber = null;
        String databaseName = null;
        String username = null;
        String password = null;




        //Trying to make connection to MS-SQL server
        try {
            BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter server name: ");
            serverName = br.readLine();
            System.out.println("Enter port number: ");
            portNumber = br.readLine();
            System.out.println("Enter database name: ");
            databaseName = br.readLine();
            System.out.println("Enter user name: ");
            username = br.readLine();
            System.out.println("Enter password: ");
            password = br.readLine();

            //Create a variable for connection
            String connectionURL = "jdbc:sqlserver://" + serverName + ":" + portNumber +";"
                    + "databaseName=" + databaseName + ";username=" + username + ";password=" + password +";";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionURL);


//            SQLServerDataSource ds = new SQLServerDataSource();
//            ds.setServerName(serverName);
//            ds.setPortNumber(Integer.parseInt(portNumber));
//            ds.setDatabaseName(databaseName);
//            ds.setUser(username);
//            ds.setPassword(password);
//
//            con = ds.getConnection();

            System.out.println();
            System.out.println("Connection established successfully");
            String sqlQuery = "SELECT * FROM users";
            stmt = con.createStatement();
            rst = stmt.executeQuery(sqlQuery);

            while(rst.next() ){
                System.out.println( "user name: " + rst.getString(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( rst != null ) rst.close();
                if( cstmt != null ) cstmt.close();
                if( con != null ) con.close();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }

    }


}
