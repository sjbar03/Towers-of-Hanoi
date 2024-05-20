package Components;

import java.awt.Dimension;
import javax.swing.JPanel;

public class Disk extends JPanel implements Comparable<Disk> {

    private int weight;

    public Disk() {
        weight = 0;
        setPreferredSize(new Dimension(200,20));
    }

    /**
     * Create a new disk object with a specified weight.
     * @param weight - Integer, the desired weight of this disk.
     */
    public static Disk of(int weight) {
        Disk n = new Disk();
        n.setWeight(weight);
        return n;
    }

    /** Changes the weight of this disk to d. */
    public void setWeight(int d) {
        weight = d;
    }

    /** Changes the weight of this disk by amount d. */
    public void changeWeight(int d) {
        weight = weight + d;
    }

    /**
     * @return the weight of this specific disk.
     */
    public int weight() {
        return weight;
    }

    @Override
    public int compareTo(Disk o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return Integer.toString(weight);
    }

}
