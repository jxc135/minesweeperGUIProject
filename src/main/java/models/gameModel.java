package models;

import java.util.Random;
/**
 * The gameModel class represents the game logic behind 'Minesweeper'. It contains all field variables and methods that are required to play the game.
 * The state of the board that the player will only see is the int[][] playerBoard mentioned below.
 * The locations of the mines are held within the int[][] mineBoard mentioned below. The player will NEVER be able to see this.
 *
 * @version 29/08/2020
 * @author JXC135
 *
 */
public class gameModel {
    /**
     *  field variable to represent the number of tiles per row
     */
    private int xtiles;
    /**
     *  field variable to represent the number of tiles per column
     */
    private int ytiles;
    /**
     *  field variable to represent the number of mines
     */
    private int mineNum;
    /**
     *  field variable to represent the number of tiles not revealed
     */
    private int tilesUnrevealed;
    /**
     *  field variable to represent the current state of the player's board
     */
    private int[][] playerBoard;
    /**
     *  field variable to represent where the mines are for a given board size
     */
    private int[][] mineBoard;
    /**
     *  field variable to represent an unrevealed tile as a constant integer number.
     */
    public static final int unrevealed=-1;
    /**
     *  field variable to represent a tile that contains a mine as a constant integer number.
     */
    public static final int mine=-2;
    /**
     * field variable to represent a tile that has been marked/flagged. Used if a player THINKS a tile contains a mines...that's it.
     */
    public static final int flag=-3;
    /**
     * Hint about the field variables referenced from: http://www.eecs.qmul.ac.uk/~mmh/ItP/resources/MineSweeper/Notes.html
     */

    /**
     * Constructor for the gameModel.
     * The constructor validates if parameter values are suitable to set up a game (via 'setUpGame()' method). Then sets the values for the number of rows, columns and amount of mines.
     * Then using 'resetTilesUnrevealed' method, the value of field variable 'tilesUnrevealed' becomes xtiles*ytiles.
     * After, it creates 2 integer matrices, 'playerBoard' and 'mineBoard'.
     * @param xtiles The number of tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines
     */
    public gameModel(int xtiles, int ytiles, int mineNum) {
        try {
            //Validates the values of the parameters first via 'setUpGame()' method before actually constructing the gameModel object
            this.setUpGame(xtiles, ytiles, mineNum);
        }
        catch(Exception e) {
            e.getMessage();
        }
    }

    /**
     * Checks if the given parameters are valid enough to set up a game. If true, set the field variables of this class using the parameters.
     * @param xtiles The number tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines
     * @throws IllegalArgumentException If the method 'isValidGameSetup()' becomes false
     */
    public void setUpGame(int xtiles,int ytiles,int mineNum) throws IllegalArgumentException {
        if(isValidGameSetup(xtiles,ytiles,mineNum)) {
            this.xtiles = xtiles;
            this.ytiles = ytiles;
            this.tilesUnrevealed=xtiles*ytiles;
            this.mineNum=mineNum;
            this.setPlayerBoardSize(xtiles,ytiles);
            this.setMineBoardSize(xtiles,ytiles);
            this.resetPlayerBoard();
            this.resetMineBoard();
            this.assignMines();

        } else {
            throw new IllegalArgumentException("Invalid board size or mineNum");
        }
    }

