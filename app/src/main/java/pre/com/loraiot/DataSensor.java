package pre.com.loraiot;

import java.util.Date;

public class DataSensor {
    int id;
    String timeStamp;
    int distance;

    public DataSensor() {

    }

    public DataSensor(int id, String timeStamp, int distance){
        this.id = id;
        this.timeStamp = timeStamp;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
