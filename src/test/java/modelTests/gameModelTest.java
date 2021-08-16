package modelTests;

import models.gameModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
/**
 * This class represents all the test that was conducted on the 3 'sample' gameModel objects.
 * Testing consisted of ensuring no out of bounds coordinates was entered, when trying to reveal a certain tile.
 * Testing consisted of ensuring parameters entered to set up a game (tiles per row, tiles per column, amount of mines) was valid.
 * Various other test was conducted as shown below
 * @author JXC135
 */

class gameModelTest {
    private gameModel sample0,sample1,sample2;

    @BeforeEach
    public void init() {
        int[][] customMineBoard0 = {
                {-2,0,-2},
                {0,-2,0},
                {0,0,0}
        };
        sample0=new gameModel(customMineBoard0);

        int[][] customMineBoard1 = {
                {-2,-2,0,0},
                {0,0,0,-2},
                {0,0,0,0}
        };
        sample2=new gameModel(customMineBoard1);

        sample1=new gameModel(2,1,1);
    }

    //Tests for gameModel object 'sample0'

    //Testing getXtiles() method
    @Test
    public void sample0Test1() {
        int expectedValue = 3;
        int actualValue = sample0.getXtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getYtiles() method
    @Test
    public void sample0Test2() {
        int expectedValue = 3;
        int actualValue = sample0.getYtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getMineNum() method
    @Test
    public void sample0Test3() {
        int expectedValue = 3;
        int actualValue = sample0.getMineNum();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getTilesUnrevealed() method
    @Test
    public void sample0Test4() {
        int expectedValue = 3*3;
        int actualValue = sample0.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setTilesUnrevealed() method (Case: invalid parameter value 50)
    @Test
    public void sample0Test5(){
        int expectedValue = 3*3;
        sample0.setTilesUnrevealed(50);
        int actualValue = sample0.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setTilesUnrevealed() method (Case: valid parameter value 5)
    @Test
    public void sample0Test6(){
        int expectedValue = 5;
        sample0.setTilesUnrevealed(5);
        int actualValue = sample0.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing resetTilesUnrevealed() method
    @Test
    public void sample0Test7(){
        int expectedValue = 3*3;
        sample0.resetTilesUnrevealed();
        int actualValue = sample0.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setPlayerBoardSize() method
    @Test
    public void sample0Test8() {
        //Check if:
        //(1) playerBoard[][] is NOT null
        //(2) playerBoard[][] width/height are same as xtiles/ytiles respectively
        //(3) all elements in playerBoard[][] are set to -1 (unrevealed)
        assertTrue(sample0.getPlayerBoard()!=null);
        assertTrue(sample0.getPlayerBoard().length==sample0.getXtiles());
        assertTrue(sample0.getPlayerBoard()[0].length==sample0.getYtiles());
        //Create an expected int[][] matrix with expected values
        int[][] expectedPlayerBoard = new int[sample0.getXtiles()][sample0.getYtiles()];
        for(int i=0;i<expectedPlayerBoard.length;i++) {
            for(int j=0;j<expectedPlayerBoard[0].length;j++) {
                expectedPlayerBoard[i][j]=-1;
            }
        }
        //Compared two int[][] arrays using Arrays.deepEquals method
        assertTrue(Arrays.deepEquals(expectedPlayerBoard,sample0.getPlayerBoard()));
    }

    //Testing validGameSetup() method for sample0
    @Test
    public void sample0Test9() {
        assertTrue(gameModel.isValidGameSetup(sample0.getXtiles(),sample0.getYtiles(),sample0.getMineNum()));
    }

    //Testing isValidPosition() method on sample0 (Case: invalid positions)
    @Test
    public void sample0Test10() {
        //Test 3 points outside the north-west corner of mineBoard matrix
        assertFalse(sample0.isValidPosition(0, -1));
        assertFalse(sample0.isValidPosition(-1, 0));
        assertFalse(sample0.isValidPosition(-1, -1));
        //Test 3 points outside the north-east corner of mineBoard matrix
        assertFalse(sample0.isValidPosition(0, 3));
        assertFalse(sample0.isValidPosition(-1, 2));
        assertFalse(sample0.isValidPosition(-1, 3));
        //Test 3 points outside the south-west corner of mineBoard matrix
        assertFalse(sample0.isValidPosition(2, -1));
        assertFalse(sample0.isValidPosition(3, 0));
        assertFalse(sample0.isValidPosition(3, -1));
        //Test 3 points outside the south-east corner of mineBoard matrix
        assertFalse(sample0.isValidPosition(2, 3));
        assertFalse(sample0.isValidPosition(3, 2));
        assertFalse(sample0.isValidPosition(3, 3));

    }

    //Testing isValidPosition() method on sample0 (Case: valid positions 0,0 , 0,2 , 2,0 , 2,2)
    @Test
    public void sample0Test11() {
        //Test using the north-west corner
        assertTrue(sample0.isValidPosition(0, 0));
        //Test using the north-east corner
        assertTrue(sample0.isValidPosition(0, 2));
        //Test using the south-west corner
        assertTrue(sample0.isValidPosition(2, 0));
        //Test using the south-east corner
        assertTrue(sample0.isValidPosition(2, 2));
    }

    //Testing calculateTileNumber() method on every-single element inside the matrices of sample0
    //Test to see if the method returns the CORRECT number of adjacent mines to chosen/current tile
    @Test
    public void sample0Test12() {
        //First row
        assertEquals(-2,sample0.calculateTileNumber(0, 0));
        assertEquals(3,sample0.calculateTileNumber(0, 1));
        assertEquals(-2,sample0.calculateTileNumber(0, 2));
        //Second row
        assertEquals(2,sample0.calculateTileNumber(1, 0));
        assertEquals(-2,sample0.calculateTileNumber(1, 1));
        assertEquals(2,sample0.calculateTileNumber(1, 2));
        //Third row
        assertEquals(1,sample0.calculateTileNumber(2, 0));
        assertEquals(1,sample0.calculateTileNumber(2, 1));
        assertEquals(1,sample0.calculateTileNumber(2, 2));
    }

    //Testing calculateTileNumber() method (Case: invalid position 3,3)
    @Test
    public void sample0Test13() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample0.calculateTileNumber(3, 3);
        });
    }

    //Testing calculateTileNumber() method (Case: invalid position -1,-1)
    @Test
    public void sample0Test14() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample0.calculateTileNumber(-1, -1);
        });
    }

    //Testing reveal() method (Case: valid position 2,0)
    //Testing if correct number is returned for position 2,0 (via calculateTileNumber() method)
    @Test
    public void sample0Test15() {
        int playerBoardValueBefore = sample0.getPlayerBoard()[2][0];
        int tilesUnrevealedBefore = sample0.getTilesUnrevealed();

        int calculatedTileNumber = sample0.calculateTileNumber(2, 0);
        assertEquals(1,calculatedTileNumber);
        sample0.reveal(2, 0);

        int playerBoardValueAfter=sample0.getPlayerBoard()[2][0];
        int tilesUnrevealedAfter=sample0.getTilesUnrevealed();

        assertTrue(playerBoardValueBefore != playerBoardValueAfter);
        assertTrue(tilesUnrevealedBefore != tilesUnrevealedAfter);
    }

    //Testing reveal() method twice to ensure tilesUnrevealed number is correct
    //Testing if playerBoard[][] correctly updates position 0,1
    @Test
    public void sample0Test16() {
        sample0.reveal(0,1);
        sample0.reveal(0,1);
        int expectedTilesUnrevealed = 8;
        int expectedPlayerBoardValue = 3;
        assertEquals(expectedPlayerBoardValue,sample0.getPlayerBoard()[0][1]);
        assertEquals(expectedTilesUnrevealed,sample0.getTilesUnrevealed());
    }

    //Testing reveal() method (Case: 2 valid positions 0,1 and 2,0)
    //Testing if playerBoard[][] correctly updates position 0,1 and 2,0
    @Test
    public void sample0Test17() {
        sample0.reveal(0,1);
        sample0.reveal(2,0);
        int expectedTilesUnrevealed=7;
        int expectedPlayerBoardValueTop = 3;
        int expectedPlayerBoardValueBottomLeft = 1;
        assertEquals(expectedPlayerBoardValueTop,sample0.getPlayerBoard()[0][1]);
        assertEquals(expectedPlayerBoardValueBottomLeft,sample0.getPlayerBoard()[2][0]);
        assertEquals(expectedTilesUnrevealed,sample0.getTilesUnrevealed());
    }

    //Testing reveal() method. (Case: invalid position -1,-1)
    //Note: Also testing if correct number is returned (via calculateTileNumber() method)
    @Test
    public void sample0Test18() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample0.reveal(-1, -1);
        });
    }

    //Testing isGameWon() method. (Case: revealed 3 positions, expect game is still not won)
    @Test
    public void sample0Test19(){
        sample0.reveal(2,1);
        boolean expectedAnswer0 = false;
        assertEquals(expectedAnswer0,sample0.isGameWon());
        sample0.reveal(2,2);
        boolean expectedAnswer1 = false;
        assertEquals(expectedAnswer1,sample0.isGameWon());
        sample0.reveal(1,2);
        boolean expectedAnswer2 = false;
        assertEquals(expectedAnswer2,sample0.isGameWon());
    }

    //Testing isGameWon() method. (Case: revealed 6 positions, expect game is won)
    @Test
    public void sample0Test20() {
        sample0.reveal(0,1);
        sample0.reveal(1,0);
        sample0.reveal(1,2);
        sample0.reveal(2,0);
        sample0.reveal(2,1);
        sample0.reveal(2,2);
        boolean expectedAnswer0 = true;
        assertEquals(expectedAnswer0,sample0.isGameWon());
    }

    //Testing isGameLost() method. (Case: choosing unrevealed non-mine position 0,1 , expect game not lost)
    @Test
    public void sample0Test21() {
        boolean expectedAnswer0 = false;
        assertEquals(expectedAnswer0,sample0.isGameLost(0, 1));
    }

    //Testing isGameLost() method. (Case: revealing non-mine position 0,1 , expect game not lost)
    @Test
    public void sample0Test22() {
        boolean expectedAnswer0 = false;
        sample0.reveal(0,1);
        assertEquals(expectedAnswer0,sample0.isGameLost(0, 1));
    }

    //Testing isGameLost() method. (Case: revealing mine position 0,0 , expect game is lost)
    @Test
    public void sample0Test23() {
        boolean expectedAnswer0 = true;
        sample0.reveal(0,0);
        assertEquals(expectedAnswer0,sample0.isGameLost(0, 0));
    }

    //Testing isGameLost() method. (Case: invalid position 3,3)
    @Test
    public void sample0Test24() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample0.isGameLost(3, 3);
        });
    }

    //Testing showAnswer() method.
    @Test
    public void sample0Test25() {
        int expectedTilesUnrevealed=9;
        //First row of playerBoard[][]
        int expectedValue00=-2;
        int expectedValue01=3;
        int expectedValue02=-2;
        //Second row of playerBoard[][]
        int expectedValue10=2;
        int expectedValue11=-2;
        int expectedValue12=2;
        //Third row of playerBoard[][]
        int expectedValue20=1;
        int expectedValue21=1;
        int expectedValue22=1;

        sample0.showAnswer();

        assertEquals(expectedTilesUnrevealed,sample0.getTilesUnrevealed());

        assertEquals(expectedValue00,sample0.getPlayerBoard()[0][0]);
        assertEquals(expectedValue01,sample0.getPlayerBoard()[0][1]);
        assertEquals(expectedValue02,sample0.getPlayerBoard()[0][2]);

        assertEquals(expectedValue10,sample0.getPlayerBoard()[1][0]);
        assertEquals(expectedValue11,sample0.getPlayerBoard()[1][1]);
        assertEquals(expectedValue12,sample0.getPlayerBoard()[1][2]);

        assertEquals(expectedValue20,sample0.getPlayerBoard()[2][0]);
        assertEquals(expectedValue21,sample0.getPlayerBoard()[2][1]);
        assertEquals(expectedValue22,sample0.getPlayerBoard()[2][2]);

    }

    /*
       {-2,0,-2},
       {0,-2,0},
       {0,0,0}
     */
    //Testing revealNeighbour() method. (Case: chosen position 2,0)
    //Expect playerBoard[][] to look like this (? means unrevealed):
    // {? ? ?}
    // {? ? ?}
    // {1 ? ?}
    @Test
    public void sample0Test26() {
        sample0.revealNeighbours(2, 0);
        //Expect that '1' is the value at position 2,0 inside playerBoard[][]
        assertEquals(1,sample0.getPlayerBoard()[2][0]);
        //Expect that the number of hidden tiles is '8'
        assertEquals(8,sample0.getTilesUnrevealed());
    }
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //Tests for gameModel object 'sample1'

    //Testing setUpGame() method (with invalid parameters 1,1,15)
    @Test
    public void sample1Test0() {
        assertThrows(IllegalArgumentException.class, () -> {
            sample1.setUpGame(1,1,15);
        });
    }

    //Testing getXtiles() method
    @Test
    public void sample1Test1() {
        int expectedValue = 2;
        int actualValue = sample1.getXtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getYtiles() method
    @Test
    public void sample1Test3() {
        int expectedValue = 1;
        int actualValue = sample1.getYtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getMineNum() method
    @Test
    public void sample1Test5() {
        int expectedValue = 1;
        int actualValue = sample1.getMineNum();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }


    //Testing getTilesUnrevealed() method
    @Test
    public void sample1Test7() {
        int expectedValue = 2;
        int actualValue = sample1.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setTilesUnrevealed() method
    @Test
    public void sample1Test8() {
        int expectedValue = 2;
        sample1.setTilesUnrevealed(50);
        int actualValue = sample1.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing resetTilesUnrevealed() method
    @Test
    public void sample1Test9() {
        int expectedValue = 2;
        sample1.resetTilesUnrevealed();
        int actualValue = sample1.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing validGameSetup() method
    @Test
    public void sample1Test10() {
        assertTrue(gameModel.isValidGameSetup(sample1.getXtiles(),sample1.getYtiles(),sample1.getMineNum()));
    }

    //Testing isValidPosition() method (Case: invalid position 0,1)
    @Test
    public void sample1Test11() {
        assertFalse(sample1.isValidPosition(0, 1));
    }

    //Testing isValidPosition() method (Case: valid position 0,0)
    @Test
    public void sample1Test12() {
        assertTrue(sample1.isValidPosition(0, 0));
    }

    //Testing setPlayerBoardSize() method
    @Test
    public void sample1Test13() {
        //Check if:
        //(1) playerBoard[][] is NOT null
        //(2) playerBoard[][] width/height are same as xtiles/ytiles respectively
        //(3) all elements in playerBoard[][] are set to -1 (unrevealed)
        assertTrue(sample1.getPlayerBoard()!=null);
        assertTrue(sample1.getPlayerBoard().length==sample1.getXtiles());
        assertTrue(sample1.getPlayerBoard()[0].length==sample1.getYtiles());
        //Create an expected int[][] matrix with expected values
        int[][] expectedPlayerBoard = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<expectedPlayerBoard.length;i++) {
            for(int j=0;j<expectedPlayerBoard[0].length;j++) {
                expectedPlayerBoard[i][j]=-1;
            }
        }
        //Compared two int[][] arrays using Arrays.deepEquals method
        assertTrue(Arrays.deepEquals(expectedPlayerBoard,sample1.getPlayerBoard()));
    }

    //Testing setMineBoardSize() method
    @Test
    public void sample1Test14() {
        //Check if (1) mineBoard[][] is NOT null
        //(2) mineBoard[][] width/height are same as Xtiles/Ytiles respectively
        //(3) all elements in mineBoard[][] are set to -1 (unrevealed)
        assertTrue(sample1.getMineBoard()!=null);
        assertTrue(sample1.getMineBoard().length==sample1.getXtiles());
        assertTrue(sample1.getMineBoard()[0].length==sample1.getYtiles());
        //Create an expected int[][] matrix with expected values
        int[][] expectedMineBoard = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<expectedMineBoard.length;i++) {
            for(int j=0;j<expectedMineBoard[0].length;j++) {
                expectedMineBoard[i][j]=-1;
            }
        }
        //Testing the resetMineBoard() method INSIDE of setMineBoardSize() method
        assertTrue(Arrays.deepEquals(expectedMineBoard,sample1.getPlayerBoard()));
    }

    //Testing assignMines() method. Testing if correct amount of mines has been allocated into the mineBoard[][]
    @Test
    public void sample1Test15() {
        int expectedAssignedMines=sample1.getMineNum();
        //initial set to 0. Will increment to get actual number of assigned mines
        int actualAssignedMines=0;
        for(int i=0;i<sample1.getMineBoard().length;i++) {
            for(int j=0;j<sample1.getMineBoard()[0].length;j++) {
                if(sample1.getMineBoard()[i][j]==gameModel.mine) {
                    actualAssignedMines++;
                }
            }
        }
        assertEquals(actualAssignedMines,expectedAssignedMines);
    }

    //Testing resetMineBoard() method. Testing when AFTER assignMines() is called,
    //will resetMineBoard() make all elements in mineBoard[][] be -1 (unrevealed)
    @Test
    public void sample1Test16() {
        sample1.resetMineBoard();
        int[][] actualMineBoard=sample1.getMineBoard();

        int[][] expectedMineBoard = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<expectedMineBoard.length;i++) {
            for(int j=0;j<expectedMineBoard[0].length;j++) {
                expectedMineBoard[i][j]=-1;
            }
        }

        assertTrue(Arrays.deepEquals(actualMineBoard,expectedMineBoard));
    }

    //Testing if resetMineBoard() and assignMines() actually RANDOMLY assigns mines AFTER each iteration.
    //This test randomly assigns mines for the mineBoard[][] doing 5 iterations. Could test randomness with more iterations though.
    @Test
    public void sample1Test17() {
        int[][] randomMineBoard0 = sample1.getMineBoard();

        sample1.resetMineBoard();
        sample1.assignMines();
        int[][] randomMineBoard1 = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<sample1.getXtiles();i++) {
            for(int j=0;j<sample1.getYtiles();j++) {
                randomMineBoard1[i][j]=sample1.getMineBoard()[i][j];
            }
        }

        sample1.resetMineBoard();
        sample1.assignMines();
        int[][] randomMineBoard2 = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<sample1.getXtiles();i++) {
            for(int j=0;j<sample1.getYtiles();j++) {
                randomMineBoard2[i][j]=sample1.getMineBoard()[i][j];
            }
        }
        sample1.resetMineBoard();
        sample1.assignMines();
        int[][] randomMineBoard3 = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<sample1.getXtiles();i++) {
            for(int j=0;j<sample1.getYtiles();j++) {
                randomMineBoard3[i][j]=sample1.getMineBoard()[i][j];
            }
        }

        sample1.resetMineBoard();
        sample1.assignMines();
        int[][] randomMineBoard4 = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<sample1.getXtiles();i++) {
            for(int j=0;j<sample1.getYtiles();j++) {
                randomMineBoard4[i][j]=sample1.getMineBoard()[i][j];
            }
        }

        sample1.resetMineBoard();
        sample1.assignMines();
        int[][] randomMineBoard5 = new int[sample1.getXtiles()][sample1.getYtiles()];
        for(int i=0;i<sample1.getXtiles();i++) {
            for(int j=0;j<sample1.getYtiles();j++) {
                randomMineBoard5[i][j]=sample1.getMineBoard()[i][j];
            }
        }

        boolean compareBoards01 = Arrays.deepEquals(randomMineBoard0, randomMineBoard1);
        boolean compareBoards12 = Arrays.deepEquals(randomMineBoard1, randomMineBoard2);
        boolean compareBoards23 = Arrays.deepEquals(randomMineBoard2, randomMineBoard3);
        boolean compareBoards34 = Arrays.deepEquals(randomMineBoard3, randomMineBoard4);
        boolean compareBoards45 = Arrays.deepEquals(randomMineBoard4, randomMineBoard5);

        assertFalse(compareBoards01&&compareBoards12&&compareBoards23&&compareBoards34&&compareBoards45);
    }
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    //Tests for gameModel object 'sample2'

    //Testing getXtiles() method
    @Test
    public void sample2Test1() {
        int expectedValue = 3;
        int actualValue = sample2.getXtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getYtiles() method
    @Test
    public void sample2Test2() {
        int expectedValue = 4;
        int actualValue = sample2.getYtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getMineNum() method
    @Test
    public void sample2Test3() {
        int expectedValue = 3;
        int actualValue = sample2.getMineNum();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getTilesUnrevealed() method
    @Test
    public void sample2Test4() {
        int expectedValue = 3*4;
        int actualValue = sample2.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setTilesUnrevealed() method (Case: invalid parameter value 33)
    @Test
    public void sample2Test5(){
        int expectedValue = 3*4;
        sample2.setTilesUnrevealed(33);
        int actualValue = sample2.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setTilesUnrevealed() method (Case: valid parameter value 8)
    @Test
    public void sample2Test6(){
        int expectedValue = 8;
        sample2.setTilesUnrevealed(8);
        int actualValue = sample2.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing resetTilesUnrevealed() method
    @Test
    public void sample2Test7(){
        int expectedValue = 3*4;
        sample2.resetTilesUnrevealed();
        int actualValue = sample2.getTilesUnrevealed();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing setPlayerBoardSize() method
    @Test
    public void sample2Test8() {
        //Check if:
        //(1) playerBoard[][] is NOT null
        //(2) playerBoard[][] width/height are same as xtiles/ytiles respectively
        //(3) all elements in playerBoard[][] are set to -1 (unrevealed)
        assertTrue(sample2.getPlayerBoard()!=null);
        assertTrue(sample2.getPlayerBoard().length==sample2.getXtiles());
        assertTrue(sample2.getPlayerBoard()[0].length==sample2.getYtiles());
        //Create an expected int[][] matrix with expected values
        int[][] expectedPlayerBoard = new int[sample2.getXtiles()][sample2.getYtiles()];
        for(int i=0;i<expectedPlayerBoard.length;i++) {
            for(int j=0;j<expectedPlayerBoard[0].length;j++) {
                expectedPlayerBoard[i][j]=-1;
            }
        }
        //Compared two int[][] arrays using Arrays.deepEquals method
        assertTrue(Arrays.deepEquals(expectedPlayerBoard,sample2.getPlayerBoard()));
    }

    //Testing validGameSetup() method for sample2
    @Test
    public void sample2Test9() {
        assertTrue(gameModel.isValidGameSetup(sample2.getXtiles(),sample2.getYtiles(),sample2.getMineNum()));
    }

    //Testing isValidPosition() method on sample2 (Case: invalid positions)
    @Test
    public void sample2Test10() {
        //Test 3 points outside the north-west corner of mineBoard matrix
        assertFalse(sample2.isValidPosition(-1,0));
        assertFalse(sample2.isValidPosition(0,-1));
        assertFalse(sample2.isValidPosition(-1,-1));
        //Test 3 points outside the north-east corner of mineBoard matrix
        assertFalse(sample2.isValidPosition(0,4));
        assertFalse(sample2.isValidPosition(-1,3));
        assertFalse(sample2.isValidPosition(-1,4));
        //Test 3 points outside the south-west corner of mineBoard matrix
        assertFalse(sample2.isValidPosition(2,-1));
        assertFalse(sample2.isValidPosition(3,0));
        assertFalse(sample2.isValidPosition(3,-1));
        //Test 3 points outside the south-east corner of mineBoard matrix
        assertFalse(sample2.isValidPosition(2,4));
        assertFalse(sample2.isValidPosition(3,3));
        assertFalse(sample2.isValidPosition(3,4));
    }

    //Testing isValidPosition() method on sample0 (Case: valid positions 0,0 , 0,3 , 2,0 , 2,3)
    @Test
    public void sample2Test11() {
        //Test using the north-west corner
        assertTrue(sample2.isValidPosition(0, 0));
        //Test using the north-east corner
        assertTrue(sample2.isValidPosition(0, 3));
        //Test using the south-west corner
        assertTrue(sample2.isValidPosition(2, 0));
        //Test using the south-east corner
        assertTrue(sample2.isValidPosition(2, 3));
    }

    //Testing calculateTileNumber() method on every-single element inside the matrices of sample2
    //Test to see if the method returns the CORRECT number of adjacent mines to chosen/current tile
    @Test
    public void sample2Test12() {
        //First row
        assertEquals(-2,sample2.calculateTileNumber(0, 0));
        assertEquals(-2,sample2.calculateTileNumber(0, 1));
        assertEquals(2,sample2.calculateTileNumber(0, 2));
        assertEquals(1,sample2.calculateTileNumber(0, 3));
        //Second row
        assertEquals(2,sample2.calculateTileNumber(1, 0));
        assertEquals(2,sample2.calculateTileNumber(1, 1));
        assertEquals(2,sample2.calculateTileNumber(1, 2));
        assertEquals(-2,sample2.calculateTileNumber(1, 3));
        //Third row
        assertEquals(0,sample2.calculateTileNumber(2, 0));
        assertEquals(0,sample2.calculateTileNumber(2, 1));
        assertEquals(1,sample2.calculateTileNumber(2, 2));
        assertEquals(1,sample2.calculateTileNumber(2, 3));

    }

    //Testing calculateTileNumber() method (Case: invalid position 3,3)
    @Test
    public void sample2Test13() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample2.calculateTileNumber(0, 4);
        });
    }

    //Testing calculateTileNumber() method (Case: invalid position -1,-1)
    @Test
    public void sample2Test14() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample2.calculateTileNumber(-1, -1);
        });
    }

    //Testing reveal() method (Case: valid position 2,0)
    //Testing if correct number is returned for position 2,0 (via calculateTileNumber() method)
    @Test
    public void sample2Test15() {
        int playerBoardValueBefore = sample2.getPlayerBoard()[2][0];
        int tilesUnrevealedBefore = sample2.getTilesUnrevealed();

        int calculatedTileNumber = sample2.calculateTileNumber(2, 0);
        assertEquals(0,calculatedTileNumber);
        sample2.reveal(2, 0);

        int playerBoardValueAfter=sample2.getPlayerBoard()[2][0];
        int tilesUnrevealedAfter=sample2.getTilesUnrevealed();

        assertTrue(playerBoardValueBefore != playerBoardValueAfter);
        assertTrue(tilesUnrevealedBefore != tilesUnrevealedAfter);
    }

    //Testing reveal() method twice to ensure tilesUnrevealed number is correct
    //Testing if playerBoard[][] correctly updates position 0,2
    @Test
    public void sample2Test16() {
        sample2.reveal(0,2);
        sample2.reveal(0,2);
        int expectedTilesUnrevealed = 11;
        int expectedPlayerBoardValue = 2;
        assertEquals(expectedPlayerBoardValue,sample2.getPlayerBoard()[0][2]);
        assertEquals(expectedTilesUnrevealed,sample2.getTilesUnrevealed());
    }

    //Testing reveal() method (Case: 2 valid positions 0,1 and 2,0)
    //Testing if playerBoard[][] correctly updates position 0,1 and 2,0
    @Test
    public void sample2Test17() {
        sample2.reveal(0,3);
        sample2.reveal(1,0);
        int expectedTilesUnrevealed=10;
        int expectedPlayerBoardValueTop = 1;
        int expectedPlayerBoardValueMiddleLeft = 2;
        assertEquals(expectedPlayerBoardValueTop,sample2.getPlayerBoard()[0][3]);
        assertEquals(expectedPlayerBoardValueMiddleLeft,sample2.getPlayerBoard()[1][0]);
        assertEquals(expectedTilesUnrevealed,sample2.getTilesUnrevealed());
    }

    //Testing reveal() method. (Case: invalid position -1,-1)
    //Note: Also testing if correct number is returned (via calculateTileNumber() method)
    @Test
    public void sample2Test18() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample0.reveal(-1, -1);
        });
    }

    //Testing isGameWon() method. (Case: revealed 3 positions, expect game is still not won)
    @Test
    public void sample2Test19(){
        sample2.reveal(0,2);
        boolean expectedAnswer0 = false;
        assertEquals(expectedAnswer0,sample2.isGameWon());
        sample2.reveal(1,1);
        boolean expectedAnswer1 = false;
        assertEquals(expectedAnswer1,sample2.isGameWon());
        sample2.reveal(2,3);
        boolean expectedAnswer2 = false;
        assertEquals(expectedAnswer2,sample2.isGameWon());
    }

    //Testing isGameWon() method. (Case: revealed all positions, expect game is won)
    @Test
    public void sample2Test20() {
        for(int i=0;i<sample2.getPlayerBoard().length;i++) {
            for(int j=0;j<sample2.getPlayerBoard()[0].length;j++) {
                if(sample2.getMineBoard()[i][j]!=gameModel.mine) {
                    sample2.reveal(i,j);
                }
            }
        }
        boolean expectedAnswer0 = true;
        assertEquals(expectedAnswer0,sample2.isGameWon());
    }

    //Testing isGameLost() method. (Case: choosing unrevealed non-mine position 0,2 , expect game not lost)
    @Test
    public void sample2Test21() {
        boolean expectedAnswer0 = false;
        assertEquals(expectedAnswer0,sample2.isGameLost(0, 2));
    }

    //Testing isGameLost() method. (Case: revealing non-mine position 0,2 , expect game not lost)
    @Test
    public void sample2Test22() {
        boolean expectedAnswer0 = false;
        sample2.reveal(0,2);
        assertEquals(expectedAnswer0,sample2.isGameLost(0, 2));
    }

    //Testing isGameLost() method. (Case: revealing mine position 0,0 , expect game is lost)
    @Test
    public void sample2Test23() {
        boolean expectedAnswer0 = true;
        sample2.reveal(0,0);
        assertEquals(expectedAnswer0,sample2.isGameLost(0, 0));
    }

    //Testing isGameLost() method. (Case: invalid position 3,3)
    @Test
    public void sample2Test24() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sample2.isGameLost(3, 3);
        });
    }

    //Testing revealNeighbour() method. (Case: chosen position 2,0)
    //Expect playerBoard[][] to look like this (? means unrevealed):
    // {? ? ? ?}
    // {2 2 2 ?}
    // {0 0 1 ?}
    @Test
    public void sample2Test25() {
        sample2.revealNeighbours(2, 0);
        //Middle row
        assertEquals(2,sample2.getPlayerBoard()[1][0]);
        assertEquals(2,sample2.getPlayerBoard()[1][1]);
        assertEquals(2,sample2.getPlayerBoard()[1][2]);
        //Last row
        assertEquals(0,sample2.getPlayerBoard()[2][0]);
        assertEquals(0,sample2.getPlayerBoard()[2][1]);
        assertEquals(1,sample2.getPlayerBoard()[2][2]);

        //Checking if tilesUnrevealed variable has changed
        assertEquals(6,sample2.getTilesUnrevealed());

    }

}
