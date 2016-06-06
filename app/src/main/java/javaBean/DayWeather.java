package javaBean;

/**
 * Created by zchao on 2016/6/6.
 */
public class DayWeather {
    private long time;
    private int weather;
    private int tempMax;
    private int tempMin;

    public void setTime(long time) {
        this.time = time;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public long getTime() {
        return time;
    }

    public int getWeather() {
        return weather;
    }

    public int getTempMax() {
        return tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }
}
