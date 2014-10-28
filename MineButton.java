

import java.awt.Color;

import javax.swing.JButton;

/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MineButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
        isMine = status;
    }
    
    public void setFlag(){    
        if (!isFlagged && !this.getText().equals("" + minesTouching) ){
        	setForeground(Color.BLACK);
            setText("F");
        }
        else if(!this.getText().equals("" + minesTouching)) {
            setText("");
            setMineColor();
        }
        isFlagged = !isFlagged;
    }
    public void blow(){
    	if (isMine)
    		setText("M");
    	else
    		setText("" + minesTouching);
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
    
    private void setMineColor(){
    	if (minesTouching <= 2 && !isMine)
    		this.setForeground(Color.GREEN);
    	else if (minesTouching > 2 && minesTouching <= 4 && !isMine)
    		this.setForeground(new Color(255, 140, 0));
    	else if (minesTouching > 4 && minesTouching <= 9 && !isMine)
    		this.setForeground(Color.RED);

    }
    
    public void setMinesTouching(int mines){
        minesTouching = mines;
        setMineColor();
    }
}
