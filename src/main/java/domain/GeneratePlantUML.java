package domain;
import datasource.DataLogger;
import datasource.PlantUMLFileLogger;
import domain.classadapter.ClassModel;
import domain.classadapter.FieldModel;
import domain.classadapter.MethodModel;
import org.objectweb.asm.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeneratePlantUML extends Check {
    private final DataLogger logger;

    private List<String> classNames;
    private DependencyHasACreator creator;


    public GeneratePlantUML(ArrayList<ClassModel> classes, DataLogger dataLogger) {
        super(classes);
        this.logger = dataLogger;
        classNames = new ArrayList<String>();
        this.creator = new DependencyHasACreator(classes);

    }

    @Override
    public List<String> performCheck() {
        logger.log("@startuml");
        logger.log("");
        generateClasses();
        logger.log("");
        generateInheritanceImplementArrows();
        generateDependenciesAndHasAArrows();
        logger.log("");
        logger.log("@enduml");
        List<String> retVal = new ArrayList<String>();
        retVal.add("Generated PlantUML of classes");
        return retVal;
    }

    private void generateDependenciesAndHasAArrows() {
        Map<String, List<String>> dependencyMap = creator.getDependencies();
        Map<String, List<String>> hasAMap = creator.getHasA();
        for(String from : dependencyMap.keySet()) {
            for(String to : dependencyMap.get(from)) {
                logger.log(from + " ..> " + to);
            }
        }
        for(String from : hasAMap.keySet()) {
            for(String to : hasAMap.get(from)) {
                logger.log(from + " --> " + to);
            }
        }
    }

    private void generateClasses() {
        for(ClassModel model: classes){
            String classType = "";
            String className = model.getName();
            classNames.add(className);
            if (model.getAccess() == 1537) {
                classType = "interface ";
            }
            else if ((model.getAccess() & Opcodes.ACC_ABSTRACT) != 0) {
                classType = "abstract class ";
            }
            else {
                classType = "class ";
            }
            logger.log(classType + className + " {");
            generateFields(model);
            generateMethods(model);
            logger.log("}");
            logger.log("");
        }
    }

    private void generateMethods(ClassModel model) {
        for(MethodModel method : model.getMethods()) {
            String methodParameters = getMethodParameters(method);
            String retType = parseRetType(method.getRetType());
            String methodName = method.getName();
            if(methodName.equals("<init>")) {
                methodName = model.getName();
            }
            String methodLine = " " + accessModifier(method.getAccess()) + methodName + "(" + methodParameters + "): " + retType;
            logger.log(methodLine);
        }
    }

    private String parseRetType(String retType) {
        if(retType.contains(".")) {
            retType = retType.substring(retType.lastIndexOf(".")+1);
        }
        return retType;
    }


    private String getMethodParameters(MethodModel method) {
        StringBuilder retVal = new StringBuilder();
        for(String argument : method.getArgs()) {
            argument = parseRetType(argument);
            retVal.append(argument).append(", ");
        }
        if(retVal.length() != 0) {
            retVal.delete(retVal.length() - 2, retVal.length());
        }
        return retVal.toString();
    }

    private void generateFields(ClassModel model) {
        for(FieldModel field : model.getFields()) {
            String fieldType = field.getDesc();
            if(fieldType.contains("/")) {
                fieldType = parseFieldType(field.getDesc());
            }
            String fieldLine = " " + accessModifier(field.getAccess()) + field.getName() + ": " + fieldType;
            logger.log(fieldLine);
        }
    }

    private void generateInheritanceImplementArrows() {
        for(ClassModel model : classes) {
            if(classNames.contains(model.getSuperName())) {
                logger.log(model.getName() + " --|> " + model.getSuperName());
            }
            for(String val : model.getInterfaces()) {
                String parsed = parseFieldType(val);
                if(classNames.contains(parsed)) {
                    logger.log(model.getName() + " ..|> " + parsed);
                }
            }
        }
    }

    private String accessModifier(int access) {
        if ((access & Opcodes.ACC_PUBLIC) != 0) {
            return "+";
        } else if ((access & Opcodes.ACC_PRIVATE) != 0) {
            return "-";
        } else if ((access & Opcodes.ACC_PROTECTED) != 0) {
            return "#";
        }
        return "~";
    }



}
