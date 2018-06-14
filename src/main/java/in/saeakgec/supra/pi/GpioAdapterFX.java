package in.saeakgec.supra.pi;

import com.pi4j.io.gpio.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class GpioAdapterFX {

//    private GpioController GPIO;
//    private ObservableMap<BooleanProperty, ChangeListener<Boolean>> pins;
//    private BooleanProperty pin0StateProperty;
//    private BooleanProperty pin1StateProperty;
//    private BooleanProperty pin2StateProperty;
//
//    public GpioAdapterFX() {
//        init();
//    }
//
//    private void init() {
//        pin0StateProperty = new SimpleBooleanProperty(Boolean.FALSE);
//        pin1StateProperty = new SimpleBooleanProperty(Boolean.FALSE);
//        pin2StateProperty = new SimpleBooleanProperty(Boolean.FALSE);
//
//
//        pins = FXCollections.observableHashMap();
//        pins.put(pin0StateProperty, null);
//        pins.put(pin1StateProperty, null);
//        pins.put(pin2StateProperty, null);
//    }
//
//    public void connect(){
//        GPIO = GpioFactory.getInstance();
//        GpioPinDigitalInput pin0 = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
//        GpioPinDigitalInput pin1 = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
//        GpioPinDigitalInput pin2 = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
//
//        pins.put(pin0StateProperty, createPinListener(pin0));
//        pins.put(pin1StateProperty, createPinListener(pin1));
//        pins.put(pin2StateProperty, createPinListener(pin2));
//
//        pin0StateProperty.addListener(pins.get(pin0StateProperty));
//        pin1StateProperty.addListener(pins.get(pin1StateProperty));
//        pin2StateProperty.addListener(pins.get(pin2StateProperty));
//    }
//
//    public void reset() {
//        if (testTimeline != null) {
//            testTimeline.stop();
//        }
//        for (BooleanProperty booleanProperty : pins.keySet()) {
//            booleanProperty.setValue(Boolean.FALSE);
//        }
//    }

}
