package in.saeakgec.supra;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/in/saeakgec/supra/view/Dashboard.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("bootstrapfx.css");
        stage.setTitle("Bravado");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String args[]){
        launch(args);
    }
}
