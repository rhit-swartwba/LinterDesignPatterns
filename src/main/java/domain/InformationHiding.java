package domain;

import domain.classadapter.ClassModel;
import java.util.ArrayList;
import java.util.List;

public class InformationHiding extends Check{
    public InformationHiding(ArrayList<ClassModel> classes) {
        super(classes);
    }

    @Override
    public List<String> performCheck() {
        return null;
    }
}
