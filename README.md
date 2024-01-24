# Project: Linter

This was a school project I completed with two other classmates to create a linter to detect common programming issues. There was a style check, principle check, pattern check, and additional feature that needed to be developed. We used the base ASM library to read Java code, but for flexibility and maintainence for good software design, created our own adapter class for our work. That way, if we ever changed libraries, we could simply edit the adaptor instead of our functional code. 

The parts of my project focused on recognizing tight coupling and flawed implementations of the Decorator Design Pattern. For my extra A level feature, I suggested changes that could be made to the Decorator Pattern as well as generated a Plant UML diagram inputted code.

For more details on the implementation of these detections, check the Wiki Pages.

## Dependencies
Specified in pom.xml

## Contributors
Blaise Swartwood, Brian Beasley, Jonathan Spychalski

## Team Member's Engineering Notebooks (one per person)
- Blaise Swartwood: https://rosehulman-my.sharepoint.com/:w:/g/personal/swartwba_rose-hulman_edu/EZIzga4rRiFBlzTGC5ZOYCoB1lzjo8dLFV_LUTTovkREYA?e=CNn6Lv
- Jonathan Spychalski: https://docs.google.com/document/d/1WnFdSPrPbyNB1I5SHAUfkQUvhkN9AeC4X7wKCafcZIQ/edit?usp=sharing
- Brian Beasley: https://rosehulman-my.sharepoint.com/:w:/g/personal/beaslebf_rose-hulman_edu/EXVKuH8oW8pEhaUjGXZLSlcBG0SR43sV_P6gRtNTKc_Faw?e=baRcLV

## Features


| Developer         | Style Check        | Principle Check | Pattern Check     | A Feature (optional) |
|:------------------|:-------------------|:----------------|:------------------|:---------------------|
| Blaise Swartwood   |  Hashcode & Equals |  Tight Coupling | Decorator Pattern |  Errors/Suggestions in Decorator Pattern && PlantUML Code Generation |
| Brian Beasley      | Source Code Method Length | Holleywood Principle | Strategy Pattern |  |
| Jonathan Spychalski| Variable Name Format | Information Hiding | Template Pattern |                  


## Configure and Run Tests ##
Upon running the main method (located inside Main.java, in src/main/java/presentation), a JFileChooser will appear. Then you can navigate through the file chooser to select any .class files you wish to use (more than one can be selected, but they must be located in the same directory). Alternatively, you can select a directory, which will simply select all .class files located in that directory. Then you can select the "Open" button. This will add those .class files to the selection. The JFileChooser will pop up again. If you have already selected all the files you want, you can click "Cancel." If you want to select more, from other directories, you can continue to select them in the same way and click "Open". When you are finished, click "Cancel". Make sure you haven't selected anything when you click Cancel, this is for after you have aleady selected all the files you want. Once you finish selecting, a box will appear where you can select what information you want to print (class/field/method info) as well as which tests you would like to run. Once you select the tests, they will run and the results will be printed to the console. 

In order to utilize any of the test classes we have provided, navigate to target/test-classes/domain within this project, where the .class files are organized by check, from within the JFileChooser. 

The JUnit tests can be run from UnitTests.java, located in src/test/java.
