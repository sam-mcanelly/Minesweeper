

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MineFrame extends JFrame implements ActionListener {
    
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem newGame;
    private JMenuItem quit;
    private JMenuBar menuBar;
    private JMenu file;
    private MinePanel panel;
    public static final int EASY = 12;
    public static final int MEDIUM = 20;
    public static final int HARD = 28;
    
    public MineFrame(){
        setSize(500, 500);
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new MinePanel();
        menuBar = new JMenuBar();
        file = new JMenu("file");
        save = new JMenuItem("save");
        load = new JMenuItem("load");
        newGame = new JMenuItem("new");
        quit = new JMenuItem("quit");
        
        save.addActionListener(this);
        load.addActionListener(this);
        newGame.addActionListener(this);
        quit.addActionListener(this);
        
        file.add(save);
        file.add(load);
        file.add(newGame);
        file.add(quit);
        
        menuBar.add(file);
        this.setJMenuBar(menuBar);
        add(panel);
    }
    
    public enum Difficulty {
        EASY, MEDIUM, HARD
    };
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == save) {
        	System.out.println("save");
        } else if (e.getSource() == load) {
            System.out.println("load");
        } else if (e.getSource() == newGame) {
            panel.resetMineField(12, HARD);
            System.out.println("new game");
        } else if (e.getSource() == quit) {
            System.exit(0);
        }
    }
    
}
