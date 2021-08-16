package controllers;

import models.gameModel;
import models.settingsModel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the initial class that should be launched when running the application. 
 * This class is the central point for all scenes and contains the stage (window) which all relevant scenes will share.
 * This class creates a setting model object, game model object and a controller object.
 * The scenes are then created via the controller's constructor. 
 *
 * @version 27/08/2020
 * @author JXC135
 *
 */
public class startWindow extends Application{
        /**
         * Static variable to represent the stage of this whole application
         */
        public static Stage stage;
        @Override
        public void start(Stage arg0) throws Exception {
            //Referencing the arg0 parameter so that all scenes can be shown on this stage
            stage=arg0;
            //Creating a default setting model object. Default values are 8 rows, 8 columns and 10 mines
            settingsModel settingModel = new settingsModel();
            //Creating a game model object based on the values recently made from the setting model object above
            gameModel gameModel = new gameModel(settingModel.getXtiles(),settingModel.getYtiles(),settingModel.getMineNum());
            //Creating controller object that requires what models to update and work with, so that the controller can generate the correct relevant scenes for each model
            mainController controller=new mainController(gameModel,settingModel);
            //Setting the game scene as the initial scene to be shown on the stage
            arg0.setScene(controller.getControllersView().getScene());
            //Setting the title of the application
            arg0.setTitle("Minesweeper");
            arg0.setResizable(false);
            arg0.show();

        }

        public static void main(String[] args) {
            launch(args);
        }

}
