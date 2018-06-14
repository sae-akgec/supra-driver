package in.saeakgec.supra.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    ImageView hold;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


            raspberryPi();

    }

    public void raspberryPi(){
        try{

        }catch (Exception es){

        }
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalInput pinInput0 =
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_DOWN);
        try{
        pinInput0.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                String state = event.getState().toString();
                if (state.equalsIgnoreCase("HIGH")) {
                    File file = new File("src/main/resources/in/saeakgec/supra/img/" + "fps.jpg");
                    Image image = new Image(file.toURI().toString());
                    hold.setImage(image);
                } else {

                }

            }
        });}
        catch(NullPointerException e2) {
        }

        GpioPinDigitalInput pinInput1 =
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, PinPullResistance.PULL_DOWN);
        try{
        pinInput1.addListener(new GpioPinListenerDigital(){

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                String state = event.getState().toString();
                if (state.equalsIgnoreCase("HIGH")) {
                    try {
                        openDashboard();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    File file = new File("src/main/resources/in/saeakgec/supra/img/" + "raeder.jpg");
                    Image image = new Image(file.toURI().toString());
                    hold.setImage(image);

                }

            }
        });}
        catch(NullPointerException e2) {
        }

        GpioPinDigitalInput pinInput2 =
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_22, PinPullResistance.PULL_DOWN);

        try{
        pinInput2.addListener(new GpioPinListenerDigital(){

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                String state = event.getState().toString();
                if (state.equalsIgnoreCase("HIGH")) {

                } else {

                }

            }
        });}
        catch(NullPointerException e2) {
        }
    }

    private void openDashboard() throws IOException {
        Stage stage= (Stage) hold.getScene().getWindow();;
        stage.setScene(new Scene(new Pane()));
        Parent root = FXMLLoader.load(getClass().getResource("/in/saeakgec/supra/view/Dashboard.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("bootstrapfx.css");
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
