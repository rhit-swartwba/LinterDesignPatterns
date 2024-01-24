package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.MethodModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MethodLength extends Check {
    private List<String> checkResults;
    public MethodLength(ArrayList<ClassModel> classes) {
        super(classes);
        this.checkResults = new ArrayList<String>();
    }

    @Override
    public List<String> performCheck() {
        for (ClassModel aClass : classes) {
            for(MethodModel aClassMethods : aClass.getMethods()){
                checkResults.add("The method: " + aClassMethods.getName() + " is " +
                        aClassMethods.getLength() + " lines of code.");
                if(aClassMethods.getLength() > 30){
                    checkResults.add("The method: " + aClassMethods.getName() + " may be too long at " +
                            aClassMethods.getLength() + " lines of code.");
                }
            }
        }
        return checkResults;
    }
}