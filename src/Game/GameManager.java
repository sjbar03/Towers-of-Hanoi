package Game;

import Components.Counter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;


public class GameManager {

    public static final JFrame frame = new JFrame("Towers of Hanoi!");
    private static Thread gameThread = new Thread();
    private static final Game game = new Game(3, 1000);

    public static int MAX_DISKS = 20;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui(game));
    }

    private static void createAndShowGui(Game game) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        game.populateBoard(frame);

        JSlider diskSlider = new JSlider(1, 20, 3);
        diskSlider.setBorder(BorderFactory.createTitledBorder("Number of disks"));
        diskSlider.setMajorTickSpacing(1);
        diskSlider.setPaintLabels(true);
        diskSlider.setPaintTicks(true);
        diskSlider.setToolTipText("Please select how many disks you'd like to play with. BE AWARE"
                + " 20 disks takes ~16 minutes to complete on 1 ms speed (over 1,000,000 moves!!)");
        diskSlider.addChangeListener((c) -> game.changeNumDisks(
                diskSlider.getValue()));

        JSlider speedSlider = new JSlider(0, 2000, 1000);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Speed (ms)"));
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener((c) -> game.changeSpeed(speedSlider.getValue()+1));

        frame.add(diskSlider, BorderLayout.PAGE_START);
        frame.add(speedSlider, BorderLayout.PAGE_END);

        // Create start button and add functionality.
        gameThread = new Thread(game);
        JMenuItem startButton = new JMenuItem("START");
        startButton.addActionListener((ActionEvent e) -> {
                    if (gameThread.isAlive()) {
                        gameThread.interrupt();
                    } else {
                        gameThread = new Thread(game);
                        gameThread.start();
                    }
                });

        JMenuItem resetButton = new JMenuItem("RESET");
        resetButton.addActionListener((e) -> game.restart());

        JMenuBar bar = new JMenuBar();
        bar.add(startButton);
        bar.add(resetButton);

        frame.setJMenuBar(bar);

        frame.pack();
        frame.setVisible(true);
    }

    public static void refresh() {
        frame.repaint();
    }
}
