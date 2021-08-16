package views;

import javafx.scene.control.Button;
/**
 * This class represents a single Button that will be used in the game scene class. In the game scene class, the Minesweeper board is represented as
 * tileButton[][]. Hence, the main purpose of this class is to make it easier for event handlers, (which is defined in the controller class)
 * to figure out the row and col position of the tileButton that was selected. Once tileButton is created, the row and col values should not be changed at all.
 * @version 29/08/2020
 * @author JXC135
 *
 */
public class tileButton extends Button {
    /**
     * field variable to represent the row position of the tileButton object
     */
    private final int row;
    /**
     * field variable to represent the column position of the tileButton object
     */
    private final int col;
    /**
     * field variable to represent the pixel width/length of the tileButton object. The tileButton will be a square, hence only needing to specify one length value.
     */
    public final int pixelWidth=30;
    /**
     * The constructor for a single tileButton object. It requires 2 integer parameters, each representing a row position and column position.
     * The row and column will be used and referenced by the controller, to easily figure out which part of the minesweeper board has been clicked or needs updating.
     * @param row The row position of the tileButton object
     * @param col The column position of the tileButton object
     */
    public tileButton(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.setPrefSize(pixelWidth, pixelWidth);
        this.setMaxSize(pixelWidth, pixelWidth);
    }
    /**
     * Getter for the row position of the tileButton object
     * @return row The row position of the tileButton object
     */
    public int getRow() {
        return row;
    }
    /**
     * Getter for the col position of the tileButton object
     * @return col The col position of the tileButton object
     */
    public int getColumn() {
        return col;
    }

}
