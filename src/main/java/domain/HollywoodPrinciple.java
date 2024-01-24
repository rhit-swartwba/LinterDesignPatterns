package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;

import java.util.ArrayList;
import java.util.List;

public class HollywoodPrinciple extends Check{
    private List<String> checkResults;
    public HollywoodPrinciple(ArrayList<ClassModel> classes) {
        super(classes);
        this.checkResults = new ArrayList<String>();
    }

    @Override
    public List<String> performCheck() {
        for (ClassModel aClass : classes) {
            if(aClass.getSuperName() != null){
                for(ClassModel superClass : classes){
                    if(superClass.getName().equals(aClass.getSuperName())){
                        checkSuperClassForDependency(aClass, superClass);
                    }
                }
            }
        }
        return checkResults;
    }

    private void checkSuperClassForDependency(ClassModel aClass, ClassModel superClass) {
        boolean violated = false;
        for(MethodModel methodVal : superClass.getMethods()) {
            //looking for parameter dependencies
            for(String argument : methodVal.getArgs()) {
                if(argument.contains(".")) {
                    String argType = argument.substring(argument.indexOf(".")+1);
                    if(argType.equals(aClass.getName())){
                        checkResults.add("A dependency is created from the superclass: " + superClass.getName()
                                + " to its subclass: " + aClass.getName() + " in the method " + methodVal.getName()
                                + ". This violates the Hollywood Principle.");
                        violated= true;
                    }
                }
            }

            for(String calledMethod : methodVal.getMethodCalls()) {
                //looking for initialization dependencies
                if(calledMethod.contains("<init>") && calledMethod.contains(aClass.getName())) {
                    checkResults.add("A dependency is created from the superclass: " + superClass.getName()
                            + " to its subclass: " + aClass.getName() + " since an instance of " + aClass.getName() +
                            " is initialized in the method " + methodVal.getName() + ". This violates the Hollywood Principle.");
                    violated = true;
                }
                //looking for method call dependencies
                else if(calledMethod.contains(aClass.getName())){
                    checkResults.add("A dependency is created from the superclass: " + superClass.getName()
                            + " to its subclass: " + aClass.getName() + " since it interacts with a method from " + aClass.getName()
                            + ", " + calledMethod + ". This violates the Hollywood Principle.");
                    violated = true;
                }
            }
        }
        if(!violated){
            checkResults.add("There is no violated of the Hollywood Principle betwen the superclass " + superClass.getName()
                    +  " and the subclass " + aClass.getName() + ".");
        }
    }

}
