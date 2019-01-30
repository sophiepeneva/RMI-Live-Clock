# RMI-Live-Clock
final exam preparation

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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

        GridPane pane = new GridPane();
        String name = "Clock No. " + uid;
        Button changeTimeZone = new Button("Change time zone");
        changeTimeZone.setLayoutX(150);
        changeTimeZone.setOnAction(event -> {
            Time localTime = null;
            try {
                localTime = remoteTime.setupClock(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("local time : " + localTime);
            Random random = new Random();
            int timeZone = random.nextInt(25) - 12;
            localTime.setTimeZone(timeZone);
            clock.setClock(localTime);
        });

        try {
            remoteTime = new TimeServerInterfaceImpl();
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            registry.rebind(name, remoteTime);
            remoteTime.addClocks(name, clock);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        pane.getChildren().addAll(clock, changeTimeZone);
        primaryStage.setTitle(name);
        Scene scene = new Scene(pane, 250, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
