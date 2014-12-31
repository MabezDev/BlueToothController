package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private FloatControl control;
    private  javax.sound.sampled.Mixer.Info[] mixers;
    private String currentAudioStream = "None";

    AudioStreamController handler;
    @FXML
    private Label headerLabel;
    @FXML
    private Button myButton;
    @FXML
    private ComboBox myComboBox;
    @FXML
    public Slider sliderVol;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


            }
        });
        //printMixersDetails();
        initializeComboBox();
        myComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentAudioStream = myComboBox.getValue().toString();
                headerLabel.setText(currentAudioStream);
                handler = new AudioStreamController(currentAudioStream);



            }
        });

        sliderVol.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Number temp = observableValue.getValue();
                float tempFloat = temp.floatValue()/100;
                handler.setVolume(tempFloat);
            }
        });
    }

    private void initializeComboBox(){
        mixers = AudioSystem.getMixerInfo();
        ArrayList<Mixer.Info> temp = new ArrayList<Mixer.Info>();
        for(int i = 0;i<mixers.length;i++){
            Mixer.Info mixerInfo = mixers[i];
            if(mixerInfo.getVersion().contains("Unknown Version") || mixerInfo.getName().contains("Mic") || mixerInfo.getName().contains("Input")){//gets rid of dummy ports
                    System.out.println("Useless Port");
            }else {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                System.out.println(mixer.getMixerInfo());
                temp.add(mixerInfo);

            }
        }
        myComboBox.setItems(FXCollections.observableArrayList(temp));//adds the ports to the combo box

    }



   /* private void getVolumeControl(){//not working yet
        Port lineIn;
        Mixer mixer;
        FloatControl volCtrl;
        try {
            mixer = AudioSystem.getMixer(null);

            lineIn = (Port)mixer.getLine(Port.Info.LINE_IN);
            lineIn.open();
            volCtrl = (FloatControl) lineIn.getControl(

                    FloatControl.Type.VOLUME);

            // Assuming getControl call succeeds,
            // we now have our LINE_IN VOLUME control.
        } catch (Exception e) {
            System.out.println("Failed trying to find LINE_IN"
                    + " VOLUME control: exception = " + e);
        }
    }*/
}
