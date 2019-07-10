package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


/**
 * This is the Vote class of the Voting App
 */
public class Vote extends LoginControl {

   /* LoginControl info = new LoginControl();
    String name = info.getUsername();
    String pw = info.getPassword();*/

    @FXML
    private TextField idField;

    @FXML
    public JFXComboBox<String> candidBox;

    @FXML
    private JFXButton votebtn;

    @FXML
    private Button logout;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> candidates = FXCollections.observableArrayList(
                "APC - Gen. Muhammadu Buhari",
                "PDP - Alhaji Atiku Abubakar",
                "APGA - Chief Rotimi Amaechi",
                "LP - Chief Dino Melaye");

        candidBox.setItems(candidates);
    }

    /**
     * This is the EventHandler for the Vote Button.
     * It receives the user's input and sends it to the database.
     * @param event
     */
    @FXML
    public void handleVote(MouseEvent event) {
        //This is the first method to output a string to a combo box.
        //To use this you must declare the combobox as a string type using '<String>'
        String vote = candidBox.getSelectionModel().getSelectedItem();
        //This is the second method to output a string from a combo box
//        String output = candidBox.getSelectionModel().getSelectedItem().toString();
        Connection conn;
        Connection second;
        String sql;
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet res;


        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection("jdbc:ucanaccess://" + home + "\\Desktop\\INEC.accdb");
            sql = "update Login_Table set Vote='" + vote + "' where EmailAddress='" + idField.getText() + "'";
            pst = conn.prepareStatement(sql);

                int a = pst.executeUpdate();
                if (a > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your vote has been accepted!" +
                            "\nThank You for participating in this Voting Exercise" +
                            "\nDo not practice Political Apathy! Your Vote counts!" +
                            "\nWe can make Nigeria great Together, Spread the word!");

                    alert.setTitle("Vote Accepted");
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> alert.close());
//
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your vote was rejected!");

                    alert.setTitle("Vote Rejected");
                    alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> alert.close());
                }

        } catch(HeadlessException | ClassNotFoundException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());


            alert.setTitle("Error Message");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
        }
    }


    /**
     * This is the logout event handler for the Vote class.
     * @param event
     */
    @FXML
    private void handleLogOut(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent roots = loader.load();
            ((Stage)logout.getScene().getWindow()).setScene(new Scene(roots));
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
