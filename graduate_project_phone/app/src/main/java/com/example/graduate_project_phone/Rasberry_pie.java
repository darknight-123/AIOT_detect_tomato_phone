package com.example.graduate_project_phone;

public class Rasberry_pie {
    private String ID;
    private  String is_temperature;

    public Rasberry_pie(String ID, String is_temperature) {
        this.ID = ID;
        this.is_temperature = is_temperature;
    }

    public String getID() {
        return ID;
    }



    public void setID(String ID) {
        this.ID = ID;
    }

    public String isIs_temperature() {
        return is_temperature;
    }

    public void setIs_temperature(String is_temperature) {
        this.is_temperature = is_temperature;
    }
    @Override
    public String toString() {
        return "Rasberry_pie{" +
                "ID='" + ID + '\'' +
                ", is_temperature='" + is_temperature + '\'' +
                '}';
    }
}
