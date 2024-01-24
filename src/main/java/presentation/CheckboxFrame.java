package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class CheckboxFrame extends JFrame {
    private JCheckBox[] checkBoxes;
    private JButton submitButton;
    private boolean[] selectedValues;
    private CountDownLatch latch;

    public CheckboxFrame(String[] options) {
        setTitle("Checkbox Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        checkBoxes = new JCheckBox[options.length+3];

        JCheckBox printClass = new JCheckBox("Print details of all classes?");
        JCheckBox printFields= new JCheckBox("Print details of all fields?");
        JCheckBox printMethods = new JCheckBox("Print details of all methods?");
        panel.add(printClass);
        panel.add(printFields);
        panel.add(printMethods);

        checkBoxes[0] = printClass;
        checkBoxes[1] = printFields;
        checkBoxes[2] = printMethods;
        
        for (int i = 0; i < options.length; i++) {
            checkBoxes[i+3] = new JCheckBox(options[i]);
            panel.add(checkBoxes[i+3]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedValues = new boolean[checkBoxes.length];
                for (int i = 0; i < checkBoxes.length; i++) {
                    selectedValues[i] = checkBoxes[i].isSelected();
                }
                latch.countDown(); 
            }
        });
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    public boolean[] getSelectedValues() throws InterruptedException {
        latch = new CountDownLatch(1);
        latch.await(); 
        return selectedValues;
    }

}
