package domain.classadapter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ASMLibrary implements ClassModelGenerator{

    @Override
    public ArrayList<ClassModel> generateClassModels(ArrayList<File> files) throws IOException {
        ArrayList<ClassModel> classModels = new ArrayList<ClassModel>();
        ArrayList<ClassReader> readers = createReaders(files);
        for (ClassReader reader : readers){
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
            classModels.add(new ClassModel(classNode.access, classNode.name, classNode.signature, classNode.superName,
                     classNode.interfaces, generateFieldModels(classNode), generateMethodModels(classNode)));
        }
        return classModels;
    }

    private ArrayList<MethodModel> generateMethodModels(ClassNode classNode) {
        ArrayList<MethodModel> methodModels = new ArrayList<MethodModel>();
        for (MethodNode method : classNode.methods){
            methodModels.add(new MethodModel(method.access, method.name, method.exceptions, generateParameters(method), generateMethodCalls(method), getMethodLength(method), getDesc(method), getRetType(method)));
        }
        return methodModels;
    }

    private String getRetType(MethodNode method) {
        return Type.getReturnType(method.desc).getClassName();
    }

    private List<String> getDesc(MethodNode method) {
        List<String> argVals = new ArrayList<String>();
        for (Type argType : Type.getArgumentTypes(method.desc)) {
            argVals.add(argType.getClassName());
        }
        return argVals;
    }

    private ArrayList<FieldModel> generateFieldModels(ClassNode classNode){
        ArrayList<FieldModel> fieldModels = new ArrayList<FieldModel>();
        for (FieldNode field : classNode.fields){
            fieldModels.add(new FieldModel(field.access, field.name, field.value, field.desc));
        }
        return fieldModels;
    }

    private ArrayList<FieldModel> generateParameters(MethodNode methodNode){
        ArrayList<FieldModel> parameters = new ArrayList<FieldModel>();
        if(methodNode.parameters != null){
            for(ParameterNode parameter : methodNode.parameters){
                parameters.add(new FieldModel(parameter.access, parameter.name, null, null));
            }
        }

        return parameters;
    }

    private ArrayList<String> generateMethodCalls(MethodNode methodNode){
        ArrayList<String> methodCalls = new ArrayList<String>();
        if(methodNode.instructions != null){
            for(AbstractInsnNode instruction : methodNode.instructions){
               if(instruction.getType() == 5){
                   MethodInsnNode methodCall = (MethodInsnNode) instruction;
                   methodCalls.add(methodCall.owner + methodCall.name);
               }
            }
        }
        return methodCalls;
    }

    private int getMethodLength(MethodNode methodNode){
        ArrayList<Integer> lines = new ArrayList<Integer>();
        if(methodNode.instructions != null) {
            for (AbstractInsnNode instruction : methodNode.instructions) {
                if (instruction.getType() == 15) {
                    LineNumberNode lnNode = (LineNumberNode) instruction;
                    if(!lines.contains(lnNode.line)){
                        lines.add(lnNode.line);
                    }
                }
            }
        }
        return lines.size();
    }

    private ArrayList<ClassReader> createReaders(ArrayList<File> files) throws IOException{
        ArrayList<ClassReader> readers = new ArrayList<ClassReader>();
        for(File f : files) {
            InputStream inputStream = new FileInputStream(f);
            ClassReader reader = new ClassReader(inputStream);
            readers.add(reader);
        }
        return readers;
    }



    ////////////////////THE FOLLOWING METHODS WILL BE REMOVED WHEN NOT NEEDED FOR DEBUGGING//////////////////////////////



    public void printInfo(ArrayList<File> files) throws IOException {
        ArrayList<ClassReader> readers = createReaders(files);
        for(ClassReader reader : readers){
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
            printClass(classNode);
            printFields(classNode);
            printMethods(classNode);
        }
    }



    private static void printClass(ClassNode classNode) {
        System.out.println("Class's Internal JVM name: " + classNode.name);
        System.out.println("User-friendly name: "
                + Type.getObjectType(classNode.name).getClassName());
        System.out.println("public? "
                + ((classNode.access & Opcodes.ACC_PUBLIC) != 0));
        System.out.println("Extends: " + classNode.superName);
        System.out.println("Implements: " + classNode.interfaces);
        // TODO: how do I write a lint check to tell if this class has a bad name?
        if(Character.isLowerCase(classNode.name.charAt(8))){
            System.out.println("Bad class name: " + classNode.name);
        }
        else{
            System.out.println(classNode.name + " is a good class name.");
        }
    }

    private static void printFields(ClassNode classNode) {
        // Print all fields (note the cast; ASM doesn't store generic data with its Lists)
        List<FieldNode> fields = (List<FieldNode>) classNode.fields;
        for (FieldNode field : fields) {
            System.out.println("	Field: " + field.name);
            System.out.println("	Internal JVM type: " + field.desc);
            System.out.println("	User-friendly type: "
                    + Type.getObjectType(field.desc).getClassName());
            // Query the access modifiers with the ACC_* constants.

            System.out.println("	public? "
                    + ((field.access & Opcodes.ACC_PUBLIC) != 0));
            // TODO: how do you tell if something has package-private access? (ie no access modifiers?)
            System.out.println("package-private? " + (field.access == 0));
            // TODO: how do I write a lint check to tell if this field has a bad name?
            if(Character.isUpperCase(field.name.charAt(0))){
                System.out.println(field.name + " is a bad name");
            }
            else{
                System.out.println(field.name + " is a good name");
            }

            System.out.println();
        }
    }

    private void printMethods(ClassNode classNode) {
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        for (MethodNode method : methods) {
            System.out.println("	Method: " + method.name);
            System.out
                    .println("	Internal JVM method signature: " + method.desc);

            System.out.println("	Return type: "
                    + Type.getReturnType(method.desc).getClassName());

            System.out.println("	Args: ");
            for (Type argType : Type.getArgumentTypes(method.desc)) {
                System.out.println("		" + argType.getClassName());
                // FIXME: what is the argument's *variable* name?
            }

            System.out.println("	public? "
                    + ((method.access & Opcodes.ACC_PUBLIC) != 0));
            System.out.println("	static? "
                    + ((method.access & Opcodes.ACC_STATIC) != 0));
            // How do you tell if something has default access? (ie no access modifiers?)

            for (String methodCall : this.generateMethodCalls(method)){
                System.out.println(methodCall);
            }

            System.out.println(getMethodLength(method));

            System.out.println();

            // Print the method's instructions
            //printInstructions(method);
        }
    }

    private static void printInstructions(MethodNode methodNode) {
        InsnList instructions = methodNode.instructions;
        for (int i = 0; i < instructions.size(); i++) {

            // We don't know immediately what kind of instruction we have.
            AbstractInsnNode insn = instructions.get(i);

            // FIXME: Is instanceof the best way to deal with the instruction's type?
            if (insn instanceof MethodInsnNode) {
                // A method call of some sort; what other useful fields does this object have?
                MethodInsnNode methodCall = (MethodInsnNode) insn;
                System.out.println("		Call method: " + methodCall.owner + " "
                        + methodCall.name);
            } else if (insn instanceof VarInsnNode) {
                // Some some kind of variable *LOAD or *STORE operation.
                VarInsnNode varInsn = (VarInsnNode) insn;
                int opCode = varInsn.getOpcode();
                System.out.println("        Manipulate variable: " + varInsn.var + opCode);
                // See VarInsnNode.setOpcode for the list of possible values of
                // opCode. These are from a variable-related subset of Java
                // opcodes.
            }

            if(insn instanceof LineNumberNode){
                LineNumberNode line = (LineNumberNode) insn;
                System.out.println("Line number: " + line.line);
            }
            // There are others...
            // This list of direct known subclasses may be useful:
            // http://asm.ow2.org/asm50/javadoc/user/org/objectweb/asm/tree/AbstractInsnNode.html

            // TODO: how do I write a lint check to tell if this method has a bad name?
        }
    }
}
