package models;
/**
 * This class represents the settings for the 'Minesweeper' game. It contains the field variables and options to select the colour of the tiles, the number
 * of tiles per row, the number of tiles per column and the number of mines
 *
 * @version 21/08/2020
 * @author JXC135
 *
 */
public class settingsModel {
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
     *  field variable to represent the colour of the tiles
     */
    private String tileColor;
    /**
     *  static field variables that represents different colours.
     *  The tiles in the game scene are all Buttons. Hence, one of the ways to change Buttons colours was to define them using CSS and hex colour.
     */
    public static final String lightGreyTiles= "-fx-background-color: #e5e5e5";
    public static final String greenTiles= "-fx-background-color: #90ee90";
    public static final String blueTiles= "-fx-background-color: #99BBFF";
    public static final String redTiles= "-fx-background-color: #FA8072";

    /**
     * Constructor for settingsModel. It validates if the parameter values are suitable for a set up a minesweeper first, before actually contructing a settingModel object
     * @param xtiles The number of tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines
     */
    public settingsModel(int xtiles, int ytiles, int mineNum) {
        this.setUpSettings(xtiles, ytiles, mineNum);
        this.setTileColor(lightGreyTiles);
    }

    /**
     * Default constructor for settingsModel
     */
    public settingsModel() {
        this.setUpSettings(8, 8, 10);
        this.tileColor=lightGreyTiles;
    }

    /**
     * This method ensures the parameter values are valid for a game of minesweeper. If so, it'll set values for all the field variables of this class accordingly and
     * @param xtiles The number of tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines for a given minesweeper board
     * @throws IllegalArgumentException To indicate that parameter values are invalid to setup a game of Minesweeper
     */
    public void setUpSettings(int xtiles, int ytiles, int mineNum) throws IllegalArgumentException {
        if(isValidGameSetup(xtiles,ytiles,mineNum)) {
            this.xtiles = xtiles;
            this.ytiles = ytiles;
            this.mineNum = mineNum;
        } else {
            throw new IllegalArgumentException("Invalid parameters.");
        }
    }
    /**
     * Checks if the game size is valid and the mines set for the game is valid
     * @param xtiles The number of tiles per row
     * @param ytiles The number of tiles per column
     * @param mineNum The number of mines
     * @return true or false, depending on the values of the parameters
     */
    public static boolean isValidGameSetup(int xtiles, int ytiles, int mineNum) {
        if(xtiles<1 || ytiles<1 || mineNum<1) {
            return false;
        }

        if(xtiles*ytiles<mineNum) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Getter for xtiles
     * @return xtiles The number of tiles per row
     */
    public int getXtiles() {
        return xtiles;
    }

    /**
     * Getter for ytiles
     * @return ytiles The number of tiles per column
     */
    public int getYtiles() {
        return ytiles;
    }

    /**
     * Getter for mineNum
     * @return mineNum The number of mines
     */
    public int getMineNum() {
        return this.mineNum;
    }

    /**
     * Getter for tileColor
     * @return tileColor The current colour of the tiles
     */
    public String getTileColor() {
        return this.tileColor;
    }

    /**
     * Setter for tileColor
     * @param tileColor The new colour of the tiles
     */
    public void setTileColor(String tileColor) {
        switch(tileColor) {
            case redTiles:
                this.tileColor=redTiles;
                break;
            case greenTiles:
                this.tileColor=greenTiles;
                break;
            case blueTiles:
                this.tileColor=blueTiles;
                break;
            default:
                this.tileColor=lightGreyTiles;
                break;
        }
    }
}
