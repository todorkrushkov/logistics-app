package logisticsapp;

import logisticsapp.core.LogisticsEngineImpl;

public class Startup {
    public static void main(String[] args) {
        LogisticsEngineImpl engine = new LogisticsEngineImpl();
        engine.printBootImage();
        engine.load();
        engine.start();
        engine.save();
    }
}