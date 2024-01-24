package domain.classadapter;

import java.util.ArrayList;
import java.util.List;

public class ClassModel {
    private int access;
    private String name;
    private String signature;
    private String superName;
    private List<String> interfaces;
    private ArrayList<FieldModel> fields;
    private ArrayList<MethodModel> methods;

    public ClassModel(int access, String name, String signature, String superName, List<String> interfaces, ArrayList<FieldModel> fields, ArrayList<MethodModel> methods){
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
    }

    public String getDetails(){
        String x = "";
        x += "**Class**\n";
        x += "Class Name: " + this.name + "\n";
        x += "Access type: " + this.access + "\n";;
        x += "Signature: " + this.signature + "\n";
        x += "Super Name: " + this.superName + "\n";
        x += "Interfaces: " + this.interfaces.toString() + "\n";

        return x;
    }

    public String getFieldDetails(){
        String x = "";
        for(FieldModel model: fields){
            x += model.getDetails();
        }
        return x;
    }

    public String getMethodDetails(){
        String x = "";
        for(MethodModel model: methods){
            x += model.getDetails();
        }
        return x;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return parseClassName(name);
    }

    public String getSignature() {
        return signature;
    }

    public String getSuperName() {
        return parseSuperType(superName);
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public ArrayList<FieldModel> getFields() {
        return fields;
    }

    public ArrayList<MethodModel> getMethods() {
        return methods;
    }

    private String parseClassName(String model) throws IllegalArgumentException {
        int indexOfClassName = model.lastIndexOf("/");
        if(indexOfClassName == -1) {
            throw new IllegalArgumentException();
        }
        return model.substring(indexOfClassName+1);
    }

    private String parseSuperType(String input) {
        String[] parts = input.split("/");
        return parts[parts.length - 1];
    }



}
