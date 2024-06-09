package Components;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Counter extends JPanel {

    private int count;

    public Counter() {
        count = 0;
        setPreferredSize(new Dimension(15,15));
    }

    public void increment() {
        count += 1;
    }

    public void reset() {
        count = 0;
    }

    public void display() {
        JLabel text = new JLabel("Count: " + count);
        add(text);
    }
}
