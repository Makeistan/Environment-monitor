package enviroment.monitor;

import java.net.Socket;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reciever extends Thread
{
    private static final int ID = 1;
    private static final int TEST = 2;
    private static final int DUST = 3;
    private final BufferedReader socketConnection;
   // private final Connection databaseConnection;
    public Reciever(BufferedReader br)
    {
        socketConnection = br; 
    }
    /*
    public Reciever(Socket s, Connection databaseConnection) throws IOException
    {
        this.databaseConnection = databaseConnection;
        InputStream ispr = s.getInputStream();
        socketConnection = new BufferedReader(new InputStreamReader(is));
    }
    */
    @Override
    @SuppressWarnings("empty-statement")
    public void run()
    {
        
        while(true)
        {
            try
            {
                String line;
                while(null == (line =socketConnection.readLine()));
                for(int i = 0; i < EnviromentMonitor.apps.size(); i++)
                    EnviromentMonitor.apps.get(i).println(line);
            }
            /*
            try {
            while(true)
            this.readSocketAndWriteInDatabase();
            } catch (IOException | SQLException ex) {
            Logger.getLogger(Reciever.class.getName()).log(Level.SEVERE, null, ex);
            }
             */ catch (IOException ex)
            {
                Logger.getLogger(Reciever.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        

    }
    /*
    @SuppressWarnings("empty-statement")
    private void readSocketAndWriteInDatabase() throws IOException, SQLException
    {
        String line;
        while((line = socketConnection.readLine()) == null);
        this.executeQuery(line);
        System.out.println(line);
    }
    private void executeQuery(String line) throws SQLException
    {
        String[] data = line.split("$");
        String query;
        query = " insert into device_data(device_id, time_stamp, test, dust_25)"
                + " values (?, ?, ?, ?)";
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(data[0]));
        statement.setTimestamp(2, date);
        statement.setInt(3, Integer.parseInt(data[2]));
        statement.setInt(4, Integer.parseInt(data[3]));
        statement.execute();
    }
    */
}