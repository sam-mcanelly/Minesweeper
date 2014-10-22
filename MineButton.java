

import javax.swing.JButton;

/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MineButton extends JButton {
    private boolean isMine = false;
    private boolean isFlagged = false;
    private int x;
    private int y;
    private int minesTouching = 0;
    
    
    public MineButton(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    //getters and setters
    public void setMineStatus(boolean status){
        this.isMine = status;
    }
    
    public void setFlag(){    
        if (!isFlagged && !this.getText().equals("" + minesTouching) ){
            setText("F");
        }
        else if(!this.getText().equals("" + minesTouching)) {
            setText("");
        }
        isFlagged = !isFlagged;
    }
    public boolean blow(int x, int y){
        setText("" + minesTouching);
        return isMine;
    }
    
    public boolean getFlaggedStatus(){
        return isFlagged;
    }
    public int getXPosition(){
        return x;
    }
    public int getYPosition(){
        return y;
    }
    public int getMinesTouching(){
        return minesTouching;
    }
    public void setMinesTouching(int mines){
        minesTouching = mines;
    }
}
