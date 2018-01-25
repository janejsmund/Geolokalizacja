package com.janejsmund.geolokalizacja;


public class MyLocation {

    private String nazwa;
    private String opis;
    private String promien;
    private String latitude;
    private String longitude;

    public MyLocation(String nazwa, String opis, String promien, String latitude, String longitude) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.promien = promien;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public String getPromien() {
        return promien;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
