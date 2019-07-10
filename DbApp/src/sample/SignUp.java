package sample;

import com.healthmarketscience.jackcess.*;
import com.healthmarketscience.jackcess.Cursor;
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
import org.hsqldb.types.Types;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.RowId;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the SignUp class of this voting app.
 * It holds all the elements concerning the process of Signing Up.
 */
public class SignUp implements Initializable {

    @FXML
    private TextField fName;

    @FXML
    private TextField sName;

    @FXML
    private TextField age;

    @FXML
    private TextField eMail;

    @FXML
    private TextField uName;

    @FXML
    private PasswordField fPass;

    @FXML
    private PasswordField rPass;

    @FXML
    private Button signbtn;

    @FXML
    private Button logbtn;

    String home = System.getProperty("user.home");

    /**
     * This is the initialize method of the <strong>SignUp</strong> Class.
     * Once the program is run it automatically checks for a database file.
     * If it is existing then it continues the program, else it creates it.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            File file = new File(home + "\\Desktop\\INEC.accdb");

            if (!file.exists()) {
                Database db = DatabaseBuilder.create(Database.FileFormat.V2007, new File(String.valueOf(file)));
                Table table = new TableBuilder("Login_Table")
                        .addColumn( new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                        .addColumn(new ColumnBuilder("Username").setSQLType(Types.VARCHAR))
                        .addColumn(new ColumnBuilder("Userpass").setSQLType(Types.VARCHAR))
                        .addColumn(new ColumnBuilder("FirstName").setSQLType(Types.VARCHAR))
                        .addColumn(new ColumnBuilder("Surname").setSQLType(Types.VARCHAR))
                        .addColumn(new ColumnBuilder("Age").setSQLType(Types.INTEGER))
                        .addColumn(new ColumnBuilder("EmailAddress").setSQLType(Types.VARCHAR))
                        .addColumn(new ColumnBuilder("Vote").setSQLType(Types.VARCHAR))
                        .toTable(db);

            } else {
                System.out.println("File Exists");
            }
        } catch (SQLException | IOException e) {
            e.getMessage();
        }

    }


    /**
     * This is the method that handles the sign up process for this class.
     * @param event
     */
    @FXML
    private void handleSignUp(MouseEvent event)  {

        Connection conn;
        String sql;
        PreparedStatement pst;


            if (validateFields() & validateFirstName() & validateLastName() & validateAge() & validateEmail() & validatePass()) {
                try {
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    conn = DriverManager.getConnection("jdbc:ucanaccess://" + home + "\\Desktop\\INEC.accdb");
                    sql = "insert into Login_Table (Username, Userpass, FirstName, Surname, Age, EmailAddress) values('" + uName.getText() + "', '" + rPass.getText() + "', '" + fName.getText() + "', '" + sName.getText() + "', '" + age.getText() + "', '" + eMail.getText() + "')";
                    pst = conn.prepareStatement(sql);

                    int a = pst.executeUpdate();
                    if (a > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You've successfully Signed Up!" +
                                "\nClick the Login button to return to the login page and Sign In");

                        alert.setTitle("Login Success");
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                        }
                    } catch (HeadlessException | ClassNotFoundException | SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Error: 404");


                        alert.setTitle("Error Message");
                        alert.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> alert.close());
                    }

                 }
    }


    /**
     * This method is to return the user to the home screen if/when the user is done signing up.
     * @param event
     */
    @FXML
    private void handleGoLog(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent roots = loader.load();
            ((Stage)logbtn.getScene().getWindow()).setScene(new Scene(roots, 300, 275));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This is the validation method for the First Name field
     * @return boolean
     */
    private  boolean validateFirstName() {
        Pattern p = Pattern.compile("[a-zA-z]+");
        Matcher m = p.matcher(fName.getText());
        if (m.find() && m.group().equals(fName.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a valid First Name!");


            alert.setTitle("Validate First Name");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

            return false;
        }
    }

    /**
     * This is the validation method for the Surname field
     * @return boolean
     */
    private  boolean validateLastName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(sName.getText());
        if (m.find() && m.group().equals(sName.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a valid Last Name!");


            alert.setTitle("Validate Last Name");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

            return false;
        }
    }

    /**
     * This is the validation method for the email field
     * @return boolean
     */
    private  boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(eMail.getText());
        if (m.find() && m.group().equals(eMail.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a valid Email");


            alert.setTitle("Validate Email");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

            return false;
        }
    }

    /**
     * This is the validation method for all the fields to make sure none is empty.
     * @return
     */
    private boolean validateFields() {
        if (fName.getText().isEmpty() | sName.getText().isEmpty() | eMail.getText().isEmpty()
                | uName.getText().isEmpty() | fPass.getText().isEmpty() | rPass.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please Fill All the Fields to Proceed!");


            alert.setTitle("Validate Fields");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

        }
        return true;
    }

    /**
     * This is the method that checks to make sure that Your passwords are the same
     * @return boolean
     */
    private boolean validatePass() {
        if (Objects.equals(rPass.getText(), fPass.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your passwords do not match");


            alert.setTitle("Password Mismatch!");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
            return false;
        }
    }

    /**
     * This is the age validation method. It ensures you enter a numerical value as Your age.
     * @return boolean
     */
    private  boolean validateAge() {
        Pattern p = Pattern.compile("[0-9][0-9]+");
        Matcher m = p.matcher(age.getText());
        if (m.find() && m.group().equals(age.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a Valid Age!");


            alert.setTitle("Error Message");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

            return false;
        }
    }



}
