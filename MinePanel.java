

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MinePanel extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MineButton buttons[][];
    private MineField mainField;
    private final JFileChooser save;
    private final JFileChooser load;
    
    public MinePanel(){
        save = new JFileChooser();
        load = new JFileChooser();
    }
    
    public MinePanel(int sideLength, int diff){
    	this();
        initializeGame(sideLength, diff);
        
    }
    
    @Override
    public void paintComponent(Graphics g){
       
    }
    
    public void resetMineField(int side, int diff){
        initializeGame(side, diff);
    }
    
    public void save(){
    	int returnVal = save.showSaveDialog(this);
    	File outputFile;
    	if (returnVal == JFileChooser.APPROVE_OPTION){
    		outputFile = save.getSelectedFile();
    		try {
    			PrintWriter out = new PrintWriter(new FileWriter(outputFile));
    			int[][] board = mainField.getBoard();
    			boolean[][] flagged = mainField.getFlagged();
    			boolean[][] shown = mainField.getShown();
    			for(int i = 0; i < board.length; i++){
    				for (int j = 0; j < board[i].length; j++){
    					out.print(board[i][j]);
    				}
    				out.println();
    			}
    			for(int i = 0; i < flagged.length; i++){
    				for (int j = 0; j < flagged[i].length; j++){
    					if (flagged[i][j])
    						out.print("1");
    					else
    						out.print("0");
    				}
    				out.println();
    			}
    			for(int i = 0; i < shown.length; i++){
    				for (int j = 0; j < shown[i].length; j++){
    					if (shown[i][j])
    						out.print("1");
    					else
    						out.print("0");
    				}
    				out.println();
    			}
    			out.flush();
    			out.close();
    		} catch (IOException e) {
    			
    		} finally {
    			
    		}
    	} else {
    		return;
    	}
    }
    
    public void load() {
    	int returnVal = load.showOpenDialog(this);
    	File inputFile;
    	if (returnVal != JFileChooser.APPROVE_OPTION)
    		return;
    	else
    		inputFile = load.getSelectedFile();
    	BufferedReader reader;
    	String line;
    	//initializing them so the compiler won't complain to me
    	int[][] board = new int[20][20];
		boolean[][] flagged = new boolean[20][20];
		boolean[][] shown = new boolean[20][20];
    	int i = 0;
    	int j = 0;
    	try {
    		reader = new BufferedReader(new FileReader(inputFile));
    		while ((line = reader.readLine()) != null) {
    			if (i == 0) {
    				board = new int[line.length()][line.length()];
    				flagged = new boolean[line.length()][line.length()];
    				shown = new boolean[line.length()][line.length()];
    			}
    			if (i < line.length()) {
    				j = 0;
    				for (char c: line.toCharArray()) {
    					if (j > board.length - 1)
    						break;
    					board[i][j] = Character.getNumericValue(c);
    					j++;
    				}	
    			} else if (i < line.length() * 2 && i > line.length() - 1){
    				j = 0;
    				for(char c: line.toCharArray()){
    					if (j > board.length - 1)
    						break;
    					if (c == '1')
    						flagged[i - line.length()][j] = true;
    					else if (c == '0')
    						flagged[i - line.length()][j] = false;
    					j++;
    				}
    			} else if (i < line.length() * 3 && i > line.length() * 2 - 1) {
    				j = 0;
    				for(char c: line.toCharArray()){
    					if (j > board.length - 1)
    						break;
    					if (c == '1')
    						shown[i - (line.length() * 2)][j] = true;
    					else if (c == '0')
    						shown[i - (line.length() * 2)][j] = false;
    					j++;
    				}
    			} else {
    				break;
    			}
    			i++;
    		}
    		reader.close();
    	} catch (java.io.IOException e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to read file", "Error!", JOptionPane.INFORMATION_MESSAGE);
    		return;
    	} finally {
    		
    	}
    	mainField.loadGame(board, flagged, shown);
    	loadButtons(board[0].length);
    	showFlaggedAndShown(board[0].length);
    }
    
    public void loadButtons(int side){
    	if (mainField == null)
    		return;
    	this.removeAll();
        this.updateUI();
        this.setLayout(new GridLayout(side, side));
        buttons = null;
        buttons = new MineButton[side][side];
    	for (int i = 0; i < side; i++){
    		for (int j = 0; j < side; j++){
    			buttons[i][j] = new MineButton(i, j);
                buttons[i][j].addMouseListener(new MouseHandler());
                if(mainField.getMineStatus(i, j)){
                    buttons[i][j].setMineStatus(true);
                }
                else {
                    buttons[i][j].setMinesTouching(mainField.getMinesTouching(i, j));
                }
                add(buttons[i][j]);

    		}	
    	}
    	for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                buttons[i][j].setText("");
            }
        }
    }
    
    private void showFlaggedAndShown(int side){
    	for (int i = 0; i < side; i++){
    		for (int j = 0; j < side; j++){
    			if (mainField.isShown(i, j) && !mainField.getMineStatus(i, j))
    				buttons[i][j].blow();
    			else if (mainField.isFlagged(i, j))
    				buttons[i][j].setFlag();
    		}
    	}
    }
    
    public void endGame(boolean winner) {
        revealAll();
        if (!winner)
            JOptionPane.showMessageDialog(this, "You Lose!");
        else
            JOptionPane.showMessageDialog(this, "You Win!");
    }
    
    public void revealAll(){
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(mainField.getMineStatus(i, j)){
                    buttons[i][j].blow();
                }
                else {
                    buttons[i][j].blow();
                }
            }
        }
    }
    
    public void initializeGame(int side, int diff){
        mainField = null;
        mainField = new MineField(side, diff);
        loadButtons(side);
    }
    
    
    private class MouseHandler extends MouseAdapter {
      
        @Override
        public void mouseReleased(MouseEvent e){
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons.length; j++){
                    if (e.getSource() == buttons[i][j]){
                        if (e.getButton() == 3){
                            buttons[i][j].setFlag();
                            mainField.setFlag(i, j);
                            if (mainField.checkForWinner()){
                                revealAll();
                                endGame(true);
                            }
                        }
                        else {
                            if(mainField.getMineStatus(i, j) == true) {
                                endGame(false);
                            } else {
                                if (!mainField.getFlaggedStatus(i, j)){
                                    buttons[i][j].blow();
                                    mainField.show(i, j);
                                    	
                                }
                            }
                            if (mainField.checkForWinner()){
                                revealAll();
                                endGame(true);
                            }
                        }
                    }
                }
            }
        }
    }
}

