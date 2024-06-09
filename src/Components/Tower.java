package Components;
import Game.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.EmptyStackException;
import java.util.Stack;
import javax.swing.JPanel;

public class Tower extends JPanel {
    public final Stack<Disk> stack;
    public Game game;

    private static final Color[] colors = new Color[] {Color.red, Color.orange, Color.yellow,
            Color.green, Color.blue, Color.MAGENTA};

    public Tower(Game game) {
        stack = new OrderedStack<>();
        this.game = game;

        setPreferredSize(new Dimension(200, 700));
    }

    /**
     * Add a new disk e to the top of this tower. Requires that e has the smallest weight in the
     * stack.
     */
    public void addDisk(Disk e) {
        stack.push(e);
    }

    /** Removes the disk from the top of this tower and returns it. */
    public Disk removeTop() throws EmptyStackException{
        return stack.pop();
    }

    public void removeAllDisks() {
        while (!stack.isEmpty()) {
            stack.pop();
        }
    }

    /**
     * @return the number of disks currently on this Tower.
     */
    public int stackSize() {
        return stack.size();
    }

    /**
     * Draw this tower's representative pyramid with the appropriate amount of whitespace above it.
     */
    private void drawPyramid(Graphics2D g) {
        synchronized (g) {
            int Ycurr = getHeight();
            synchronized(stack) {
                for (Disk disk: stack) {
                    Ycurr -= 20;
                    g.setColor(colors[disk.weight() % 6]);
                    g.fillRect((getWidth()/2)-(disk.weight()*5), Ycurr, disk.weight()*10, 20);
                }
            }
        while (Ycurr > 0) {
            Ycurr -= 20;
            g.fillRect(getWidth()/2, Ycurr, 0, 20);
        }
        }
        
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawPyramid(g2d);
    }

    /**
     * Add this tower to the content pane of the main screen in its respective location.
     * @param contentPane - The content pane for this tower to be added to.
     * @param location - the BorderLayout location. Must be a valid BorderLayout location string.
     */
    public void populateContentPane(Container contentPane, String location) {
        contentPane.add(this, location);
    }
}
