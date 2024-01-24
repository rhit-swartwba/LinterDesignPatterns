package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FileChooser implements FileSelect{

    @Override
    public ArrayList<File> openFiles() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Class File", "class"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File[] selected;
        ArrayList<File> allSelected = new ArrayList<File>();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selected = chooser.getSelectedFiles();
            for(File f : selected ){
                if(f.isDirectory()){
                    allSelected.addAll(Arrays.asList(Objects.requireNonNull(f.listFiles())));
                }
                if(f.isFile()){
                    allSelected.add(f);
                }
            }
        }
        return allSelected;
    }
}
