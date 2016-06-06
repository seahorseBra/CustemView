package javaBean;

/**
 * Created by zchao on 2016/6/6.
 */
public class HourWeather {
    private int time;
    private int weather;
    private int temp;

    public HourWeather(int time, int weather, int temp) {
        this.time = time;
        this.weather = weather;
        this.temp = temp;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTime() {
        return time;
    }

    public int getWeather() {
        return weather;
    }

    public int getTemp() {
        return temp;
    }
}
