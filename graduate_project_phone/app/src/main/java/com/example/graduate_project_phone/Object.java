package com.example.graduate_project_phone;

public class Object {
    private String Time;
    private int Moisture;
    private int TomatoWorm;
    private int BeetWorm;
    private int TabacooWorm;
    private int Problems;
    private int None;
    private int Tomato;
    private int TomatoFlower;
    private int Temperature;
    public Object(String Time,int moisture,int temperature, int tomatoWorm, int beetWorm,int tabacooWorm, int problems, int none, int tomato, int tomatoFlower) {
        this.Time = Time;
        Moisture=moisture;
        Temperature=temperature;
        TomatoWorm = tomatoWorm;
        BeetWorm = beetWorm;
        TabacooWorm=tabacooWorm;
        Problems = problems;
        None = none;
        Tomato = tomato;
        TomatoFlower = tomatoFlower;
    }

    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public String getTime() {
        return Time;
    }

    public int getTabacooWorm() {
        return TabacooWorm;
    }

    public void setTabacooWorm(int tabacooWorm) {
        TabacooWorm = tabacooWorm;
    }

    public int getMoisture() {
        return Moisture;
    }

    public void setMoisture(int moisture) {
        this.Moisture = moisture;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public int getTomatoWorm() {
        return TomatoWorm;
    }

    public void setTomatoWorm(int tomatoWorm) {
        TomatoWorm = tomatoWorm;
    }

    public int getBeetWorm() {
        return BeetWorm;
    }

    public void setBeetWorm(int beetWorm) {
        BeetWorm = beetWorm;
    }

    public int getProblems() {
        return Problems;
    }

    public void setProblems(int problems) {
        Problems = problems;
    }

    public int getNone() {
        return None;
    }

    public void setNone(int none) {
        None = none;
    }

    public int getTomato() {
        return Tomato;
    }

    public void setTomato(int tomato) {
        Tomato = tomato;
    }

    public int getTomatoFlower() {
        return TomatoFlower;
    }

    public void setTomatoFlower(int tomatoFlower) {
        TomatoFlower = tomatoFlower;
    }

    @Override
    public String toString() {
        return "Object{" +
                "Time='" + Time + '\'' +
                ", Moisture=" + Moisture +
                ", TomatoWorm=" + TomatoWorm +
                ", BeetWorm=" + BeetWorm +
                ", TabacooWorm=" + TabacooWorm +
                ", Problems=" + Problems +
                ", None=" + None +
                ", Tomato=" + Tomato +
                ", TomatoFlower=" + TomatoFlower +
                ", Temperature=" + Temperature +
                '}';
    }
}
