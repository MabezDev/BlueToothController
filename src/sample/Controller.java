package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private FloatControl control;
    private  javax.sound.sampled.Mixer.Info[] mixers;
    private String currentAudioStream = "None";
    @FXML
    private Label headerLabel;
    @FXML
    private Button myButton;
    @FXML
    private ComboBox myComboBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


            }
        });
        printMixersDetails();
        initializeComboBox();
        myComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentAudioStream = myComboBox.getValue().toString();
                headerLabel.setText(currentAudioStream);
                AudioStreamController handler = new AudioStreamController(currentAudioStream);

            }
        });
    }

    private void initializeComboBox(){
        ArrayList<Mixer.Info> temp = new ArrayList<Mixer.Info>();
        for(int i = 0;i<mixers.length;i++){
            Mixer.Info mixerInfo = mixers[i];
            if(mixerInfo.getVersion().contains("Unknown Version") || mixerInfo.getName().contains("Mic")){//gets rid of dummy ports
                    System.out.println("Useless Port");
            }else {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                System.out.println(mixer.getMixerInfo());
                temp.add(mixerInfo);

            }
        }
        myComboBox.setItems(FXCollections.observableArrayList(temp));//adds the ports to the combo box

    }

    public void printMixersDetails(){//works!
        mixers = AudioSystem.getMixerInfo();
        //System.out.println("There are " + mixers.length + " mixer info objects");
        for(int i=0;i<mixers.length;i++){//go through each mixer
            Mixer.Info mixerInfo = mixers[i];
            //System.out.println("Mixer Name:"+mixerInfo.getName());
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineinfos = mixer.getTargetLineInfo();
            for(Line.Info lineinfo : lineinfos){
                //System.out.println("line:" + lineinfo);
                try {
                    Line line = mixer.getLine(lineinfo);
                    line.open();
                    if(line.isControlSupported(FloatControl.Type.VOLUME)){
                        control = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                        //System.out.println("Volume:"+control.getValue());
                        control.setValue(1.0f);
                        // if you want to set the value for the volume 0.5 will be 50%
                        // 0.0 being 0%
                        // 1.0 being 100%
                        //control.setValue((float) 0.5)
                    }
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        }
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
