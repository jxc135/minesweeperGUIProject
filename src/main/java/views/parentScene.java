package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
/**
 * This abstract class represents a set of buttons, a HBox and a scene that all relevant scenes (help scene, game scene, settings scene) each will have. The
 * aim is to reduce duplicate code, hence this abstract class was created.
 *
 * @version 29/08/2020
 * @author JXC135
 *
 */
public abstract class parentScene {
    /**
     *  field variable to represent the help button
     */
    protected Button help = new Button("HELP");
    /**
     *  field variable to represent the game button
     */
    protected Button game = new Button("GAME");
    /**
     *  field variable to represent the settings button
     */
    protected Button settings = new Button("SETTINGS");
    /**
     *  field variable to represent the HBox layout for the 'top menu'
     */
    protected HBox topMenu = new HBox(5);
    /**
     * This method is used to create and define the layout of the 'top menu' at the top of every scene. It adds event handlers to the buttons 'help','game' and 'settings'.
     */
    public abstract void setMenuPart();
    /**
     *  field variable to represent the scene's background colour
     */
    public static final Color backgroundColor = Color.LIGHTGREY;
    /**
     *  field variable to represent a scene
     */
    protected Scene scene;
    /**
     * Getter for the scene
     * @return The scene for the settings scene
     */
    public Scene getScene() {
        return this.scene;
    }
    /**
     * Getter for the help button
     * @return the help
     */
    public Button getHelp() {
        return help;
    }
    /**
     * Getter for the game button
     * @return the game
     */
    public Button getGame() {
        return game;
    }
    /**
     * Getter for the settings button
     * @return the settings
     */
    public Button getSettings() {
        return settings;
    }
    /**
     * Getter for the topMenu HBox
     * @return the topMenu
     */
    public HBox getTopMenu() {
        return topMenu;
    }
}

