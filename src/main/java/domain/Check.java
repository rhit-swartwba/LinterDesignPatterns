package domain;
import java.util.ArrayList;
import java.util.List;

import domain.classadapter.ClassModel;

public abstract class Check {

    protected ArrayList<ClassModel> classes = new ArrayList<ClassModel>();

    public Check(ArrayList<ClassModel> classes) {
        this.classes = classes;
    }
    public abstract List<String> performCheck();

    public String parseFieldType(String input) {
        String[] parts = input.split("/");
        String lastPart = parts[parts.length - 1];
        lastPart = lastPart.replace(";", "");
        return lastPart;
    }



}
