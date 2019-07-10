package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //Objects defined in the FXML
    @FXML
    private Rectangle rectangle;

    @FXML
    private Path path;

    @FXML
    private Text text;

    @FXML
    private Button startPauseButton;

    @FXML
    private Button slowerButton;

    @FXML
    private Button fasterButton;

    //Constants to control the transition's rate changes
    final double maxRate = 7.0;
    final double minRate = .3;
    final double rateDelta = .3;

    private PathTransition pathTransition;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Create the path transition
        pathTransition = new PathTransition(Duration.seconds(6), path, rectangle);
        pathTransition.setOrientation(
                PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);

        //We count laps by noticing when the currentTimeProperty changes and the
        //oldValue is greater than the newValue, which is the only true once per lap
        //We increment the lapCounterProperty
        final IntegerProperty lapCounterProperty = new SimpleIntegerProperty(0);
        pathTransition.currentTimeProperty().addListener(
                (ObservableValue<? extends Duration> ov,
                Duration oldValue, Duration newValue) -> {
                    if (oldValue.greaterThan(newValue)) {
                        lapCounterProperty.set(lapCounterProperty.get() + 1);
                    }
                });
        //Bind the text's textProperty to the lapCounterProperty and format it
        text.textProperty().bind(lapCounterProperty.asString("Lap Counter: %s"));

        startPauseButton.textProperty().bind(
                new When(pathTransition.statusProperty()
                    .isEqualTo(Animation.Status.RUNNING))
                        .then("Pause").otherwise("Start"));

        fasterButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));
        slowerButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));

        fasterButton.setText(" >> ");
        slowerButton.setText(" << ");

    }

    @FXML
    private void startPauseAction(ActionEvent event) {
        if (pathTransition.getStatus() == Animation.Status.RUNNING) {
            pathTransition.pause();
        } else {
            pathTransition.play();
        }
    }

    @FXML
    private  void  slowerAction(ActionEvent event) {
        double currentRate = pathTransition.getRate();
        if (currentRate <= minRate) {
            return;
        }
        pathTransition.setRate(currentRate - rateDelta);
        System.out.printf("Slower Rate = %.2f\n", pathTransition.getRate());
    }

    @FXML
    private void fasterAction(ActionEvent event) {
        double currentRate = pathTransition.getRate();
        if (currentRate >= maxRate) {
            return;
        }
        pathTransition.setRate(currentRate + rateDelta);
        System.out.printf("Faster Rate = %.2f\n", pathTransition.getRate());
    }



}
