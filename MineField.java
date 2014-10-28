

import java.util.Random;

/**
 *
 * @author Sam McAnelly
 * CWID: 11533007
 */
public class MineField {
    
    private int board[][];
    private boolean flagged[][];
    private boolean shown[][];
    private int totalMines;
    private int flagsLanded;
    private int side;
    
    
    
    public MineField(int sideLength, int numberOfMines){
    	side = sideLength;
        board = new int[sideLength][sideLength];
        shown = new boolean[sideLength][sideLength];
        flagged = new boolean[sideLength][sideLength];
        populateFlagged();
        populateShown();
        totalMines = numberOfMines;
        initializeBoard(sideLength);
        placeMines(numberOfMines);
        flagsLanded = 0;
    }
    
    
    
    //initializing the minefield
    private void populateFlagged(){
        for (int i = 0; i < flagged.length; i++)
            for (int j = 0; j < flagged[i].length; j++)
                flagged[i][j] = false;
    }
    private void populateShown() {
        for (int i = 0; i < shown.length; i++)
            for (int j = 0; j < shown[i].length; j++)
                shown[i][j] = false;
    }
    public void placeMines(int numberOfMines){
        int i = 0;
        while(i < numberOfMines){
            Random placement = new Random();
            int x = placement.nextInt(10000) % 10;
            int y = placement.nextInt(10000) % 10;
            if(board[x][y] != 9){
                board[x][y] = 9;
                shown[x][y] = true;
                i++;
                for(int j = -1; j <= 1; j++){
                    for(int k = -1; k <= 1; k++){
                        //if the point is the newly placed mine, skip it and don't add 1 to it. 
                        if(k == 0 && j ==0)
                            continue;
                        else
                            try{
                                if(board[x+j][y+k] == 9)
                                    continue;
                                board[x + j][y + k] += 1;
                            } catch(ArrayIndexOutOfBoundsException e){
                                
                            }
                    }
                }
                //Tells where all mines are placed for testing purposes
                //System.out.println("Mine placed at (" + (y+1) + ", " + (x+1) + ")...");
            }
        }
    }
    public void initializeBoard(int sideLength){
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength; j++){
                board[i][j] = 0;
            }
        }
    }
   
    //getters
    public boolean getMineStatus(int x, int y){
        if(board[x][y] == 9)
            return true;
        else
            return false;
    }
    public int getMinesTouching(int x, int y){
        return board[x][y];
    }
    public boolean getFlaggedStatus(int x, int y) {
        return flagged[x][y];
    }
    public int getSideLength(){
    	return side;
    }
    public boolean[][] getShown(){
    	return shown;
    }
    public boolean[][] getFlagged(){
    	return flagged;
    }
    public int[][] getBoard(){
    	return board;
    }
    
    //changing the status of positions on the board
    public void setFlag(int x, int y) {
        flagged[x][y] = !flagged[x][y];
        if (flagged[x][y])
            flagsLanded++;
        else
            flagsLanded--;
    }
    public void show(int x, int y){
    	if (x < 0 || x >= side || y < 0 || y >= side)
    		return;
        shown[x][y] = true;
    }
    
    public boolean isShown(int x, int y){
    	return shown[x][y];
    }
    
    public boolean isFlagged(int x, int y){
    	return flagged[x][y];
    }
    
    public boolean checkForWinner(){
        /*
         * Prints out the status of the board for testing 
         *
        System.out.println("Flags Landed: " + flagsLanded);
        System.out.println("Total Mines: " + totalMines);
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
         */
        if (flagsLanded != totalMines){
            return false;
        }
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (!shown[i][j])
                    return false;
                else if (flagged[i][j] == false && board[i][j] == 9){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void loadGame(int gameBoard[][], boolean flags[][], boolean show[][]){
    	board = gameBoard;
    	shown = show;
    	flagged = flags;
    	side = board.length;
    }
    
    
    
    /*
     * Testing purposes
    
    private void showFlaggedBoard(){
        for (int i = 0; i < flagged.length; i++){
            for (int j = 0; j < flagged[i].length; j++) {
                System.out.print(flagged[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(flagsLanded);
        System.out.println();
    }
    
    private void showShownBoard() {
        for (int i = 0; i < shown.length; i++){
            for (int j = 0; j < shown[i].length; j++) {
                System.out.print(shown[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
     
     */
}
