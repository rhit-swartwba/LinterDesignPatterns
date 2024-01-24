package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.MethodModel;

import java.util.ArrayList;
import java.util.List;

public class HashcodeAndEquals extends Check{
    public HashcodeAndEquals(ArrayList<ClassModel> classes) {
        super(classes);
    }

    @Override
    public List<String> performCheck() {
        boolean violates = false;
        List<String> retVal = new ArrayList<String>();
        for(ClassModel model: classes){
            boolean[] arr = new boolean[2];
            if(violatesHashcodeAndEquals(model, arr)) {
                String missingMethod = "";
                if(arr[0]) {
                    missingMethod = "equals";
                } else {
                    missingMethod = "hashCode";
                }
                violates = true;
                retVal.add(model.getName() + " class violates HashCodeAndEquals: Missing the " + missingMethod + " method");
            }
        }
        if(!violates) {
            retVal.add("There are no classes that violate HashCodeAndEquals Check. Good job!");
        }
        return retVal;
    }

    private boolean violatesHashcodeAndEquals(ClassModel model, boolean[] arr) {
        String hashCodeMethod = "hashCode"; //0
        String equalsMethod = "equals"; //1
        for(MethodModel methodVal : model.getMethods()) {
            String methodName = methodVal.getName();
            if(methodName.equals(hashCodeMethod)) {
                arr[0] = true;
            }
            else if(methodName.equals(equalsMethod)) {
                arr[1] = true;
            }
        }
        return (!arr[0] && arr[1]) || (arr[0] && !arr[1]);
    }

}
