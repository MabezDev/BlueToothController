package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

import javax.sound.sampled.*;

/**
 * Created by Scott on 31/12/2014.
 */
public class AudioStreamController {

    private  Mixer.Info[]  mixers;
    private  Mixer mix;
    private Port chosenPort;
    private float volumeControl;
    private FloatControl control;
    public float Volume = 0;





    public AudioStreamController(String audioLine) {

        System.out.println("In Contructor");
        mixers = AudioSystem.getMixerInfo();
        for(int i = 0; i < mixers.length;i++){
            Mixer.Info temp = mixers[i];
            if((temp.toString()).equals(audioLine)){
               mix = AudioSystem.getMixer(mixers[i]);
                System.out.println("Choosen Mixer: "+mixers[i].getName());
                TakeControl();
            }

        }

    }

    private void TakeControl(){
        Line.Info[] lineinfos = mix.getTargetLineInfo();
        for(Line.Info lineinfo : lineinfos){
            System.out.println("line:" + lineinfo);
            try {
                Line line = mix.getLine(lineinfo);
                line.open();
                if(line.isControlSupported(FloatControl.Type.VOLUME)){
                    control = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    System.out.println("Volume:"+control.getValue());
                    volumeControl = control.getValue();

                    // if you want to set the value for the volume 0.5 will be 50%
                    // 0.0 being 0%
                    // 1.0 being 100%
                    //control.setValue((float) 0.5)
                }else{
                    System.out.println("Control Not Supported");
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

    }}

    public float getVolumeControl(){
        return volumeControl;
    }

    public void setVolume(float v){
        System.out.print("Setting Volume: ");
        System.out.print(v);
        System.out.println("");
        volumeControl = v;
        control.setValue(v);
    }
}

