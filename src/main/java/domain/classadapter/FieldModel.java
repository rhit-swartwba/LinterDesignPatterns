package domain.classadapter;

public class FieldModel {
    private int access;
    private String name;
    private Object value;

    private String desc;

    public FieldModel(int access, String name, Object value, String desc) {
        this.access = access;
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    public String getDetails(){
        String x = "";
        x += "**Field**\n";
        x += "Name: " + this.name + "\n";
        x += "Access type: " + this.access + "\n";
        x += "Value: " + this.value + "\n";
        x += "Type: " + this.desc + "\n";
        x += "\n";
        return x;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getDesc() { return this.desc;
    }


}
