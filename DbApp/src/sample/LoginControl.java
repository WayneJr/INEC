package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * This is the Login Control Class of this Voting App.
 * It is also the Main Interface of the entire application. It serves as a doorway to other classes.
 */

public class LoginControl implements Initializable {

//    private sample.Track application;?

//    private Scene scene = application.scene2;

    int row;

    @FXML
    private Button loginbutton;

    @FXML
    TextField user;

    @FXML
    PasswordField pass;

    @FXML
    Hyperlink signup;

    public String home = System.getProperty("user.home");



    /**
     * This is basically the main method of this class.
     * It controls the ability of the user to log into the app.
     * @param event
     */

    @FXML
    private void handleLogin(MouseEvent event) {
        Connection conn;
        PreparedStatement pst;
        ResultSet rs;

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + home + "\\Desktop\\INEC.accdb");
            String sql = "select * from Login_Table where Username='" + user.getText() + "' and Userpass='" + pass.getText() + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();



            if (rs.next() && ageTester()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have Successfully Logged In!");

                alert.setTitle("Login Success");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("vote.fxml"));
                    Parent roots = loader.load();
                    ((Stage)loginbutton.getScene().getWindow()).setScene(new Scene(roots));
                } catch (Exception e) {
                    e.getMessage();
                }




            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Entry! Error could be one of these: " +
                        "\n1. Invalid Username or Password" +
                        "\n2. You do not have An Account(Please click the link below to Sign Up!)" +
                        "\n3. You are not of Age to Participate in this exercise");

                alert.setTitle("Login Failure");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
            }


        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());


            alert.setTitle("Error Message");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
//            e.printStackTrace();
        }


    }

    /**
     *This is the age validation method for the LoginControl Class.
     * It is a boolean method.
     * @return boolean value
     */

    private boolean ageTester() {

        Connection conns;
        PreparedStatement prep;
        ResultSet result;

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conns = DriverManager.getConnection("jdbc:ucanaccess://" + home + "\\Desktop\\INEC.accdb");
            String sql = "select * from Login_Table where Username='" + user.getText() + "' and Userpass='" + pass.getText() + "'";
            prep = conns.prepareStatement(sql);
            result = prep.executeQuery();

            while (result.next()) {
                int age = result.getInt("Age");
                row = result.getInt("ID");
                if (age >= 18) {
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You are not of Age to vote");

                    alert.setTitle("Too Young to Vote");
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> alert.close());

                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * The initialize method of the LoginControl class
     * For this particular app, it doesn't override anything.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Vote handleVote = new Vote();

        /*Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have opened the Login Panel");

        alert.setTitle("Welcome!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> alert.close());*/

    }

    /**
     * This method takes of the <strong>Sign In</strong> hyperlink and directs the user from the Login page to the sign in page.
     * @param event
     */

    @FXML
    private void handleSign(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent roots = loader.load();
            ((Stage)signup.getScene().getWindow()).setScene(new Scene(roots));
        } catch (IOException e) {
            e.getMessage();
        }

    }

}

