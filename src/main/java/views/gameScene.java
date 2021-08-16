package views;

import controllers.mainController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.gameModel;
import models.settingsModel;

/**
 *  This class shows how the scene of the Minesweeper game is made.
 *  There are three main sections of this game scene:
 *
 *  First is the 'top menu'. The 'top menu' allows the user to goto different scenes. 
 *  All buttons and layout related to the 'top menu' is inherited from the parentScene class. 
 *
 *  The second is the 'score display'. The 'score display' shows the number of mines for the given 
 *  board size, the timer and the emoji button. The emoji button allows the user to restart a new game, with 
 *  the same board size and same amount of mines.
 *
 *  The third is the actual Minesweeper board. The board size and amount of mines is taken from gameModel.java
 *  More details about the board is mentioned within the code below. 
 *
 *  @version 21/08/2020
 *  @author JXC135
 */
public class gameScene extends parentScene {

    /*
     * The 5 field variables below represent images relating to the timer, amount of mines and emoji (to start a new game) labels.
     */
    private ImageView normalEmojiFaceView;
    private ImageView deadEmojiFaceView;
    private ImageView happyEmojiFaceView;
    private ImageView minesweeperClockView;
    private ImageView minesweeperBombView;

    /*
     * The 7 field variables below represent nodes related to the score display.
     * The score display will show amount of mines, time played and an emoji face (to start a new game).
     * Field variables 'timelineobj', 'seconds', 'isFirstClick' are needed to update timePlayed label every second
     * Field variables 'scoreDisplay' will contain the these labels.
     */
    private Label numberOfMines;
    private Label emojiButton;
    private Label timePlayed;
    private Timeline timelineobj;
    private int seconds=0;
    public boolean isFirstClick=true;
    private HBox scoreDisplay;

    /*
     * The 7 field variables below represent nodes related to the actual minesweeper game itself.
     * The state of the game will be represented a button matrix (tilesArray) and placed within a boardpane (board) layout.
     * The pixel size and colour of each button are initialised here to be used within 'tilesArray'
     *
     */
    private gameModel gameModel;
    private GridPane board = new GridPane();
    private tileButton[][] tilesArray;
    private String tileColor=settingsModel.lightGreyTiles;

    /**
     *  field variable to represent the VBox layout that will contain all the other buttons, text and layouts
     */
    private VBox wholeLayout;

    /**
     * Constructor for the game scene. 
     * The parameter 'gameModel' is assigned to be the value of this class's gamemodel field variable.
     * Then the size of the board is obtained from 'gameModel' and hence, a button matrix is created, updated with correct color or text and neatly arranged via a boardpane.
     * Then the menu at the top of the screen is created.
     * The images within this package are created as imageviews to be used later.
     * The score display (timer, amount of mines and emoji) are created and also uses imageviews that was made via 'setUpImageViews()' method
     * All of the above are then added to a VBox (wholeLayout) to be neatly laid out. 
     * Then 'wholeLayout' is added a group object (layoutWrapper) to ensure scene dimensions scaled automatically to the overall dimensions occupied by it's children node.
     * Then the scene is created via 'layoutWrapper' and the background is set to be darkGrey.
     *
     * @param gameModel The gameModel object
     */
    public gameScene(gameModel gameModel) {
        this.gameModel = gameModel;
        this.initialiseTilesArray();
        this.updateTilesArray();
        this.updateGridPane();

        this.updateGridPane();
        //----------------------------------------------------		
        this.setMenuPart();
        //---------------------------------------------------	
        this.setUpImageViews();
        this.setScorePart();
        //----------------------------------------------------
        wholeLayout = new VBox(5);
        wholeLayout.getChildren().addAll(topMenu,scoreDisplay,board);
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
        this.game.setStyle("-fx-text-fill: #ffffff");

        this.help.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.helpMenuButtonPressed);

