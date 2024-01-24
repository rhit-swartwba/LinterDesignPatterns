package presentation;

import datasource.PlantUMLFileLogger;
import domain.*;
import domain.classadapter.ClassModelGenerator;
import domain.classadapter.ClassModel;
import domain.classadapter.ASMLibrary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) throws IOException, InterruptedException {
        FileSelect fileSelector = new FileChooser();
        ClassModelGenerator ASMModels = new ASMLibrary();
        ArrayList<ClassModel> models = ASMModels.generateClassModels(fileSelector.openFiles());
        String[] testNames = {"Hashcode Equals", "Tight Coupling", "Decorator Pattern", "Generate PlantUML", "Method Length", "Hollywood Principle", "Strategy Pattern", "Variable Name Format", "Information Hiding", "Template Pattern"};
        CheckboxFrame c = new CheckboxFrame(testNames);
        boolean[] selection = c.getSelectedValues();
        c.dispose();
        ArrayList<String> tests = new ArrayList<String>();

        boolean printClassDetails = selection[0];
        boolean printFieldDetails = selection[1];
        boolean printMethodDetails = selection[2];

        for(int i=0; i < testNames.length; i++){
            if (selection[i+3]) tests.add(testNames[i]);
        }

        System.out.println("we will be running the following tests: " + tests.toString());
        System.out.println("Printing out details: " + printClassDetails + ", field: " + printFieldDetails + ", field: " + printMethodDetails);

        if (printClassDetails){
            for(ClassModel model: models){
                System.out.println(model.getDetails());
                if (printFieldDetails) System.out.println(model.getFieldDetails());
                if (printMethodDetails) System.out.println(model.getMethodDetails());
            }
        }

        runChecks(createChecks(tests, models));

    }

    private static void runChecks(ArrayList<Check> checks){
        for(Check c: checks){
            List<String> results = c.performCheck();
            for(String result : results){
                System.out.println(result);
            }
            System.out.println();
        }
    }

    private static ArrayList<Check> createChecks(ArrayList<String> checkName, ArrayList<ClassModel> classes) throws FileNotFoundException {
        ArrayList<Check> checks = new ArrayList<Check>();
        for(String name: checkName){
            switch(name){
            case "Hashcode Equals":
                checks.add(new HashcodeAndEquals(classes));
                break;
            case "Tight Coupling":
                Scanner input = new Scanner(System.in);
                System.out.println("What percent coupling is deemed as too high (1 indicates only max coupling is too high, 0 means any relationships is too tight coupling)?: ");
                double userInput = input.nextDouble();
                if(userInput <= 0 || userInput > 1) {
                    System.out.println("Incorrect tight coupling percent given... setting to default value of 0.5.");
                    userInput = 0.5;
                }
                checks.add(new TightCoupling(classes, userInput));
                break;
            case "Decorator Pattern":
                checks.add(new DecoratorPattern(classes));
                break;
            case "Method Length":
                checks.add(new MethodLength(classes));
                break;
            case "Hollywood Principle":
                checks.add(new HollywoodPrinciple(classes));
                break;
            case "Strategy Pattern":
                checks.add(new StrategyPattern(classes));
                break;
            case "Variable Name Format":
                checks.add(new VariableNameFormat(classes));
                break;
            case "Information Hiding":
                checks.add(new InformationHiding(classes));
                break;
            case "Template Pattern":
                checks.add(new TemplatePattern(classes));
                break;

                case "Generate PlantUML":
                    checks.add(new GeneratePlantUML(classes, new PlantUMLFileLogger("OutputClassUMLFile.puml")));
                    break;
            }
        }
        return checks;
    }
}
