

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MinePanel extends JPanel {
    
    private MineButton buttons[][];
    private boolean gameOver = false;
    private MineField mainField;
    
    
    public MinePanel(){
        initializeGame(10, 12);
    }
    
    public MinePanel(int sideLength, int diff){
        initializeGame(sideLength, diff);
    }
    
    @Override
    public void paintComponent(Graphics g){
       
    }
    
    public void resetMineField(int side, int diff){
        initializeGame(side, diff);
    }
    
    public void save(){
    	
    }
    
    public void load() {
    	
    }
    
    public void endGame(boolean winner) {
        revealAll();
        if (!winner)
            JOptionPane.showMessageDialog(this, "You Lose!");
        else
            JOptionPane.showMessageDialog(this, "You Win!");
    }
    
    public void revealAll(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mainField.getMineStatus(i, j)){
                    buttons[i][j].setText("MINE");
                }
                else {
                    buttons[i][j].setText("" + buttons[i][j].getMinesTouching());
                }
            }
        }
    }
    
    public void initializeGame(int side, int diff){
        this.removeAll();
        this.updateUI();
        this.setLayout(new GridLayout(side, side));
        buttons = null;
        mainField = null;
        buttons = new MineButton[side][side];
        mainField = new MineField(side, diff);
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                buttons[i][j] = new MineButton(i, j);
                buttons[i][j].addMouseListener(new MouseHandler(i, j));
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
    
    
    
    
    private class MouseHandler extends MouseAdapter {
        private int i;
        private int j;
        
        public MouseHandler(int i, int j){
            this.i = i;
            this.j = j;
        }
    
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
                                gameOver = true;
                                endGame(false);
                            } else {
                                if (!mainField.getFlaggedStatus(i, j)){
                                    buttons[i][j].blow(i, j);
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

