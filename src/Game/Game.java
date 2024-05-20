package Game;

import Components.Disk;
import Components.Tower;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {

    public final Tower one = new Tower(this);
    public final Tower two = new Tower(this);
    public final Tower three = new Tower(this);
    private int numDisks;
    private int speed;
    private int numMoves;

    public static void main(String[] args) {
        Game game = new Game(5,0);
        game.play();
    }

    /**
     * Create a new game instance with numDisks disks and a 'delay' of speed milliseconds between
     * each move. Requires numDisks > 0.
     * @param numDisks - number of disks in the game.
     * @param speed - delay between each move (milliseconds).
     */
    public Game(int numDisks, int speed) {
        assert numDisks > 0;

        for (int n = numDisks; n > 0; n--) {
            one.addDisk(Disk.of(n));
        }
        this.numDisks = numDisks;
        this.speed = speed;
        numMoves = 0;

        setPreferredSize(new Dimension(600, 600));
    }

    /**
     * Move all disks from Components.Tower 1 to Components.Tower 3.
     */
    public void play() {
        try {
            moveDisks(numDisks, one, three, two);
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * Move 'numDisks' of the disks on Components.Tower 'here' to Components.Tower 'there.' Performs this via recursion,
     * first moving 'numDisks - 1' to Components.Tower 'other', then moving the bottom disk to Components.Tower 'there',
     * then moving the 'numDisks - 1' to Components.Tower 'there.' This algorithm is inspired by Prof
     * Eichorn's lecture on induction (CS 2800 | Fall 2023).
     *
     * @param numDisks - number of disks to be moved.
     * @param here - the origin tower.
     * @param there - the destination tower.
     * @param other - the other tower in the game.
     */
    public void moveDisks(int numDisks, Tower here, Tower there, Tower other)
            throws InterruptedException {
        if (numDisks == 1) {
            there.addDisk(here.removeTop());
            updateGame();
            return;
        }

        moveDisks(numDisks-1, here, other, there);
        moveDisks(1, here, there, other);
        moveDisks(numDisks-1, other, there, here);
    }

    /**
     * Increments numMoves, prints the current game board, and pauses for the designated amount of
     * time.
     */
    public void updateGame() throws InterruptedException {
        numMoves = numMoves + 1;
        GameManager.refresh();
        synchronized(this) {
            if (speed > 0) {
                wait(speed);
            } else {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }
    }

    /**
     * Prints the current game board to the terminal.
     */
    public void printGame() {
        for (int n = numDisks; n >= 0; n--) {
            printRow(n);
        }
    }

    /**
     * Returns number of disks in the game.
     */
    public int numDisks() {
        return numDisks;
    }

    public int numMoves() {
        return numMoves;
    }

    public void changeNumDisks(Integer i) {
        numDisks = i;
        restart();
        GameManager.refresh();
    }

    public void changeSpeed(Integer i) {
        speed = i;
    }

    /**
     * Prints the row of the game board at index 0. Indexes grow downwards.
     * @param index - the row to print.
     */
    private void printRow(int index) {
        Object[] arrOne = one.stack.toArray();
        Object[] arrTwo = two.stack.toArray();
        Object[] arrThree = three.stack.toArray();

        String diskOne = index < arrOne.length ? ((Disk) arrOne[index]).toString(): " ";
        String diskTwo = index < arrTwo.length ? ((Disk) arrTwo[index]).toString(): " ";
        String diskThree = index < arrThree.length ? ((Disk) arrThree[index]).toString(): " ";

        System.out.println(diskOne + "    " + diskTwo + "    " + diskThree);
    }

    /**
     * Add each of this game's three towers to the main content pane. Tower one goes on the left,
     * Tower two goes in the middle, and Tower three goes on the right.
     * @param frame - the frame whose content pane the towers are being added too.
     */
    public void populateBoard(JFrame frame) {
        one.populateContentPane(frame.getContentPane(), BorderLayout.LINE_START);
        two.populateContentPane(frame.getContentPane(), BorderLayout.CENTER);
        three.populateContentPane(frame.getContentPane(), BorderLayout.LINE_END);
    }

    /**
     * Remove all disks from all towers and populate tower one with the appropriate number of disks.
     */
    public void restart() {
        one.removeAllDisks();
        two.removeAllDisks();
        three.removeAllDisks();

        numMoves = 0;

        for (int n = numDisks; n > 0; n--) {
            one.addDisk(Disk.of(n));
        }

        GameManager.refresh();
    }

    @Override
    public void run() {
        restart();
        play();
    }
}
