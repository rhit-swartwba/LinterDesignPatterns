package domain;

import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecoratorPattern extends Check{

    //strings with messages to return after detecting various parts
    private List<String> decoratingPattern;
    //list of decorating patterns
    private Map<String, ArrayList<String>> decoratorPatternsDetected;

    private boolean isGoodDecorator;
    public DecoratorPattern(ArrayList<ClassModel> classes) {
        super(classes);
        decoratorPatternsDetected = new HashMap<>();
        decoratingPattern = new ArrayList<>();
        isGoodDecorator = true;
    }

    @Override
    public List<String> performCheck() {
        boolean foundPattern = false;
        for(ClassModel model: classes){
            //first check to see if there is an abstract class in between is done while finding decorator pattern
            if(followsDecoratorPattern(model.getSuperName(), model)) {
                //perform the second check for each decorator model class
                checkMethodCallsSameMethod(model);
                foundPattern = true;
            }
        }
        //only check for cores if found at least one decorating class
        if(foundPattern) {
            findAndCheckCores();
        }
        if(!foundPattern) {
            decoratingPattern.add("No decorator pattern detected.");
        }
        else {
            if(isGoodDecorator) {
                decoratingPattern.add("Good execution of decorator pattern!");
            }
            else {
                decoratingPattern.add("Poor execution of decorator pattern. See violating constraints above.");
            }
        }
        return decoratingPattern;
    }

    private boolean followsDecoratorPattern(String superName, ClassModel model) {
        List<String> fields = new ArrayList<String>();
        int count = 0;
        StringBuilder addToList = new StringBuilder(model.getName() + " decorates ");
        while(!superName.equals("Object")) {
            count++;
            addToFieldListChecker(fields, model);
            //compare all fields of subclasses to that of the current superclass to see if the type matches
            for(String fieldFromBelow : fields) {
                if(fieldFromBelow.equals(superName)) {
                    //found a field type that matches the supertype
                    addToList.append(superName);
                    decoratingPattern.add(addToList.toString());
                    checkConstraintAbstractDecorator(count, model);
                    addDecoratorPatternDetected(model, superName);
                    return true;
                }
            }
            //continue to loop upwards to the next superclass if not found
            boolean hasChanged = false;
            for(ClassModel classModel : classes) {
                if(superName.equals(classModel.getName())) {
                    //adding the abstract decorating class looping through
                    addToList.append("(through abstract decorating class: ").append(superName).append(") ");
                    model = classModel;
                    superName = model.getSuperName();
                    hasChanged = true;
                    break;
                }
            }
            if(!hasChanged) {
                break;
            }
        }
        return false;
    }

    private void checkConstraintAbstractDecorator(int count, ClassModel model) {
        if(count == 1 && (model.getAccess() & Opcodes.ACC_ABSTRACT) == 0) {
            isGoodDecorator = false;
            decoratingPattern.add("VIOLATE CHECK 1: No abstract decorator class for " + model.getName() + ", SUGGESTION: PLEASE ADD A DECORATING ABSTRACT CLASS FOR MORE FLEXIBILITY");
        }
    }

    private void addToFieldListChecker(List<String> fields, ClassModel model) {
        for(FieldModel field : model.getFields()) {
            String fieldType = field.getDesc();
            if(fieldType.contains("/")) {
                fieldType = parseFieldType(fieldType);
                fields.add(fieldType);
            }
        }
    }

    private void addDecoratorPatternDetected(ClassModel model, String superName) {
        if(!decoratorPatternsDetected.containsKey(superName)) {
            ArrayList<String> newArr = new ArrayList<String>();
            newArr.add(model.getName());
            decoratorPatternsDetected.put(superName, newArr);
        } else {
            if(!decoratorPatternsDetected.get(superName).contains(model.getName())) {
                decoratorPatternsDetected.get(superName).add(model.getName());
            }
        }
    }

    private void checkMethodCallsSameMethod(ClassModel model) {
        boolean callsSame = false;
        for(MethodModel methodVal : model.getMethods()) {
            String methodName = methodVal.getName();
            if(methodName.equals("<init>")) {
                continue;
            }
            List<String> params = methodVal.getMethodCalls();
            for(String methodCall : params) {
                if(methodCall.length() > methodName.length()){
                    String ending = methodCall.substring(methodCall.length() - methodName.length());
                    if(ending.equals(methodName)) {
                        callsSame = true;
                    }
                }
            }
        }
        if(!callsSame && (model.getAccess() & Opcodes.ACC_ABSTRACT) == 0) {
            isGoodDecorator = false;
            decoratingPattern.add("VIOLATE CHECK 2: Method does not call the same method on its parent field, SUGGESTION: Double check methods to ensure they call the method on the field");
        }
    }


    private void findAndCheckCores() {
        boolean foundCore = false;
        for(ClassModel model : classes) {
            for(String decorMain : decoratorPatternsDetected.keySet()) {
                boolean inside = false;
                for(String decorator : decoratorPatternsDetected.get(decorMain)) {
                    if(model.getName().equals(decorator)) {
                        inside = true;
                    }
                }
                if(model.getSuperName().equals(decorMain) && !inside) {
                    String coreFound = "CORE detected: " + model.getName() + " for " + decorMain;
                    decoratingPattern.add(coreFound);
                    foundCore = true;
                }
            }
        }
        if(!foundCore) {
            isGoodDecorator = false;
            decoratingPattern.add("VIOLATE CHECK 3: No Core Found, SUGGESTION: ADD A CORE CLASS");
        }
    }

}