        this.settings.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.settingsMenuButtonPressed);

        this.topMenu.getChildren().addAll(help,game,settings);

    }

    /**
     * This method will initialise field variables 'numberOfMines', 'emojiButton' and timePlayed' as labels.
     * Within each label will contain an imageview and text, with set dimensions and event handlers. 
     * The 'numberOfMines' label text is set and retrieved from the gamemodel's 'mineNum' field variable value.
     * The 'emojiButton' label consist of 3 imageviews. Which image is shown is dependant on the current state of the game (won/lost/still-playing). 
     * The 'emojiButton' label also has an event handler, which creates a new game and changes the image within the 'emojiButton' label.
     * The 'timePlayed' label has text that updates every second. It uses a Timeline object to do this. The text only starts updating when user clicks on the board for the first time.
     * The 'timePlayed' label stops updating whenever the game is won or lost.
     * All these labels are then added to a HBox called 'scoreDisplay'.
     */
    public void setScorePart() {

        this.numberOfMines = new Label(""+this.gameModel.getMineNum(),this.minesweeperBombView);
        this.numberOfMines.setPrefWidth(40);

        this.emojiButton=new Label();
        this.emojiButton.setGraphic(this.normalEmojiFaceView);
        this.emojiButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.restartButtonPressed );

        this.timePlayed = new Label(""+this.seconds,this.minesweeperClockView);
        this.timePlayed.setPrefWidth(40);
        this.setUpTimelineobj();

        this.scoreDisplay = new HBox(10);
        this.scoreDisplay.getChildren().addAll(this.numberOfMines,this.emojiButton,this.timePlayed);
        this.scoreDisplay.setAlignment(Pos.BASELINE_CENTER);
    }


    //---------------------------------------------------------	
    // INITIALISE BUTTON[][] MATRIX ONCE, INITIALLY EMPTY BUTTONS IN EACH INDEX. 
    /**
     * This method initialises the 'tilesArray' field variable as a matrix of empty buttons. 
     * This method also defines the pixel size and an event handler of each button within the matrix. 
     * The number of rows and column of the button matrix are obtained from the gamemodel object. 
     */
    public void initialiseTilesArray() {
        this.tilesArray=new tileButton[gameModel.getXtiles()][gameModel.getYtiles()];

        for(int i=0;i<this.tilesArray.length;i++) {
            for(int j=0;j<this.tilesArray[0].length;j++) {
                this.tilesArray[i][j]=new tileButton(i,j);
                this.tilesArray[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mainController.boardButtonPressed);
            }
        }
    }
    // ONCE TILEBUTTON[][] ARRAY HAS BEEN INITIALLY MADE. UPDATING TILES OF BUTTON[][] MEANS JUST CHANGING IT'S TEXT, COLOUR, DISABLE. NOT CREATING A NEW BUTTON[][] EVERYTIME
    /**
     * This method is often used whenever the player clicks on a tile within the board. 
     * This method will update each element within the button matrix (tilesArray), based on the updated state of the gamemodel's playerBoard matrix. 
     * This method will update either the text, colour or option to disable each button within the tilesArray matrix.
     */
    public void updateTilesArray() {
        for(int i=0;i<tilesArray.length;i++) {
            for(int j=0;j<tilesArray[0].length;j++) {

                int entry=this.gameModel.getPlayerBoard()[i][j];

                switch(entry) {
                    case models.gameModel.unrevealed:
                        this.tilesArray[i][j].setText("");
                        this.tilesArray[i][j].setStyle(this.getTileColor());
                        this.tilesArray[i][j].setDisable(false);
                        break;
                    case 0:
                        this.tilesArray[i][j].setText("");
                        this.tilesArray[i][j].setStyle(this.getTileColor());
                        this.tilesArray[i][j].setDisable(true);
                        break;
                    case models.gameModel.mine:
                        this.tilesArray[i][j].setText("");
                        this.tilesArray[i][j].setStyle("-fx-background-color: #FF3333");
                        this.tilesArray[i][j].setDisable(true);
                        break;
                    case models.gameModel.flag:
                        this.tilesArray[i][j].setText("?");
                        this.tilesArray[i][j].setStyle("-fx-background-color: #d8d800");
                        this.tilesArray[i][j].setDisable(false);
                        break;
                    default:
                        this.tilesArray[i][j].setText(""+entry);
                        this.tilesArray[i][j].setStyle("-fx-font-weight: bolder;"+this.getTileColor());
                        this.tilesArray[i][j].setDisable(true);
                        break;
                }
            }
        }
    }

    /**
     * This method will clear all nodes from the gridpane and then fill the gridpane with nodes from the tileButton matrix (i.e tilesArray)
     */
    public void updateGridPane() {
        this.board.getChildren().clear();
        for(int i=0;i<tilesArray.length;i++) {
            for(int j=0;j<tilesArray[0].length;j++) {
                this.board.add(tilesArray[i][j], i, j);
            }
        }
        this.board.setHgap(1);
        this.board.setVgap(1);
    }

    // METHODS RELATED TO THE TIMER
    /**
     * This method creates the Timeline object. Every 1 second, the incrementTimePlayed() method is executed.
     * When the Timeline object is activated, it will keep counting indefinitely (till the stop() method is called upon it)
     * Timeline code is referenced from: https://stackoverflow.com/questions/9966136/javafx-periodic-background-task
     * Timeline code also referenced from: http://tomasmikula.github.io/blog/2014/06/04/timers-in-javafx-and-reactfx.html
     */
    public void setUpTimelineobj() {
        this.timelineobj =
                new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        incrementTimePlayed();
                    }
                }));
        timelineobj.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * This method will increment the 'seconds' field variable. It also updates the text of the 'timePlayed' label
     */
    public void incrementTimePlayed() {
        this.seconds++;
        this.timePlayed.setText(""+seconds);
    }

    /**
     * This method will start the timer
     */
    public void startTimelineobj() {
        this.timelineobj.play();
    }
    /**
     * This method will stop the timer
     */
    public void stopTimelinobj() {
        this.timelineobj.stop();
    }
    /**
     * This method will set the text of the 'timePlayed' label to zero,  
     * sets the value of 'seconds' to zero,
     * sets the value of 'isFirstClick' to true 
     */
    public void resetTimeline() {
        this.seconds=0;
        this.timePlayed.setText(""+this.seconds);
        this.isFirstClick=true;
    }

    /**
     * This method initialises the all images as imageview objects.
     * The imageview objects are given set sizes.
     * Image references: https://openmoji.org/library/
     */
    public void setUpImageViews() {

        this.normalEmojiFaceView = new ImageView(new Image("normalGameEmoji.png"));
        this.normalEmojiFaceView.setFitHeight(40);
        this.normalEmojiFaceView.setFitWidth(40);

        this.deadEmojiFaceView = new ImageView(new Image("gameLostEmoji.png"));
        this.deadEmojiFaceView.setFitHeight(40);
        this.deadEmojiFaceView.setFitWidth(40);

        this.happyEmojiFaceView = new ImageView(new Image("gameWonEmoji.png"));
        this.happyEmojiFaceView.setFitHeight(40);
        this.happyEmojiFaceView.setFitWidth(40);

        this.minesweeperClockView= new ImageView(new Image("minesweeperTimerImage.png"));
        this.minesweeperClockView.setFitHeight(20);
        this.minesweeperClockView.setFitWidth(20);

        this.minesweeperBombView= new ImageView(new Image("minesweeperMineImage.png"));
        this.minesweeperBombView.setFitHeight(20);
        this.minesweeperBombView.setFitWidth(20);

    }

    /**
     * This method changes the emojiButton label to an image to show that the game is lost
     */
    public void changeToGameLostEmoji() {
        this.emojiButton.setGraphic(this.deadEmojiFaceView);
    }
    /**
     * This method changes the emojiButton label to an image to show that the game is won
     */
    public void changeToGameWonEmoji() {
        this.emojiButton.setGraphic(this.happyEmojiFaceView);
    }
    /**
     * This method changes the emojiButton label to an image to show that the game in progress
     */
    public void changeToNormalGameEmoji() {
        this.emojiButton.setGraphic(this.normalEmojiFaceView);
    }

    /**
     * Getter for the array of buttons that represent the board
     * @return the tilesArray The current array of buttons that represent the board
     */
    public tileButton[][] gettilesArray() {
        return tilesArray;
    }

    /**
     * Getter for the board
     * @return the board The current GridPane of the board
     */
    public GridPane getBoard() {
        return board;
    }

    /**
     * Getter for the colour of the tiles on the board
     * @return tileColor The current tile colour of the board
     */
    public String getTileColor() {
        return this.tileColor;
    }

    /**
     * Setter for the color of the tiles on the board
     * @param tileColor The color of the tiles on the board
     */
    public void setTileColor(String tileColor) {
        this.tileColor=tileColor;
    }

}

