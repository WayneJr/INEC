package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Track extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Race Track");
        Image image = new Image("file:///C:/Users/Ekeoma/Desktop/Work/New Lessons/JavaFX Lessons/RaceTrack/src/sample/trapnation.jpg");
        primaryStage.setScene(new Scene(root, 400, 300));

        primaryStage.getIcons().add(image);

        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
