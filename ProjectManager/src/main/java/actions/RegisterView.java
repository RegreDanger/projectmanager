package actions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RegisterView {


    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField fullnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button visibilityButton;
    
    private ImageView eyeSlashImage = new ImageView(new Image(getClass().getResourceAsStream("/images/eye-slash.png")));
    private ImageView eyeImage = new ImageView(new Image(getClass().getResourceAsStream("/images/eye.png")));
    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
    	//TODO Agregar botón de registro, crear login y terminar el proceso de registro 
    	eyeSlashImage.setFitWidth(26);
    	eyeSlashImage.setFitHeight(26);
    	visibilityButton.setGraphic(eyeSlashImage);
    }
    
    @FXML
    void emailFieldOnAction(ActionEvent event) {

    }

    @FXML
    void fullnameFieldOnAction(ActionEvent event) {

    }

    @FXML
    void passwordFieldOnAction(ActionEvent event) {

    }

    @FXML
    void passwordTextFieldOnAction(ActionEvent event) {

    }

    @FXML
    void registerButtonOnAction(ActionEvent event) {

    }

    @FXML
    void usernameFieldOnAction(ActionEvent event) {

    }

    @FXML
    void visibilityButtonOnAction(ActionEvent event) {
    	eyeImage.setFitWidth(26);
    	eyeImage.setFitHeight(26);
    	eyeSlashImage.setFitWidth(26);
    	eyeSlashImage.setFitHeight(26);
    	if(!isPasswordVisible) {
			visibilityButton.setGraphic(eyeImage);
			passwordTextField.setText(passwordField.getText());
			passwordTextField.setVisible(true);
			passwordField.setVisible(false);
		} else {
			visibilityButton.setGraphic(eyeSlashImage);
			passwordField.setText(passwordTextField.getText());
			passwordTextField.setVisible(false);
			passwordField.setVisible(true);
		}
    	isPasswordVisible = !isPasswordVisible;
    }

}
