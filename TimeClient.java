# RMI-Live-Clock
final exam preparation

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class TimeClient extends Application {

    private static int clients = 0;
    private Clock clock;
    private TimeServerInterface remoteTime;
    private int uid;

    public TimeClient() {
        clients++;
        uid = clients;
        clock = new Clock();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        popUpWindow(primaryStage, this , 0);
        for (int i = 0; i < 3; i++) {
            Stage stage = new Stage();
            TimeClient timeClient = new TimeClient();
            popUpWindow(stage,timeClient, i+1);
        }
    }

    public void popUpWindow(Stage primaryStage, TimeClient timeClient, int position){

        Pane pane = new VBox();

        String name = "Clock No. " + timeClient.uid;
        Button changeTimeZone = new Button("Change time zone");
        changeTimeZone.setLayoutX(150);
        changeTimeZone.setOnAction(event -> {
            Time localTime = null;
            try {
                localTime = timeClient.remoteTime.setupClock(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("local time : " + localTime);
            Random random = new Random();
            int timeZone = random.nextInt(25) - 12;
            localTime.setTimeZone(timeZone);
            timeClient.clock.setClock(localTime);
        });

        try {
            timeClient.remoteTime = new TimeServerInterfaceImpl();
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            registry.rebind(name, timeClient.remoteTime);
            timeClient.remoteTime.addClocks(name, clock);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        pane.getChildren().addAll(new HBox(timeClient.clock, changeTimeZone));
        primaryStage.setTitle(name);
        Scene scene = new Scene(pane, 250, 100);
        primaryStage.setScene(scene);
        primaryStage.setX(position * 300);
        primaryStage.setY(300);
        primaryStage.show();
    }
}

