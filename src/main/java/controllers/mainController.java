package controllers;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.gameModel;
import models.settingsModel;
import views.gameScene;
import views.helpScene;
import views.settingsScene;
import views.tileButton;
/**
 * This class represents the main controller that handles all events from all the relevant scenes. It also updates all relevant models and then updates scenes,
 * according to the updated values within all the relevant models.
 * @version 29/08/2020
 * @author JXC135
 */
public class mainController {
    /**
     * Static field variable to represent a game model that the controller will reference
     */
    private static gameModel controllersModel;
    /**
     * Static field variable to represent a game view that the controller will reference
     */
    private static gameScene controllersView;
    /**
     * Static field variable to represent a settings model that the controller will reference
     */
    private static settingsModel controllerssettingModel;
    /**
     * Static field variable to represent a settings view that the controller will reference
     */
    private static settingsScene controllerssettingView;
    /**
     * Static field variable to represent a help view that the controller will reference
     */
    private static helpScene controllersHelpView;
    /**
     * The constructor for the controller. It takes in 2 parameters, both are from a game model object and a setting model object.
     * The views are constructed here, as the views require a model object before getting themselves constructed.
     * @param controllersModel The game model object that the controller will update and reference
     * @param settingModel The setting model object that the controller will update and reference
     */
    public mainController(gameModel controllersModel,settingsModel settingModel) {

        mainController.controllerssettingModel = settingModel;
        mainController.controllersModel = controllersModel;

        mainController.controllerssettingView = new settingsScene(settingModel);
        mainController.controllersView = new gameScene(controllersModel);

        mainController.controllersHelpView=new helpScene();

    }
    /**
     * This event handler is related to mouse clicks on a button within the minesweeper board.
     * This will get the row and column of the button that was pressed.
     * Then check if the user has clicked upon the board for the first time or not. If so, timer for the game starts.
     * After, the controller will update the values in game model's playerBoard[][].
     * The controller will update the game scene according the updates made in the game model.
     * Checks if the game is won or lost. If so, stop the timer and disable all buttons within the game scene.
     */
    public static final EventHandler<MouseEvent> boardButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            //Check if it is the user's 1st button click. Start timer if so
            if(controllersView.isFirstClick) {
                controllersView.startTimelineobj();
                controllersView.isFirstClick=false;
            }
            //Record the row and col position of the button pressed within the playerBoard[][]
            tileButton buttonSource = (tileButton) arg0.getSource();
            int chosenx = buttonSource.getRow();
            int choseny = buttonSource.getColumn();
            //Ensure event was a left mouse click before revealing parts of the playerBoard[][]
            if(arg0.getButton() == MouseButton.PRIMARY) {
                //Reveal parts of the playerBoard[][] array
                controllersModel.revealNeighbours(chosenx,choseny);
                //After updating game state, check if the game is lost.
                if(controllersModel.isGameLost(chosenx,choseny)) {
                    controllersModel.showAnswer();

                    controllersView.stopTimelinobj();
                    controllersView.changeToGameLostEmoji();
                }
                //After updating game state, check if the game is won.
                if(controllersModel.isGameWon()) {
                    controllersModel.showAnswer();

                    controllersView.stopTimelinobj();
                    controllersView.changeToGameWonEmoji();
                }

                //Update the view of the game board
                controllersView.updateTilesArray();

            } else if (arg0.getButton() == MouseButton.SECONDARY) {
                //toggle(or un-toggle) flag if the user RIGHT CLICKS a tile.
                controllersModel.toggleFlagAtTile(chosenx, choseny);

                controllersView.updateTilesArray();
            }
        }
    };

    /**
     * This event handler is related to mouse clicks on the 'Emoji' button within the game scene.
     * The controller will reset both the playerBoard[][] and mineBoard[][] arrays within the game model.
     * It will also reset the number of hidden tiles to the size of the board.
     * The controller also stops the timer, resets the timer to 0.
     * Then updates the game scene according to the updates within the model
     */
    public static final EventHandler<MouseEvent> restartButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            controllersModel.resetPlayerBoard();
            controllersModel.resetMineBoard();
            controllersModel.resetTilesUnrevealed();
            controllersModel.assignMines();

            controllersView.stopTimelinobj();
            controllersView.resetTimeline();
            controllersView.updateTilesArray();
            controllersView.changeToNormalGameEmoji();
        }
    };

    /**
     * This event handler is related to mouse clicks on the different board size options that a user can select within the settings scene.
     * The controller will figure out which button was pressed.
     * Then updates the board size values within the settings model
     */
    public static final EventHandler<MouseEvent> boardOptionButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            Button selectedOption = (Button) arg0.getSource();
            if(selectedOption==controllerssettingView.getSmallBoardButton()) {
                controllerssettingModel.setUpSettings(7, 7, 5);
            }else if(selectedOption==controllerssettingView.getMediumBoardButton()) {
                controllerssettingModel.setUpSettings(8, 8, 10);
            }else if(selectedOption==controllerssettingView.getBigBoardButton()) {
                controllerssettingModel.setUpSettings(9, 9, 15);
            }
        }
    };
    /**
     * This event handler is related to mouse clicks on the different tile colour options that a user can select within the settings scene.
     * The controller will figure out which button was pressed.
     * Then updates the tile colour value within the settings model
     */
    public static final EventHandler<MouseEvent> colourOptionButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            Button selectedOption = (Button) arg0.getSource();
            if(selectedOption==controllerssettingView.getLightGreyTileButton()) {
                controllerssettingModel.setTileColor(settingsModel.lightGreyTiles);
            }else if(selectedOption==controllerssettingView.getGreenTileButton()) {
                controllerssettingModel.setTileColor(settingsModel.greenTiles);
            }else if(selectedOption==controllerssettingView.getBlueTileButton()) {
                controllerssettingModel.setTileColor(settingsModel.blueTiles);
            }else if(selectedOption==controllerssettingView.getRedTileButton()) {
                controllerssettingModel.setTileColor(settingsModel.redTiles);
            }else {
                controllerssettingModel.setTileColor(settingsModel.lightGreyTiles);
            }
        }
    };

    /**
     * This event handler is related to mouse clicks on the game button at the top of every scene.
     * The controller will set board size values of the game model according to values within the settings model.
     * Then creates a new game scene, using the game model.
     * Sets the tile colours inside the game scene according to values within the settings model.
     * Updates the buttons array and gridpane within the game scene to have the right tile colour.
     * Then makes the game scene the scene of the stage
     */
    public static final EventHandler<MouseEvent> gameMenuButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            controllersModel.setUpGame(controllerssettingModel.getXtiles(), controllerssettingModel.getYtiles(), controllerssettingModel.getMineNum());
            controllersView=new gameScene(controllersModel);
            controllersView.setTileColor(controllerssettingModel.getTileColor());
            controllersView.updateTilesArray();
            controllersView.updateGridPane();
            startWindow.stage.setScene(controllersView.getScene());
        }
    };

    /**
     * This event handler is related to mouse clicks on the settings button at the top of every scene
     * The controller will make the settings scene the scene of the stage.
     */
    public static final EventHandler<MouseEvent> settingsMenuButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            startWindow.stage.setScene(controllerssettingView.getScene());
        }
    };

    /**
     * This event handler is related to mouse clicks on the help button at the top of every scene
     * The controller will make the help scene the scene of the stage.
     */
    public static final EventHandler<MouseEvent> helpMenuButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            startWindow.stage.setScene(controllersHelpView.getScene());
        }
    };

    /**
     * Getter for the setting model used by the controller
     * @return the controllerssettingModel
     */
    public static settingsModel getControllerssettingModel() {
        return controllerssettingModel;
    }

    /**
     * Getter for the setting view used by the controller
     * @return the controllerssettingView
     */
    public static settingsScene getControllerssettingView() {
        return controllerssettingView;
    }

    /**
     * Getter for the help view used by the controller
     * @return the controllersHelpView
     */
    public static helpScene getControllersHelpView() {
        return controllersHelpView;
    }

    /**
     * Getter for the game model used by the controller
     * @return the controllersModel
     */
    public gameModel getControllersModel() {
        return controllersModel;
    }

    /**
     * Getter for the game view used by the controller
     * @return the controllersView
     */
    public gameScene getControllersView() {
        return controllersView;
    }}

