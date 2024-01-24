package presentation;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface FileSelect {
    public abstract ArrayList<File> openFiles() throws IOException;
}
