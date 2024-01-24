package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TightCoupling extends Check{

    //right now only checking for circular has-a dependencies
    private List<String> retVal;
    private double percentCheck;

    private DependencyHasACreator creator;

    public TightCoupling(ArrayList<ClassModel> classes, double percentCheck) {
        super(classes);
        retVal = new ArrayList<String>();
        this.percentCheck = percentCheck;
        this.creator = new DependencyHasACreator(classes);
    }

    @Override
    public List<String> performCheck() {
        int numClasses = 0;
        for(ClassModel model: classes){
            numClasses++;
        }
        checkTightOverallCoupling(numClasses);
        checkCircularHasA();
        return retVal;
    }

    private void checkTightOverallCoupling(int numClasses) {
        Map<String, List<String>> hasAMap = creator.getHasA();
        Map<String, List<String>> dependencyMap = creator.getDependencies();
        int maxCount = numClasses * (numClasses - 1) * 2;
        int countHasA = countRelations(hasAMap);
        int countDependencies = countRelations(dependencyMap);
        int compareVal = (int)(maxCount*percentCheck);
        if((countHasA+countDependencies) > compareVal) {
            retVal.add("Overall tight coupling has been detected (total relationships > compareVal). Considering reducing hasA & dependency relationships.");
        }
        else {
            retVal.add("There is not overall tight coupling. Good job!");
        }
        retVal.add("Max Relationships: " + maxCount);
        retVal.add("Total Relationships: " + (countHasA + countDependencies));
        retVal.add("HasA Relationships: " + countHasA);
        retVal.add("Total Dependencies: " + countDependencies);
        retVal.add("Given compare value: " + percentCheck);
        retVal.add("Relative Max Value: " + compareVal);
    }

    private int countRelations(Map<String, List<String>> relationshipMap) {
        int count = 0;
        for(String key : relationshipMap.keySet()) {
            count += relationshipMap.get(key).size();
        }
        return count;
    }

    private void checkCircularHasA() {
        Map<String, List<String>> hasAMap = creator.getHasA();
        boolean circular = false;
        for(String className : hasAMap.keySet()) {
            for(String dependents : hasAMap.get(className)) {
                if(hasAMap.get(dependents) != null && hasAMap.get(dependents).contains(className)) {
                    circular = true;
                    if(!retVal.contains("Circular dependency between " + dependents + ", " + className + ", SUGGESTION: considering removing this circular dependency")) {
                        retVal.add("Circular dependency between " + className + ", " + dependents + ", SUGGESTION: considering removing this circular dependency");
                    }
                }
            }
        }
        if(!circular) {
            retVal.add("There are no circular dependencies between classes. Good job!");
        }
    }
}