    /**
     * Constructor for the gameModel (Primarily used for TESTING PURPOSES).
     * This constructor takes a predefined (mines NOT randomly assigned) 'mineBoard' matrix as a parameter. Using the parameter, the xtiles, ytiles, mineNum are found and set. The playerBoard matrix and tilesUnrevealed
     * is set afterwards. Note: The parameter should be a 'n x n' or 'm x n' matrix, where n,m are positive, non-zero integers.
     * Concept of having a pre-define int[][] mineBoard as a parameter referenced from https://www2.cs.arizona.edu/~mercer/Projects/MineSweeper.pdf
     * @param customMineBoard A 2d integer array that represents a non-random mine assigned mineBoard
     * @throws IllegalArgumentException To indicate that parameter values are invalid to setup a game of Minesweeper
     */
    public gameModel(int[][] customMineBoard) {
        //Figures out and store the number of tiles per row of parameter 'customMineBoard'
        int rowNum= customMineBoard.length;
        //Figures out and store the number of tiles per column of parameter 'customMineBoard'
        int colNum= customMineBoard[0].length;
        //Figures out and store the number of mines inside the 2d array parameter 'customMineBoard'
        int minesCounted=0;
        for(int i=0;i<rowNum;i++) {
            for(int j=0;j<colNum;j++) {
                if(customMineBoard[i][j]== gameModel.mine) {
                    minesCounted++;
                }
            }
        }
        //From above, test if rowNum,colNum and minesCounted variables are valid enough to setup a minesweeper board
        if(!isValidGameSetup(rowNum, colNum, minesCounted)) {
            throw new IllegalArgumentException("Invalid board size and mine amounts");
        } else {
            this.xtiles=rowNum;
            this.ytiles=colNum;
            this.mineNum=minesCounted;
            this.resetTilesUnrevealed();
            //Makes the field variable 'mineBoard' the same as parameter 'customMineBoard'
            this.mineBoard=customMineBoard;
            this.setPlayerBoardSize(xtiles,ytiles);
            //this.playerBoard=new int[rowNum][colNum];
            this.resetPlayerBoard();
        }
    }

