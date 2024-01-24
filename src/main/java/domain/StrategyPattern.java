package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;

import java.util.ArrayList;
import java.util.List;

public class StrategyPattern extends Check{
    List<String> interfaces = new ArrayList<String>();
    List<String> checkResults = new ArrayList<String>();
    public StrategyPattern(ArrayList<ClassModel> classes) {
        super(classes);
    }

    @Override
    public List<String> performCheck() {
        for(ClassModel aClass : classes){
            if(aClass.getAccess() == 1537){
                interfaces.add(aClass.getName());
            }
        }

        for(ClassModel aClass : classes){
            for(FieldModel field  : aClass.getFields()){
                if(interfaces.contains(parseFieldType(field.getDesc()))){
                    if(lookForStrategySetMethod(aClass, parseFieldType(field.getDesc()))){
                        checkResults.add(aClass.getName() + " has a strategy pattern with the interface " + parseFieldType(field.getDesc()));
                    }
                }
            }
        }
        return checkResults;
    }

    public boolean lookForStrategySetMethod(ClassModel aClass, String interfaceType){
        for(MethodModel method : aClass.getMethods()){
            for(String arg : method.getArgs()){
                System.out.println(arg);
                String[] parts = arg.split("\\.");
                String argType = parts[parts.length - 1];
                if(argType.equals(interfaceType)) {
                    return true;
                }
            }
        }
        return false;
    }
}