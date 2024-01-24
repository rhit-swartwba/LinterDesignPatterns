package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VariableNameFormat extends Check{

    public VariableNameFormat(ArrayList<ClassModel> classes) {
        super(classes);
    }

    @Override
    public List<String> performCheck() {
        return null;
    }

    private List<String> checkFields(){
        for(ClassModel c: classes){
            for(FieldModel f: c.getFields()){
                String name = f.getName();
                boolean isCamel = checkCamelCase(name);
            }
        }
        return new ArrayList<String>();
    }

    private List<String> checkMethods(){
        for(ClassModel c: classes){
            for(MethodModel m: c.getMethods()){
                String name = m.getName();
                boolean isCamel = checkCamelCase(name);
            }
        }
        return new ArrayList<String>();
    }

    private boolean checkCamelCase(String s){
        Pattern pattern = Pattern.compile("^[a-z]+[A-Z][a-z]+$");
        return pattern.matcher(s).matches();
    }

    private boolean checkPascalCase(String s){
        Pattern pattern = Pattern.compile("^[a-z]+[A-Z][a-z]+$");
        return pattern.matcher(s).matches();
    }
}