package in.saeakgec.supra.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    ImageView hold;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            File file = new File("src/main/resources/in/saeakgec/supra/img/" + "reader.jpg");
            Image image = new Image(file.toURI().toString());
            hold.setImage(image);

    }
}
