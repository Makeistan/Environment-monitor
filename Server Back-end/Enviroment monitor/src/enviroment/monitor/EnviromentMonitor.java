package enviroment.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnviromentMonitor
{
    private static Connection databaseConnection;
    private static ServerSocket server;
    private static Reciever recieverThread;
    
    private static final int PORT_OF_THIS = 12345;
    private static final int PORT_OF_DATABASE = 3306;
    private static final String IP_OF_DATABASE = "localhost";
    private static final String DATABASE_NAME = "env_mon";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "hellohello";
    public static ArrayList<PrintWriter> apps;
    @SuppressWarnings("empty-statement")
    public static void main(String[] args)
    {
        apps = new ArrayList();
        /*
        //------------------Connection with Database---------------//
        try {
            connectToDatabase();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EnviromentMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        /////////////////////////////////////////////////////////////
        
        
        */
        //------------------ Setting up Server ------------------------//
        startServer();
        /////////////////////////////////////////////////////////////////
        
        
        //------------------ Set Server on Recieving Threads ----------//
        try{
           //recieveClientAndAssignThread();
           Socket s = server.accept();
           BufferedReader br =
                   new BufferedReader(
                   new InputStreamReader(s.getInputStream()));
           String line;
           while( null == (line = br.readLine()));
           System.out.println(line);
           if ("nodemcu" == line)
           {
               Reciever r;
               r = new Reciever(br);
               r.start();
           }
           else if ("app" == line){
               PrintWriter pr;
               pr = new PrintWriter(s.getOutputStream(), true);
               apps.add(pr);
           }
        } catch (IOException ex){
            Logger.getLogger(EnviromentMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        /////////////////////////////////////////////////////////////////
    }
    
    public static void connectToDatabase() throws SQLException, ClassNotFoundException
    {
        System.out.println("Connecting to database . . . .");
        Class.forName("com.mysql.jdbc.Driver");
        String databaseLocation = "jdbc:mysql://" + IP_OF_DATABASE;
        databaseLocation = databaseLocation + ":";
        databaseLocation = databaseLocation + PORT_OF_DATABASE;
        databaseLocation = databaseLocation + "/" + DATABASE_NAME;
        databaseConnection = DriverManager.
            getConnection(databaseLocation, DATABASE_USERNAME, DATABASE_PASSWORD);
        System.out.println("Connected to Database . . .");
        
    }
    public static void startServer()
    {
        System.out.println("Server is Starting . . .");
        try {
            server = new ServerSocket(PORT_OF_THIS);
            System.out.println("Server is up . . .");
        } catch (IOException ex) {
            Logger.getLogger(EnviromentMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*
    private static void recieveClientAndAssignThread() throws IOException
    {
        Socket s;
        while(true)
        {
            System.out.println("Waiting for client . . .");
            s = server.accept();
            System.out.println("Client connected.");
            
            System.out.println("Creating Thread for client . . .");
            recieverThread = new Reciever(s, databaseConnection);
            
            System.out.println("Thread created. Starting thread . . .");
            recieverThread.start();
            
            System.out.println("Thread started.");
        }
    }
*/
}
