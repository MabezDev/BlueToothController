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
    private ComboBox myComboBox;
    @FXML
    public Slider sliderVol;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboBox();
        myComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentAudioStream = myComboBox.getValue().toString();
                headerLabel.setText(currentAudioStream);
                handler = new AudioStreamController(currentAudioStream);
                sliderVol.setValue(handler.getVolumeControl()*100);



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
                    //System.out.println("Useless Port");
            }else {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                System.out.println(mixer.getMixerInfo());
                temp.add(mixerInfo);

            }
        }
        myComboBox.setItems(FXCollections.observableArrayList(temp));//adds the ports to the combo box

    }




}
