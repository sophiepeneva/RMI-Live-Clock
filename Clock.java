# RMI-Live-Clock
final exam preparation

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Clock extends Pane {

    private boolean isStarted = false;
    private Time time;
    private Label lblClockDisplay;
    private Button start;
    private Button stop;

    public Time getTime(){
        return new Time(time);
    }

    public void setCurrentTime(){
        time.setCurrentTime();
    }

    public Clock() {
        time = new Time();
        lblClockDisplay = new Label();
        start = new Button("Start");
        start.setLayoutY(50);
        stop = new Button("Stop");
        stop.setLayoutX(70);
        stop.setLayoutY(50);
        start.setOnAction(event -> startClock());
        stop.setOnAction(event -> stopClock());

        getChildren().addAll(lblClockDisplay, start, stop);
        Thread clockThread = new Thread(() -> {

            while (true) {
                try {
                    waitIfStopped();
                    time.tickSeconds();
                    Platform.runLater(() -> lblClockDisplay.setText(time.toString()));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    private synchronized void waitIfStopped() throws InterruptedException {
        while(!isStarted){
            wait();
        }
    }

    public synchronized void startClock() {
        time.setCurrentTime();
        isStarted = true;
        notifyAll();
    }

    public void stopClock() {
        isStarted = false;
    }

    public void setClock(Time newTime) {
        time.setTime(newTime);
    }
    
}
