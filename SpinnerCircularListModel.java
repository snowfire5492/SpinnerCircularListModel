// Demonstrate a spinner based on SpinnerListModel. 

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.List;

class SpinnerDemo {

    JSpinner jspin;
    JLabel jlab;
    // Create an array of RGB colors. 
    String colors[] = {"Red", "Green", "Blue", "Black"};
    Color colorVals[] = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

    SpinnerDemo() {
        // Create a new JFrame container. 
        JFrame jfrm = new JFrame("SpinnerListModel");

        // Specify FlowLayout manager.   
        jfrm.setLayout(new FlowLayout());

        // Give the frame an initial size.  
        jfrm.setSize(220, 100);

        // Terminate the program when the user closes the application.  
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create spinner list model. 
        SpinnerListModel spm = new SpinnerCircularListModel(colors);

        // Create a JSpinner and specify the model. 
        jspin = new JSpinner(spm);

        //jspin.getEditor().getComponent(0).setEnabled(false);
        // Set the preferred size of the spinner. 
        jspin.setPreferredSize(new Dimension(60, 20));

        // Make a label that displays the selection 
        // and set its border color.  Because Red is the 
        // first string in the list, it will be selected 
        // by default when the spinner is created. 
        jlab = new JLabel(" Current selection is: Red ");
        jlab.setBorder(BorderFactory.createLineBorder(Color.RED, 4));

        // Add change listener for the spinner. 
        jspin.addChangeListener((ChangeEvent ce) -> {
            // Obtain the current selection.
            String color = (String) jspin.getValue();
            
            // Report the selection in the label.
            jlab.setText(" Current selection is: "
                    + color + " ");
            
            // Set the label's border color based on
            // the selection.
//            if(color.equals("Red"))
//              jlab.setBorder(
//                BorderFactory.createLineBorder(Color.RED, 4));
//            else if(color.equals("Green"))
//              jlab.setBorder(
//                BorderFactory.createLineBorder(Color.GREEN, 4));
//            else
//              jlab.setBorder( 
//                BorderFactory.createLineBorder(Color.BLUE, 4));
jlab.setBorder(BorderFactory.createLineBorder(colorVals[getSelectedIndex(jspin, colors)], 4));
        });

        // Add the spinner and label to the content pane. 
        jfrm.add(jspin);
        jfrm.add(jlab);

        // Display the frame.  
        jfrm.setVisible(true);
    }

    public int getSelectedIndex(JSpinner spinner, String[] values) {
        int index = 0;
        for (String o : values) {
            if (o.equals(spinner.getValue())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void setSelectedIndex(JSpinner spinner, List<?> values, int index) {
        spinner.setValue(values.get(index));
    }

    public static void main(String args[]) {
        // Create the frame on the event dispatching thread.  
        SwingUtilities.invokeLater(() -> {
            new SpinnerDemo();
        });
    }
}

@SuppressWarnings("serial")
class SpinnerCircularListModel extends SpinnerListModel {

    public SpinnerCircularListModel(Object[] items) {
        super(items);
    }

    public Object getNextValue() {
        List list = getList();
        int index = list.indexOf(getValue());

        index = (index >= list.size() - 1) ? 0 : index + 1;
        return list.get(index);
    }

    public Object getPreviousValue() {
        List list = getList();
        int index = list.indexOf(getValue());

        index = (index <= 0) ? list.size() - 1 : index - 1;
        return list.get(index);
    }
}