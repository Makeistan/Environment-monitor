package enviroment.monitor;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter
{
    private static FileOutputStream fout;
    private static Mutex lock;
    public static void setup() throws FileNotFoundException
    {
        fout = new FileOutputStream("~log.txt");
        lock = new Mutex();
    }
    public static void write(String line) throws InterruptedException, IOException
    {
        lock.acquire();
        String[] lines;
        lines = line.split(",");
        
        for (String line1 : lines)
        {
            
            fout.write(line1.getBytes());
        }
        lock.release();
    }
    
}