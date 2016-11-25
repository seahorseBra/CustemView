package com.example;

/**
 * Created by zchao on 2016/11/25.
 */

public class Content {
    private IStrategy strategy;

    public Content(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void getWeather(){
        if (strategy != null) {
            strategy.getWeather();
        }
    }
}
