package views;

import controllers.mainController;
import models.settingsModel;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/**
 * This class shows how the scene of the settings page is made.
 * It has a menu at the top with different buttons. This allows the user to goto different scenes.
 * Under the menu, there are text and buttons that represent different settings.
 * By clicking on one of the setting buttons, the user can change the size and colour of the game's board.
 * There are text next to each setting buttons to show users what each button refers to.
 *
 * @version 21/08/2020
 * @author JXC135
 *
 */
public class settingsScene extends parentScene {
    /**
     *  field variable to represent the settingsModel
     */
    protected settingsModel settingModel;

    /**
     *  field variable to represent the VBox layout that will contain all the other buttons and layouts mentioned in this class
     */
    private VBox wholeLayout;

    /**
     *  field variable to represent the GridPane layout. This is for all the text and buttons related to choosing different settings.
     */
    private GridPane settingOptions = new GridPane();

    /**
     * 3 field variables below are labels to represent different BOARD SIZE OPTIONS
     */
    private Label smallBoardLabel;
    private Label mediumBoardLabel;
    private Label bigBoardLabel;

    /**
     * 3 field variables below are buttons to represent different BOARD SIZE OPTIONS
     */
    private Button smallBoardButton;
    private Button mediumBoardButton;
    private Button bigBoardButton;

    /**
     * 4 field variables below are labels to represent different TILE COLOUR OPTIONS for the board
     */
    private Label lightGreyTileLabel;
    private Label greenTileLabel;
    private Label blueTileLabel;
    private Label redTileLabel;

    /**
     * 4 field variables below are buttons to represent different TILE COLOUR OPTIONS for the board
     */
    private Button lightGreyTileButton;
    private Button greenTileButton;
    private Button blueTileButton;
    private Button redTileButton;

    /**
     * Constructor for the settings scene.
     * This constructor assigns the value of field variable 'settingsModel' to be the parameter (settingsModel).
     * Then the menu at the top of the scene is created via 'setMenuPart' method.
     * The buttons, text and layout for the settings options (choosing difficulty, board size and tile color) are created via 'createSettingOptions' method
     * All the above are added to a VBox (wholeLayout). Then a group object (layoutWrapper) is initialised and contains 'wholeLayout'.
     * The scene is created via 'layoutWrapper' and the background color is set to darkGrey.
     */
    public settingsScene(settingsModel settingModel) {

        this.settingModel = settingModel;

        this.setMenuPart();

        this.createSettingOptions();

        wholeLayout = new VBox(10);
        wholeLayout.getChildren().addAll(topMenu,settingOptions);
        wholeLayout.setPadding(new Insets(10, 10, 10, 10));

        Group layoutWrapper = new Group(wholeLayout);

        this.scene= new Scene(layoutWrapper);
        this.scene.setFill(backgroundColor);
    }

    /**
     * This method is inherited from parentScene class. This implements the relevant event handlers for the three buttons (help,game and settings button).
     * Then adds the buttons to the HBox 'topMenu'
     */
    @Override
    public void setMenuPart() {
        this.help.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.helpMenuButtonPressed);
        this.game.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.gameMenuButtonPressed);
        this.settings.setStyle("-fx-text-fill: #ffffff");
        topMenu.getChildren().addAll(help,game,settings);
    }

    /**
     * This method creates all the buttons, text and event handlers that the user will mainly click on (in order to change game settings).
     * The options are 3 different board sizes (increased board size also means increased number of mines) and 4 different tile colours.
     * The buttons and text are all neatly arranged via a gridpane (settingsOptions).
     */
    public void createSettingOptions() {
        smallBoardLabel=new Label("7 x 7 Grid (5 mines): ");
        mediumBoardLabel=new Label("8 x 8 Grid (10 mines): ");
        bigBoardLabel=new Label("9 x 9 Grid (15 mines): ");

        smallBoardButton=new Button("  ");
        smallBoardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.boardOptionButtonPressed);
        mediumBoardButton=new Button("  ");
        mediumBoardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.boardOptionButtonPressed);
        bigBoardButton=new Button("  ");
        bigBoardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.boardOptionButtonPressed);

        lightGreyTileLabel = new Label("Grey tiles: ");
        greenTileLabel = new Label("Green tiles: ");
        blueTileLabel = new Label("Blue tiles: ");
        redTileLabel = new Label("Red tiles: ");

        lightGreyTileButton = new Button("  ");
        lightGreyTileButton.setStyle(settingsModel.lightGreyTiles);
        lightGreyTileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.colourOptionButtonPressed);
        greenTileButton = new Button("  ");
        greenTileButton.setStyle(settingsModel.greenTiles);
        greenTileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.colourOptionButtonPressed);
        blueTileButton = new Button("  ");
        blueTileButton.setStyle(settingsModel.blueTiles);
        blueTileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.colourOptionButtonPressed);
        redTileButton = new Button("  ");
        redTileButton.setStyle(settingsModel.redTiles);
        redTileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.colourOptionButtonPressed);

        settingOptions.add(smallBoardLabel, 0, 0);
        settingOptions.add(smallBoardButton, 1, 0);
        settingOptions.add(mediumBoardLabel, 0, 1);
        settingOptions.add(mediumBoardButton, 1, 1);
        settingOptions.add(bigBoardLabel, 0, 2);
        settingOptions.add(bigBoardButton, 1, 2);

        settingOptions.add(lightGreyTileLabel, 0, 6 );
        settingOptions.add(lightGreyTileButton, 1, 6);
        settingOptions.add(greenTileLabel, 0, 7);
        settingOptions.add(greenTileButton, 1, 7);
        settingOptions.add(blueTileLabel, 0, 8);
        settingOptions.add(blueTileButton, 1,8);
        settingOptions.add(redTileLabel, 0, 9);
        settingOptions.add(redTileButton, 1, 9);

        settingOptions.setVgap(5);
    }

    /**
     * Getter for the smallBoardButton
     * @return the smallBoardButton
     */
    public Button getSmallBoardButton() {
        return smallBoardButton;
    }

    /**
     * Getter for the smallBoardButton
     * @return the mediumBoardButton
     */
    public Button getMediumBoardButton() {
        return mediumBoardButton;
    }

    /**
     * Getter for the smallBoardButton
     * @return the bigBoardButton
     */
    public Button getBigBoardButton() {
        return bigBoardButton;
    }

    /**
     * Getter for the lightGreyTileButton
     * @return the lightGreyTileButton
     */
    public Button getLightGreyTileButton() {
        return lightGreyTileButton;
    }

    /**
     * Getter for the greenTileButton
     * @return the greenTileButton
     */
    public Button getGreenTileButton() {
        return greenTileButton;
    }

    /**
     * Getter for the blueTileButton
     * @return the blueTileButton
     */
    public Button getBlueTileButton() {
        return blueTileButton;
    }

    /**
     * Getter for the redTileButton
     * @return the redTileButton
     */
    public Button getRedTileButton() {
        return redTileButton;
    }
}

