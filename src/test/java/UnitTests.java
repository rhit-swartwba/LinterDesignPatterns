import domain.Check;
import domain.DecoratorPattern;
import domain.HashcodeAndEquals;
import domain.TightCoupling;
import domain.classadapter.ASMLibrary;
import domain.classadapter.ClassModel;
import domain.classadapter.ClassModelGenerator;
import domain.classadapter.MethodModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests{

    private String path = System.getProperty("user.dir") + File.separator + "target" + File.separator + "test-classes" + File.separator + "domain" + File.separator;

    /***********************************************
     *  HashCode and Equals
     ***********************************************/

    @Test
    public void testHashCodeEqualsHasBoth() throws IOException {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "PerformanceRoom.class");
        ArrayList<File> files = new ArrayList<File>();
        Check check = new HashcodeAndEquals(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("There are no classes that violate HashCodeAndEquals Check. Good job!");
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void testHashCodeEqualsMissingHashCode() throws IOException {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "Piano.class");
        ArrayList<File> files = new ArrayList<File>();
        Check check = new HashcodeAndEquals(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("Piano class violates HashCodeAndEquals: Missing the hashCode method");
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void testHashCodeEqualsMissingEquals() throws IOException {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "Guitar.class");
        ArrayList<File> files = new ArrayList<File>();
        Check check = new HashcodeAndEquals(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("Guitar class violates HashCodeAndEquals: Missing the equals method");
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void testHashCodeEqualsHasNeither() throws IOException {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "milk.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "mocha.class");
        ArrayList<File> files = new ArrayList<File>();
        Check check = new HashcodeAndEquals(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("There are no classes that violate HashCodeAndEquals Check. Good job!");
        checkEquals(expectedAnswer, ans);
    }

    /***********************************************
     *  Tight Coupling
     ***********************************************/

    @Test
    public void testTightCouplingTooHigh() throws IOException {
        List<String> filePaths = getHashCodeANDCouplingFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new TightCoupling(generateModels(files, filePaths), 0.3);
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = getTestTightCouplingTooHighValues();
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void testTightCouplingPassButCircular() throws IOException {
        List<String> filePaths = getHashCodeANDCouplingFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new TightCoupling(generateModels(files, filePaths), 0.8);
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = getTestTightCouplingPassButCircular();
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void testTightCouplingPasses() throws IOException {
        List<String> filePaths = getGoodDecoratorFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new TightCoupling(generateModels(files, filePaths), 0.5);
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = getTestTightCouplingPasses();
        checkEquals(expectedAnswer, ans);
    }


    /***********************************************
     *  Decorator Pattern
     ***********************************************/

    @Test
    public void decoratorPatternNoImplementationDetected() throws IOException {
        List<String> filePaths = getHashCodeANDCouplingFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new DecoratorPattern(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("No decorator pattern detected.");
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void decoratorPatternGoodImplementationDetected() throws IOException {
        List<String> filePaths = getGoodDecoratorFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new DecoratorPattern(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = getGoodDecoratorDetected();
        checkEquals(expectedAnswer, ans);
    }

    @Test
    public void decoratorPatternBadImplementationDetected() throws IOException {
        List<String> filePaths = getBadDecoratorFilePaths();
        ArrayList<File> files = new ArrayList<File>();
        Check check = new DecoratorPattern(generateModels(files, filePaths));
        List<String> ans = check.performCheck();
        List<String> expectedAnswer = getBadDecoratorDetected();
        checkEquals(expectedAnswer, ans);
    }

    /***********************************************
     *  HELPER METHODS
     ***********************************************/

    private void checkEquals(List<String> expectedAnswer, List<String> ans) {
        assertEquals(expectedAnswer.size(), ans.size());
        for(int i = 0; i < ans.size(); i ++) {
            assertEquals(ans.get(i), expectedAnswer.get(i));
        }
    }

    private ArrayList<ClassModel> generateModels(ArrayList<File> files, List<String> filePaths) throws IOException {
        for(String filePath : filePaths) {
            File file = new File(filePath);
            files.add(file);
        }
        ClassModelGenerator ASMModels = new ASMLibrary();
        return ASMModels.generateClassModels(files);
    }

    private static List<String> getTestTightCouplingPasses() {
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("There is not overall tight coupling. Good job!");
        expectedAnswer.add("Max Relationships: 60");
        expectedAnswer.add("Total Relationships: 1");
        expectedAnswer.add("HasA Relationships: 1");
        expectedAnswer.add("Total Dependencies: 0");
        expectedAnswer.add("Given compare value: 0.5");
        expectedAnswer.add("Relative Max Value: 30");
        expectedAnswer.add("There are no circular dependencies between classes. Good job!");
        return expectedAnswer;
    }

    private static List<String> getTestTightCouplingPassButCircular() {
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("There is not overall tight coupling. Good job!");
        expectedAnswer.add("Max Relationships: 12");
        expectedAnswer.add("Total Relationships: 8");
        expectedAnswer.add("HasA Relationships: 4");
        expectedAnswer.add("Total Dependencies: 4");
        expectedAnswer.add("Given compare value: 0.8");
        expectedAnswer.add("Relative Max Value: 9");
        expectedAnswer.add("Circular dependency between Guitar, Piano, SUGGESTION: considering removing this circular dependency");
        expectedAnswer.add("Circular dependency between Piano, PerformanceRoom, SUGGESTION: considering removing this circular dependency");
        return expectedAnswer;
    }

    private static List<String> getTestTightCouplingTooHighValues() {
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("Overall tight coupling has been detected (total relationships > compareVal). Considering reducing hasA & dependency relationships.");
        expectedAnswer.add("Max Relationships: 12");
        expectedAnswer.add("Total Relationships: 8");
        expectedAnswer.add("HasA Relationships: 4");
        expectedAnswer.add("Total Dependencies: 4");
        expectedAnswer.add("Given compare value: 0.3");
        expectedAnswer.add("Relative Max Value: 3");
        expectedAnswer.add("Circular dependency between Guitar, Piano, SUGGESTION: considering removing this circular dependency");
        expectedAnswer.add("Circular dependency between Piano, PerformanceRoom, SUGGESTION: considering removing this circular dependency");
        return expectedAnswer;
    }

    private List<String> getHashCodeANDCouplingFilePaths() {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "Guitar.class");
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "PerformanceRoom.class");
        filePaths.add(path + "hashcodeandcoupling" + File.separator + "Piano.class");
        return filePaths;
    }

    private List<String> getGoodDecoratorFilePaths() {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "Beverage.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "Coffee.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "CondimentDecorator.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "Milk.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "Mocha.class");
        filePaths.add(path + "decorator" + File.separator + "goodimplementation" + File.separator + "Whip.class");
        return filePaths;
    }

    private List<String> getGoodDecoratorDetected() {
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("CondimentDecorator decorates Beverage");
        expectedAnswer.add("Milk decorates (through abstract decorating class: CondimentDecorator) Beverage");
        expectedAnswer.add("Mocha decorates (through abstract decorating class: CondimentDecorator) Beverage");
        expectedAnswer.add("Whip decorates (through abstract decorating class: CondimentDecorator) Beverage");
        expectedAnswer.add("CORE detected: Coffee for Beverage");
        expectedAnswer.add("Good execution of decorator pattern!");
        return expectedAnswer;
    }

    private List<String> getBadDecoratorDetected() {
        List<String> expectedAnswer = new ArrayList<String>();
        expectedAnswer.add("Milk decorates Beverage");
        expectedAnswer.add("VIOLATE CHECK 1: No abstract decorator class for Milk, SUGGESTION: PLEASE ADD A DECORATING ABSTRACT CLASS FOR MORE FLEXIBILITY");
        expectedAnswer.add("VIOLATE CHECK 2: Method does not call the same method on its parent field, SUGGESTION: Double check methods to ensure they call the method on the field");
        expectedAnswer.add("Mocha decorates Beverage");
        expectedAnswer.add("VIOLATE CHECK 1: No abstract decorator class for Mocha, SUGGESTION: PLEASE ADD A DECORATING ABSTRACT CLASS FOR MORE FLEXIBILITY");
        expectedAnswer.add("Whip decorates Beverage");
        expectedAnswer.add("VIOLATE CHECK 1: No abstract decorator class for Whip, SUGGESTION: PLEASE ADD A DECORATING ABSTRACT CLASS FOR MORE FLEXIBILITY");
        expectedAnswer.add("VIOLATE CHECK 3: No Core Found, SUGGESTION: ADD A CORE CLASS");
        expectedAnswer.add("Poor execution of decorator pattern. See violating constraints above.");
        return expectedAnswer;
    }

    private List<String> getBadDecoratorFilePaths() {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(path + "decorator" + File.separator + "badimplementation" + File.separator + "Beverage.class");
        filePaths.add(path + "decorator" + File.separator + "badimplementation" + File.separator + "Milk.class");
        filePaths.add(path + "decorator" + File.separator + "badimplementation" + File.separator + "Mocha.class");
        filePaths.add(path + "decorator" + File.separator + "badimplementation" + File.separator + "Whip.class");
        return filePaths;
    }

    
}