    /**
     * Checks if the board size is valid and the mines set for the board is valid
     * @param xtiles The number of tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines
     * @return true or false, depending on the values of the parameters
     */
    public static boolean isValidGameSetup(int xtiles, int ytiles, int mineNum) {
        //Does a check on each parameter separately to ensure that EACH are bigger or equal to zero.
        if(xtiles<1 || ytiles<1 || mineNum<1) {
            return false;
        }
        //Does a check on combination of the parameters to ensure that amount of mines is not bigger than the board size.
        if(xtiles*ytiles<mineNum) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if a given position is within the bounds of a mineBoard/playerBoard
     * Code taken from reference: https://www.geeksforgeeks.org/cpp-implementation-minesweeper-game/?ref=rp
     * @param row The row position within the playerBoard matrix
     * @param col The column position within the playerBoard matrix
     * @return true or false, depending on whether the parameters are out of bounds of the playerBoard matrix, using xtiles and ytiles as upper bounds
     */
    public boolean isValidPosition(int row, int col) {
        if((row>=0 && row<this.xtiles) && (col>=0 && col<this.ytiles)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method sets the tilesUnrevealed value to become the size of the board
     */
    public void resetTilesUnrevealed() {
        this.setTilesUnrevealed(this.xtiles*this.ytiles);
        //this.tilesUnrevealed=this.xtiles*this.ytiles;
    }
    /**
     * This method sets every-single element within the playerBoard array to an integer value (unrevealed)
     */
    public void resetPlayerBoard() {
        for(int i=0;i<this.playerBoard.length;i++) {
            for(int j=0;j<this.playerBoard[0].length;j++) {
                this.playerBoard[i][j]=unrevealed;
            }
        }
    }
    /**
     * This method sets every-single element within the mineBoard array to an integer value (unrevealed).
     */
    public void resetMineBoard() {
        for(int i=0;i<this.mineBoard.length;i++) {
            for(int j=0;j<this.mineBoard[0].length;j++) {
                this.mineBoard[i][j]=unrevealed;
            }
        }
    }

    /**
     * Through a Random object and a while loop, this method updates random elements within the mineBoard array to an integer value (mine)
     * It needs a while loop just in case the randomly chosen element within the mineBoard array has already been changed to the integer value (mine)
     * Code is used/referenced from: https://stackoverflow.com/questions/35119330/putting-a-number-at-random-spots-in-2d-array
     */
    public void assignMines() {
        Random rand = new Random();
        int mineCount = 0;
        while (mineCount < this.mineNum)
        {
            int xrand = rand.nextInt(this.mineBoard.length);
            int yrand = rand.nextInt(this.mineBoard[0].length);

            if(this.mineBoard[xrand][yrand] != mine) {
                this.mineBoard[xrand][yrand]=mine;
                mineCount++;
            }
        }
    }

    /**
     * This method calculates the number of mines adjacent to current tile.
     * It uses the mineBoard matrix to figure out the number of adjacent mines for a given row and column
     * Code is adapted/referenced from: http://www.eecs.qmul.ac.uk/~mmh/ItP/resources/MineSweeper/Notes.html
     * @param row The row position within the playerBoard matrix
     * @param col The column position within the playerBoard matrix
     * @return neighbourMines The number of mines adjacent to current tile
     */
    public int calculateTileNumber(int row, int col) {
        int neighbourMines=0;
        if(this.mineBoard[row][col]==mine) {
            return mine;
        } else {
            for(int i=row-1;i<row+2;i++) {
                for(int j=col-1;j<col+2;j++) {
                    if(isValidPosition(i,j)) {
                        if(this.mineBoard[i][j]==mine) {
                            neighbourMines=neighbourMines+1;
                        }
                    }
                }
            }
        }
        return neighbourMines;
    }

    /**
     * This method updates the value at a row,col position within the playerBoard matrix. Then updates the tilesUnrevealed number.
     * @param row The row position within the playerBoard matrix
     * @param col The column position within the playerBoard matrix
     */
    public void reveal(int row, int col) {
        //Check if the tile of the given row/column is revealed already. If so, this method will do nothing.
        if(this.getPlayerBoard()[row][col] == gameModel.unrevealed) {
            //If UNrevealed, the position at the given row/column within the playerBoard[][] becomes updated according to the method 'calculateTileNumber()'
            this.playerBoard[row][col]=this.calculateTileNumber(row, col);
            //After the update, now checks if the tile at the given row/column is NOT a mine. If it's NOT a mine, decrement 'tilesUnrevealed' value by 1.
            if(this.playerBoard[row][col]!= gameModel.mine) {
                this.tilesUnrevealed--;
            }
        }
    }

    /**
     * This method recursively reveals other adjacent tiles if current tile value is 0.
     * Recursive part adapted/referenced from: https://github.com/domingodavid/minesweeper/blob/master/Minesweeper/src/Board.java
     * Added 4 more recursions by checking tiles diagonally adjacent to chosen tile
     * @param row The row position within the playerBoard matrix
     * @param col The column position within the playerBoard matrix
     */
    public void revealNeighbours(int row, int col) {
        //Check if the row,col inputted is out of the board bounds
        if(!this.isValidPosition(row, col)) {
            return;
        }

        //Checks if the tile at position row,col has ALREADY BEEN REVEALED OR ALREADY HAS FLAG
        if(!(this.playerBoard[row][col]==unrevealed || this.isFlagged(row, col)) ) {
            return;
        } else {
            this.reveal(row, col);
            if(this.playerBoard[row][col]==0 ) {
                //Kept revealing left positions
                revealNeighbours(row,col-1);
                //Keep revealing up positions
                revealNeighbours(row-1,col);
                //Keep revealing down positions
                revealNeighbours(row+1,col);
                //Keep revealing right positions
                revealNeighbours(row,col+1);
                //Keep revealing up+left positions
                revealNeighbours(row-1,col-1);
                //keep revealing up+right positions
                revealNeighbours(row-1,col+1);
                //keep revealing down+left positions
                revealNeighbours(row+1,col-1);
                //keep revealing down+right positions
                revealNeighbours(row+1,col+1);
            }
        }
    }

    /**
     * This method flags(or un-flags) a tile at a given row/column. If tile is already flagged, set tile's value to '-2' (unrevealed).
     * If it is NOT flagged, flag given tile by setting the it's value to '-3' (flagged).
     * @param row The selected row position of the playerBoard[][]
     * @param col The selected column position of the playerBoard[][]
     */
    public void toggleFlagAtTile(int row,int col) {
        //First check if given row/col is within the boundaries of the playerBoard[][]
        if(isValidPosition(row, col)) {
            //Check if the tile at the given row/col is NOT flagged. If so, flag the tile. OTHERWISE, make tile unrevealed.
            if(!this.isFlagged(row, col)){
                this.playerBoard[row][col]= gameModel.flag;
            } else if (this.isFlagged(row, col)) {
                this.playerBoard[row][col]= gameModel.unrevealed;
            }
        }
    }

    /**
     * This method checks if a given tile at a row/column is flagged or not
     * @param row The selected row position of the playerBoard[][]
     * @param col The selected column position of the playerBoard[][]
     * @return true or false depending on the value at the given row/col position of the playerBoard[][]
     */
    public boolean isFlagged(int row,int col) {
        return this.playerBoard[row][col] == gameModel.flag;
    }

    /**
     * This method goes through every single element within the playerBoard matrix and calculates the number of adjacent mines for each tile.
     */
    public void showAnswer() {
        for(int i=0;i<this.playerBoard.length;i++) {
            for(int j=0;j<this.playerBoard[0].length;j++) {
                this.playerBoard[i][j]=this.calculateTileNumber(i, j);
            }
        }
    }

    /**
     * This method checks if the game is lost by seeing if the player's board has a mine(-2) at a certain position
     * @param row The row position within the playerBoard matrix
     * @param col The column position within a playerBoard matrix
     * @return true or false, depending on whether the lost condition is satisfied
     */
    public boolean isGameLost(int row, int col) {
        return this.playerBoard[row][col] == mine;
    }

    /**
     * This method checks if the game is won by checking if the tilesUnrevealed=mineNumber
     * @return true or false, depending on whether the win condition is satisfied
     */
    public boolean isGameWon() {
        return this.tilesUnrevealed == this.mineNum;

    }

    /**
     *  Getter for xtiles
     *  @return The number of tiles per row
     */
    public int getXtiles() {
        return xtiles;
    }

    /**
     *  Getter for ytiles
     *  @return The number of tiles per column
     */
    public int getYtiles() {
        return ytiles;
    }

    /**
     * Getter for mineNum
     * @return The number of mines
     */
    public int getMineNum() {
        return mineNum;
    }

    /**
     * Getter for tilesUnrevealed
     * @return tilesUnrevealed The number of tiles that are not revealed
     */
    public int getTilesUnrevealed() {
        return tilesUnrevealed;
    }

    /**
     * Getter for playerBoard array
     * @return The two dimensional integer array of the current state of the playerBoard
     */
    public int[][] getPlayerBoard(){
        return this.playerBoard;
    }

    /**
     * Getter for mineBoard array
     * @return The two dimensional integer array of the mineBoard
     */
    public int[][] getMineBoard() {
        return mineBoard;
    }

    /**
     * Setter for playerBoard array
     * @param newxtiles The new number of tiles per row
     * @param newytiles The new number of tiles per column
     */
    public void setPlayerBoardSize(int newxtiles, int newytiles) {
        this.playerBoard=new int[newxtiles][newytiles];
    }

    /**
     * Setter for mineBoard array
     * @param newxtiles The new number of tiles per row
     * @param newytiles The new number of tiles per column
     */
    public void setMineBoardSize(int newxtiles, int newytiles) {
        this.mineBoard=new int[newxtiles][newytiles];
    }

    /**
     * Setter for tilesUnrevealed. It will do nothing if the parameter is bigger than the board size.
     * This is mainly a helper function for the method 'resetTilesUnrevealed()' method.
     * @param tilesUnrevealed The new number of tiles that are not revealed
     */
    public void setTilesUnrevealed(int tilesUnrevealed) {
        if(this.xtiles*this.ytiles>=tilesUnrevealed) {
            this.tilesUnrevealed=tilesUnrevealed;
        }
    }

    /**
     * Display's the mineBoard on the console and shows where all the mines are
     **/
    public void displayMineBoard() {
        System.out.println("MineBoard");
        for(int i=0;i<this.xtiles;i++) {
            for(int j=0;j<this.ytiles;j++) {

                if(this.mineBoard[i][j]==-2) {
                    System.out.print("M | ");
                } else {
                    System.out.print("# | ");
                }
            }
            System.out.println(" ");
        }
    }

    /**
     * Display's the playerBoard on the console and shows the current state of the board (what the player will always get to see)
     **/
    public void displayPlayerBoard() {
        System.out.println("playerBoard");
        for(int i=0;i<this.xtiles;i++) {
            for(int j=0;j<this.ytiles;j++) {
                if(this.playerBoard[i][j]== gameModel.unrevealed) {
                    System.out.print("?"+" | ");
                } else {
                    System.out.print(this.playerBoard[i][j]+" | ");
                }
            }
            System.out.println(" ");
        }
    }

}
