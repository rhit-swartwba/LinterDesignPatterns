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

public class DependencyHasACreator {

    private Map<String, List<String>> dependencyMap;
    private Map<String, List<String>> hasAMap;

    public DependencyHasACreator(ArrayList<ClassModel> classes) {
        this.dependencyMap = new HashMap<String, List<String>>();
        this.hasAMap = new HashMap<String, List<String>>();
        for(ClassModel model: classes){
            String modelName = model.getName();
            addDependencies(model, modelName);
            addHasA(model, modelName);
        }

    }
    public Map<String, List<String>> getHasA() {
        return hasAMap;
    }

    public Map<String, List<String>> getDependencies() {
        return dependencyMap;
    }

    private void addDependencies(ClassModel model, String modelName) {
        for(MethodModel methodVal : model.getMethods()) {
            if(methodVal.getName().contains("<init>")) {
                continue;
            }
            for(String argument : methodVal.getArgs()) {
                if(argument.contains(".")) {
                    String argType = argument.substring(argument.lastIndexOf(".")+1);
                    if(isNotCommonObjectType(argType)) {
                        addDependencyToMap(modelName, argType);
                    }
                }
            }
        }
    }

    private void addDependencyToMap(String modelName, String argType) {
        if(!dependencyMap.containsKey(modelName)) {
            ArrayList<String> mappedArgs = new ArrayList<String>();
            mappedArgs.add(argType);
            dependencyMap.put(modelName, mappedArgs);
        }
        else {
            if(!dependencyMap.get(modelName).contains(argType)) {
                dependencyMap.get(modelName).add(argType);
            }
        }
    }

    private String parseNewObjectInMethod(String input) {
        String regex = "([^/]+)<init>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNotCommonObjectType(String type) {
        return !type.equals("String") && !type.equals("List") && !type.equals("ArrayList") && !type.equals("Object");
    }

    private void addHasA(ClassModel model, String modelName) {
        for(FieldModel field : model.getFields()) {
            if(field.getDesc().contains("/")) {
                String fieldType = parseFieldType(field.getDesc());
                if(isNotCommonObjectType(fieldType)) {
                    if(!hasAMap.containsKey(modelName)) {
                        ArrayList<String> mappedFields = new ArrayList<String>();
                        mappedFields.add(fieldType);
                        hasAMap.put(modelName, mappedFields);
                    }
                    else {
                        if(!hasAMap.get(modelName).contains(fieldType)) {
                            hasAMap.get(modelName).add(fieldType);
                        }
                    }
                }
            }
        }
    }

    private String parseFieldType(String input) {
        String[] parts = input.split("/");
        String lastPart = parts[parts.length - 1];
        lastPart = lastPart.replace(";", "");
        return lastPart;
    }
}
