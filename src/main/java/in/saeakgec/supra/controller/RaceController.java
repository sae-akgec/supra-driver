package in.saeakgec.supra.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import in.saeakgec.supra.App;
import in.saeakgec.supra.model.*;
import in.saeakgec.supra.service.DashboardService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

public class RaceController implements Initializable,MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML
    private GoogleMapView googleMapView;
    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;
    protected DirectionsRenderer directionsRenderer = null;
    private GeneralFlags generalFlags;

    @FXML
    AnchorPane mapPane;

    @FXML
    ImageView flag;

    private GoogleMap map;
    private Race race;

    protected App main;

    String DOMAIN;

    StompSession stompSession;
    StompSessionHandler sessionHandler;

    public void setMainApp(App main) {
        this.main = main;
    }

    private DecimalFormat formatter = new DecimalFormat("###.00000");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.addMapInializedListener(this);

        DOMAIN = "http://192.168.43.125:8080";
        generalFlags = new GeneralFlags();

        DashboardService dashboardService = new DashboardService();
        List<Race> r = dashboardService.getAdminRace();
        this.race= r.get(0);

        List<RedFlag> redFlags = new ArrayList<>();
        List<GreenFlag> greenFlags = new ArrayList<>();
        List<BlueFlag> blueFlags = new ArrayList<>();
        List<RedYellowFlag> redYellowFlags = new ArrayList<>();
        List<YellowFlag> yellowFlags = new ArrayList<>();

        generalFlags.setRedFlags(redFlags);
        generalFlags.setGreenFlags(greenFlags);
        generalFlags.setBlueFlags(blueFlags);
        generalFlags.setRedYellowFlags(redYellowFlags);
        generalFlags.setYellowFlags(yellowFlags);

        try {
            stompSession = connectToWebsocketServer(DOMAIN + "/socket");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  String getToken(){
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        return prefs.get("token",null);
    }


    public void showMarker(float x, float y, String code) {
        System.out.println(code);
        ImageView imageView = new ImageView();
        imageView.setX(x);
        imageView.setY(y);
        File file = new File("src/main/resources/in/saeakgec/supra/img" + code);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        mapPane.getChildren().add(imageView);
    }

    public void address(String color, MouseEvent event){
        String code="";
        if(color.equals("blue")){
            code = "/flags/flag-blue.png";
            BlueFlag blueFlag = new BlueFlag();
            blueFlag.setSrc("/flags/flag-blue.png");
            blueFlag.setLat((float) event.getX());
            blueFlag.setLon((float) event.getY());

            List<BlueFlag> blueFlags = generalFlags.getBlueFlags();
            blueFlags.add(blueFlag);
            generalFlags.setBlueFlags(blueFlags);
        }
        if(color.equals("red-yellow")){
            code = "/flags/flag-red-yellow.png";
            RedYellowFlag redYellowFlag = new RedYellowFlag();
            redYellowFlag.setSrc(code);
            redYellowFlag.setLon((float) event.getY());
            redYellowFlag.setLon((float) event.getX());

            List<RedYellowFlag> blueFlags = generalFlags.getRedYellowFlags();
            blueFlags.add(redYellowFlag);
            generalFlags.setRedYellowFlags(blueFlags);
        }
        if(color.equals("red")){
            code = "/flags/flag-red.png";
            RedFlag redFlag = new RedFlag();
            redFlag.setSrc(code);
            redFlag.setLat((float) event.getX());
            redFlag.setLon((float) event.getY());

            List<RedFlag> blueFlags = generalFlags.getRedFlags();
            blueFlags.add(redFlag);
            generalFlags.setRedFlags(blueFlags);
        }
        if(color.equals("green")){
            code = "/flags/flag-green.png";
            GreenFlag greenFlag = new GreenFlag();
            greenFlag.setSrc(code);
            greenFlag.setLat((float) event.getX());
            greenFlag.setLon((float) event.getY());

            List<GreenFlag> blueFlags = generalFlags.getGreenFlags();
            blueFlags.add(greenFlag);
            generalFlags.setGreenFlags(blueFlags);
        }
        if(color.equals("yellow")){
            code = "/flags/flag-yellow.png";
            YellowFlag yellowFlag = new YellowFlag();
            yellowFlag.setSrc(code);
            yellowFlag.setLat((float) event.getX());
            yellowFlag.setLon((float) event.getY());

            List<YellowFlag> blueFlags = generalFlags.getYellowFlags();
            blueFlags.add(yellowFlag);
            generalFlags.setYellowFlags(blueFlags);
        }

        stompSession.send("/server/flags", generalFlags);
    }


    public void setRace(Race race) {
        this.race = race;
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(28.3505, 77.5351))
                .zoomControl(false)
                .zoom(15)
                .overviewMapControl(false).mapMaker(true)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = googleMapView.createMap(options);

        directionsService = new DirectionsService();
        directionsPane = googleMapView.getDirec();
        DirectionsRequest request = new DirectionsRequest(race.getAddressFrom(), race.getAddressTo(), TravelModes.WALKING);
        directionsRenderer= new DirectionsRenderer(false, googleMapView.getMap(), directionsPane);
        directionsService.getRoute(request, this, directionsRenderer);



    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }


    // Socket code

    private StompSession connectToWebsocketServer(String url) throws ExecutionException, InterruptedException, IOException {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new MyStompSessionHandler();
        StompSession session = stompClient.connect(url, sessionHandler).get();
        return session;
    }

    class MyStompSessionHandler extends StompSessionHandlerAdapter {


        public MyStompSessionHandler() {

        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe("/server/flags", this);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            exception.printStackTrace();
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return GeneralFlags.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            GeneralFlags generalFlags1= (GeneralFlags) payload;
            generalFlags = generalFlags1;
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    mapPane.getChildren().clear();
                    List<RedFlag> redFlags = generalFlags1.getRedFlags();
                    List<GreenFlag> greenFlags = generalFlags1.getGreenFlags();
                    List<YellowFlag> yellowFlags = generalFlags1.getYellowFlags();
                    List<RedYellowFlag> redYellowFlags = generalFlags1.getRedYellowFlags();
                    List<BlueFlag> blueFlags = generalFlags1.getBlueFlags();

                    for (RedFlag redFlag:redFlags){
                        showMarker(redFlag.getLat(), redFlag.getLon(), redFlag.getSrc());
                    }
                    for (GreenFlag greenFlag:greenFlags){
                        showMarker(greenFlag.getLat(), greenFlag.getLon(), greenFlag.getSrc());
                    }
                    for (YellowFlag yellowFlag:yellowFlags){
                        showMarker(yellowFlag.getLat(), yellowFlag.getLon(), yellowFlag.getSrc());
                    }
                    for (RedYellowFlag redYellowFlag:redYellowFlags){
                        showMarker(redYellowFlag.getLat(), redYellowFlag.getLon(), redYellowFlag.getSrc());
                    }
                    for (BlueFlag blueFlag:blueFlags){
                        showMarker(blueFlag.getLat(), blueFlag.getLon(), blueFlag.getSrc());
                    }
                }
            });

            
//            mapPane.getChildren().addAll(googleMapView)


        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            exception.printStackTrace();
            super.handleTransportError(session, exception);
        }


    }

}
