package in.saeakgec.supra.controller;

import in.saeakgec.supra.App;
import in.saeakgec.supra.model.Race;
import in.saeakgec.supra.service.DashboardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private DashboardService dashboardService;

    @FXML
    private ListView pendingListView;


    ObservableList<Race> pendingObservableList = FXCollections.observableArrayList();


    protected App main;

    public void setMainApp(App main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardService = new DashboardService();
        setRaces();

    }

    public void setRaces(){
        List<Race> races = dashboardService.getAdminRace();
        List<Race> pendingRaces = getPendingView(races);
        setPendingListView(races);
    }

    public void setPendingListView(List<Race> races){
        pendingObservableList.setAll(races);
        pendingListView.setItems(pendingObservableList);
        pendingListView.setCellFactory(new Callback<ListView<Race>, ListCell<Race>>(){

            @Override
            public ListCell<Race> call(ListView<Race> p) {

                ListCell<Race> cell = new ListCell<Race>(){

                    @Override
                    protected void updateItem(Race item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null){
                            DashboardData data = new DashboardData();
                            data.setInfo(item.getName(),item.getTime().toString(), item.getAddressTo(), item.getAddressFrom());
                            data.setIndicaton( "btn-warning", "btn-xs");
                            setGraphic(data.getBox());
                        }else {
                            textProperty().unbind();
                            textProperty().set("");
                        }
                    }



                };

                return cell;
            }
        });

        pendingListView.setOnMouseClicked(arg0 -> {

            ObservableList<Race> clickedRaces = pendingListView.getSelectionModel().getSelectedItems();
            Race race = clickedRaces.get(0);
            try {
                openRaceScreen(arg0, race);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }




    public List<Race> getPendingView(List<Race> races){
        ArrayList<Race> pendingRaces = new ArrayList<>();
        for (Race race : races){
            if((race.getStatus()).equals("pending")){
                pendingRaces.add(race);
            }
        }

        return pendingRaces;
    }


    private void openRaceScreen(MouseEvent event, Race race) throws IOException {
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new Pane()));

        URL location1 = RaceController.class.getResource("/in/saeakgec/supra/view/Race.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location1);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(location1.openStream());

        RaceController ctrl1 = (RaceController) fxmlLoader.getController();
        ctrl1.setRace(race);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("bootstrapfx.css");
        stage.setTitle("Dashboard");

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
