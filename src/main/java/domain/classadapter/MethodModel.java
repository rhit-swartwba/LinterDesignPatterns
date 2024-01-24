package domain.classadapter;

import java.util.ArrayList;
import java.util.List;

public class MethodModel {
    private int access;
    private String name;
    private List<String> exceptions;
    private ArrayList<FieldModel> parameters;

    private int length;

    private ArrayList<String> methodCalls;

    private List<String> args;

    private String retType;

    public MethodModel(int access, String name, List<String> exceptions, ArrayList<FieldModel> parameters, ArrayList<String> methodCalls, int length, List<String> args, String retType) {
        this.access = access;
        this.name = name;
        this.exceptions = exceptions;
        this.parameters = parameters;
        this.methodCalls = methodCalls;
        this.length = length;
        this.args = args;
        this.retType = retType;
    }

    public String getDetails(){
        String x = "";
        x += "**Method**\n";
        x += "Name: " + this.name + "\n";
        x += "Access type: " + this.access + "\n";
        x += "Exceptions: " + this.exceptions.toString() + "\n";
        x += "Parameters: " + this.parameters.toString() + "\n";
        x += "Method Calls: " + this.methodCalls.toString() + "\n";
        x += "Length: " + this.length + "\n";
        x += "Arg: ";
        for(String arg: args) {
            x += arg + "\n";
        }
        x += "Return Type: " + retType + "\n";
        x += "\n";
        return x;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public ArrayList<FieldModel> getParameters() {
        return parameters;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<String> getMethodCalls() {
        return methodCalls;
    }

    public List<String> getArgs() { return this.args;}

    public String getRetType() {
        return this.retType;
    }
}
