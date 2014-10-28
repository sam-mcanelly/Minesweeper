

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MineFrame extends JFrame implements ActionListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem save;
    private JMenuItem load;
    private JMenu newGame;
    private JMenuItem easy;
    private JMenuItem medium;
    private JMenuItem hard;
    private JMenuItem quit;
    private JMenuBar menuBar;
    private JMenu file;
    private MinePanel panel;
    public static final int EASY = 12;
    public static final int MEDIUM = 20;
    public static final int HARD = 28;
    public static final int SMALL = 10;
    public static final int NORMAL = 12;
    public static final int LARGE = 16;
    
    public MineFrame(){
        setSize(700, 700);
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new MinePanel(LARGE, HARD);
        menuBar = new JMenuBar();
        file = new JMenu("file");
        save = new JMenuItem("save");
        load = new JMenuItem("load");
        newGame = new JMenu("new");
        easy = new JMenuItem("easy");
        medium = new JMenuItem("medium");
        hard = new JMenuItem("hard");
        quit = new JMenuItem("quit");
        
        save.addActionListener(this);
        load.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        quit.addActionListener(this);
        
        file.add(save);
        file.add(load);
        file.add(newGame);
        file.add(quit);
        
        newGame.add(easy);
        newGame.add(medium);
        newGame.add(hard);
        
        menuBar.add(file);
        this.setJMenuBar(menuBar);
        add(panel);
    }
    
    public enum Difficulty {
        EASY, MEDIUM, HARD
    };
    
    public enum Size {
    	SMALL, NORMAL, LARGE
    };
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == save) {
        	panel.save();
        } else if (e.getSource() == load) {
            panel.load();
        } else if (e.getSource() == easy) {
            panel.resetMineField(SMALL, EASY);
        } else if (e.getSource() == medium) {
            panel.resetMineField(NORMAL, MEDIUM);
        } else if (e.getSource() == hard) {
            panel.resetMineField(LARGE, HARD);
        } else if (e.getSource() == quit) {
            System.exit(0);
        }
    }
    
}
