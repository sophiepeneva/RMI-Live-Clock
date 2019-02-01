# RMI-Live-Clock
final exam preparation

import java.time.LocalTime;

public class Time {

    private int timeZone;
    private int hour;
    private int minutes;
    private int seconds;

    public void setCurrentTime() {
        LocalTime localTime = LocalTime.now();
        setHour(localTime.getHour());
        setMinutes(localTime.getMinute());
        setSeconds(localTime.getSecond());
    }

    public void tickSeconds() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
            if (minutes == 60) {
                minutes = 0;
                hour++;
                if (hour == 24) {
                    hour = 0;
                }
            }
        }
    }

    public Time(int timeZone, int hour, int minutes, int seconds) {
        setTimeZone(timeZone);
        setHour(hour);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    public Time() {
        this(0, 0, 0, 0);
    }

    public Time(Time time) {
        this(time.timeZone, time.hour, time.minutes, time.seconds);
    }

    public void setTime(Time time) {
        setTimeZone(time.timeZone);
        setHour(time.hour);
        setMinutes(time.minutes);
        setSeconds(time.seconds);
    }

    public Time getTIme() {
        return new Time(this);
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        if (timeZone >= -12 && timeZone < 13) {
            this.timeZone = timeZone;
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if (hour >= 0 && hour < 24) {
            this.hour = hour + timeZone;
            if (this.hour < 0) {
                this.hour += 24;
            }
            if(this.hour>23){
                this.hour-=24;
            }
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes >= 0 && minutes <= 59) {
            this.minutes = minutes;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (seconds >= 0 && seconds < 60) {
            this.seconds = seconds;
        }
    }

    @Override
    public String toString() {
        String time = (hour > 9) ? (hour + ":") : ("0" + hour + ":");
        time += (minutes > 9) ? (minutes + ":") : ("0" + minutes + ":");
        time += (seconds > 9) ? (seconds + ": TZ:") : ("0" + seconds + ": TZ:");
        time += (timeZone < 0) ? "-" : "+";
        time += (Math.abs(timeZone) > 9) ? (Math.abs(timeZone) + "") : ("0" + Math.abs(timeZone));
        return time;
    }
}
