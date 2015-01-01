package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Main extends Application {

    /*
    Scrapped this as too much effort with java, switching to c# to do native
    windows calls(in other words it works but it doesnt because java doesnt
    have access the win32 api level, without major effort(JNI or JNA))
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mabez-BTController");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}
