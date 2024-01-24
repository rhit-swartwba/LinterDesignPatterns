package domain.classadapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface ClassModelGenerator {
    public abstract ArrayList<ClassModel> generateClassModels(ArrayList<File> files) throws IOException;
}
