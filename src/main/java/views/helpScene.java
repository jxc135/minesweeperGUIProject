package views;

import javafx.geometry.Pos;
import controllers.mainController;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * This class shows how the scene of the help page is made.
 * It has a menu at the top with different buttons. This allows the user to goto different scenes.
 * Afterwards are blocks of text that tells how to navigate the application, as well as information about the game Minesweeper.
 *
 * @version 29/08/2020
 * @author JXC135
 *
 */
public class helpScene extends parentScene {
    /**
     *  field variable to represent the VBox layout that will contain all the other buttons, text and layouts
     */
    private VBox wholeLayout;

    /**
     * Constructor for the help scene.
     * This constructor uses the 'setMenuPart' method to create the menu at the top of this scene. Then creates several text that talks about the rules of the game and
     * briefly talks about the user can do on the settings scene.
     * Both the menu at the top and the text are contained (and arranged) within a VBox (wholeLayout).
     * The scene is created via 'wholeLayout' and the background colour is set to darkGrey.
     */
    public helpScene() {

        this.setMenuPart();

        Text ruleTitle = new Text("RULES AND AIMS OF MINESWEEPER:");
        Text rule1 = new Text("The aim of the game is to reveal all of the tiles WITHOUT revealing a mine");
        Text rule2 = new Text("The numbers that get revealed gives you hints about how many mine are next to the revealed number");
        Text rule3 = new Text("If you want to reset the game, just press on the emoji face");
        Text rule4 = new Text("On the left of the emoji face shows the number of mines for the current grid");
        Text rule5 = new Text("On the right of the emoji face shows the timer");
        Text settingTitle = new Text("SETTINGS PAGE");
        Text settingText1 = new Text("The settings page can be accessed on the top left of this screen");
        Text settingText2 = new Text("You can change the size of the game in settings and customize the display to your benefit");

        wholeLayout = new VBox(10);
        wholeLayout.getChildren().addAll(topMenu,ruleTitle,rule1,rule2,rule3,rule4,rule5,settingTitle,settingText1,settingText2);
        wholeLayout.setAlignment(Pos.CENTER_LEFT);
        wholeLayout.setPadding(new Insets(10, 10, 10, 10));

        Group layoutWrapper = new Group();
        layoutWrapper.getChildren().add(wholeLayout);

        this.scene=new Scene(layoutWrapper);
        this.scene.setFill(backgroundColor);

    }

    /**
     * This method is inherited from parentScene class. This implements the relevant event handlers for the three buttons (help,game and settings button).
     * Then adds the buttons to the HBox 'topMenu'
     */
    @Override
    public void setMenuPart() {
        this.help.setStyle("-fx-text-fill: #ffffff");

        this.game.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.gameMenuButtonPressed);

        this.settings.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.settingsMenuButtonPressed);

        this.topMenu.getChildren().addAll(help,game,settings);
    }

}

