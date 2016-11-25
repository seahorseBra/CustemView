package com.example;

public class MyClass {
    public static void main(String[] arg){
        Content content = new Content(new YahooStrategy());
        content.getWeather();
        Content content1 = new Content(new GoogleStrategy());
        content1.getWeather();
    }
}
