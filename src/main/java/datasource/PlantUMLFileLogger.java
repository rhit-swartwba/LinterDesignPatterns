package datasource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class PlantUMLFileLogger implements DataLogger {

    PrintStream stream;
    public PlantUMLFileLogger(String filepath) throws FileNotFoundException {
        this.stream = new PrintStream(new FileOutputStream(filepath));
        this.stream = new PrintStream(new FileOutputStream(filepath, true));
    }
    public void log(String line){
        stream.println(line);
    }

}