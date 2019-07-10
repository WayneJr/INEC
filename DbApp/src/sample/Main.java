package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * This is a Voting Application that gathers the users information and sends it to a database.
 * This is the main class of this Voting application.
 * @author WayneJr
 * @version  1.0
 */
public class Main extends Application {

    Stage window;
    Scene scene, scene2;

    /**
     * This is the start method of this voting app.
     * It sets up the stage to hold the various scenes to be displayed.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Parent root1 = FXMLLoader.load(getClass().getResource("signup.fxml"));

        Image image = new Image("file:///C:/Users/Ekeoma/Desktop/Work/New Lessons/IdeaProjects/MyProjects/DbApp/src/sample/inec.jpg");

        scene = new Scene(root, 285, 264);
        scene2 = new Scene(root1, 382, 359);

        window.getIcons().add(image);
        window.setTitle("INEC");

        window.setScene(scene);
        window.setResizable(false);
        window.show();

    }


    /**
     * This is the main method of this Voting app.
     * Like all Java Applications, it is the main entry point of the Application.
     * @param args
     */

    public static void main(String[] args) {
        launch(args);
    }
}
